package com.jxxx.tiyu_app.view.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.api.RetrofitUtil;
import com.jxxx.tiyu_app.app.ConstValues;
import com.jxxx.tiyu_app.base.Result;
import com.jxxx.tiyu_app.bean.SchoolCourseBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBeanSmall;
import com.jxxx.tiyu_app.bean.SchoolStudentBean;
import com.jxxx.tiyu_app.utils.CustomPopWindow;
import com.jxxx.tiyu_app.utils.GlideImgLoader;
import com.jxxx.tiyu_app.view.activity.HomeTwoShangKeActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class KeChengXiangQingAdapter extends BaseQuickAdapter<SchoolCourseBean.CourseSectionVoListBean, BaseViewHolder> {



    List<String> list = new ArrayList<>();
    int queueNum = 0;

    public void setQueueNum(int queueNum) {
        this.queueNum = queueNum;
    }

    public KeChengXiangQingAdapter(@Nullable List<SchoolCourseBean.CourseSectionVoListBean> data) {
        super(R.layout.item_kechengxiangqing, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, SchoolCourseBean.CourseSectionVoListBean item) {
        SchoolCourseBeanSmall mSmallCourseVo = item.getSmallCourseVo();
        helper.setText(R.id.tv_no,(helper.getLayoutPosition()+1)+"")
                .setText(R.id.tv_type_1,"每组0人  |  共0个步骤");
        if(mSmallCourseVo!=null){
            GlideImgLoader.loadImageViewRadiusNoCenter(mContext,mSmallCourseVo.getImgUrl(),helper.getView(R.id.iv_icon));
            helper.setText(R.id.tv_type_1,"每组"+mSmallCourseVo.getGroupNum()+"人  |  共"+mSmallCourseVo.getStepNum()+"个步骤")
                    .setText(R.id.tv_name,mSmallCourseVo.getCourseName())
                .setText(R.id.tv_type_2,mSmallCourseVo.getQueueInfo());
        }
        helper.setImageResource(R.id.iv_dou,R.drawable.ic_dialog_jindu_2)
                .setTextColor(R.id.tv_no,mContext.getResources().getColor(R.color.color_999999))
            .setVisible(R.id.iv_1,false).setVisible(R.id.iv_2,false)
            .setVisible(R.id.iv_dangqian,false);
        if(helper.getLayoutPosition()==0){
            helper.setTextColor(R.id.tv_no,mContext.getResources().getColor(R.color.color_text_theme))
                    .setImageResource(R.id.iv_dou,R.drawable.ic_dialog_jindu_1)
                    .setVisible(R.id.iv_1,true).setVisible(R.id.iv_dangqian,true);
        }
        TextView tv_1 = helper.getView(R.id.tv_1);
        TextView tv_2 = helper.getView(R.id.tv_2);
        tv_1.setText(String.valueOf(item.getQueueingNum()));
        tv_2.setText(String.valueOf(item.getLoopNum()));
        tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mContext instanceof HomeTwoShangKeActivity){
                    list.clear();
                    for(int i=0;i<queueNum;i++){
                        list.add((i+1)+"");
                    }
                    if(list.size()==0){
                        return;
                    }
                    CustomPopWindow.initPopupWindow((Activity) mContext,tv_1, list,
                            new CustomPopWindow.PopWindowInterface() {
                                @Override
                                public void getPosition(int position) {
                                    tv_1.setText(list.get(position));
//                                    ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList()
//                                            .get(helper.getLayoutPosition()).setQueueingNum(Integer.parseInt(list.get(position)));]
                                    postSmallCourseCopyQueue(item.getSmallCourseId());
                                }
                            });
                }
            }
        });
        tv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mContext instanceof HomeTwoShangKeActivity) {
                    list.clear();
                    list.add("1");
                    list.add("2");
                    list.add("3");
                    list.add("4");
                    list.add("5");
                    CustomPopWindow.initPopupWindow((Activity) mContext, tv_2, list,
                            new CustomPopWindow.PopWindowInterface() {
                                @Override
                                public void getPosition(int position) {
                                    tv_2.setText(list.get(position));
                                    ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList()
                                            .get(helper.getLayoutPosition()).setLoopNum(Integer.parseInt(list.get(position)));
                                }
                            });
                }
            }
        });
    }

    private void postSmallCourseCopyQueue(String smallCourseId) {
        SchoolCourseBean.CourseSectionVoListBean mCourseSectionVoListBean = new SchoolCourseBean.CourseSectionVoListBean();
        mCourseSectionVoListBean.setId(smallCourseId);
        RetrofitUtil.getInstance().apiService()
                .postSmallCourseCopyQueue(mCourseSectionVoListBean)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result result) {
//                        if(isResultOk(result) && result.getData()!=null){
//                            ConstValues.mSchoolStudentInfoBean = result.getData();
//                        }
                    }

                    @Override
                    public void onError(Throwable e) {
//                        hideLoading();
                    }

                    @Override
                    public void onComplete() {
//                        hideLoading();
                    }
                });
    }
}
