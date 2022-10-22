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
import com.jxxx.tiyu_app.app.MainApplication;
import com.jxxx.tiyu_app.base.Result;
import com.jxxx.tiyu_app.bean.SchoolCourseBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBeanSmall;
import com.jxxx.tiyu_app.bean.SchoolStudentBean;
import com.jxxx.tiyu_app.utils.CustomPopWindow;
import com.jxxx.tiyu_app.utils.GlideImgLoader;
import com.jxxx.tiyu_app.utils.StringUtil;
import com.jxxx.tiyu_app.utils.ToastUtil;
import com.jxxx.tiyu_app.view.activity.HomeTwoShangKeActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class KeChengXiangQingAdapter extends BaseQuickAdapter<SchoolCourseBean.CourseSectionVoListBean, BaseViewHolder> {

    public KeChengXiangQingAdapter(@Nullable List<SchoolCourseBean.CourseSectionVoListBean> data) {
        super(R.layout.item_kechengxiangqing, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, SchoolCourseBean.CourseSectionVoListBean item) {
        SchoolCourseBeanSmall mSmallCourseVo = item.getSmallCourseVo();
        helper.setText(R.id.tv_no,(helper.getLayoutPosition()+1)+"")
                .setText(R.id.tv_type_1,"共0个队列  |  共0个步骤");
        if(mSmallCourseVo!=null){
            GlideImgLoader.loadImageViewRadiusNoCenter(mContext,mSmallCourseVo.getImgUrl(),helper.getView(R.id.iv_icon));
            helper.setText(R.id.tv_type_1,"共"+mSmallCourseVo.getQueueNum()+"个队列  |  共"+mSmallCourseVo.getStepNum()+"个步骤")
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
        helper.addOnClickListener(R.id.tv_1);
        helper.addOnClickListener(R.id.tv_2);
        tv_1.setText(String.valueOf(item.getQueueingNum()));
        tv_2.setText(String.valueOf(item.getLoopNum()));
    }
}
