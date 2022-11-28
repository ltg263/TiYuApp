package com.jxxx.tiyu_app.view.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.bean.SceduleCourseBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBean;
import com.jxxx.tiyu_app.utils.GlideImgLoader;

import java.util.List;

public class HomeOneAdapterBk extends BaseQuickAdapter<SceduleCourseBean, BaseViewHolder> {


    public HomeOneAdapterBk(@Nullable List<SceduleCourseBean> data) {
        super(R.layout.item_home_one, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, SceduleCourseBean item) {
        helper.addOnClickListener(R.id.tv_kcxq).addOnClickListener(R.id.tv_kssk);
        helper.setText(R.id.tv_kssk,"无法上课")
                .setText(R.id.tv_name,"--")
                .setText(R.id.tv_type_1,"--")
                .setText(R.id.tv_type_2,"--");
        GlideImgLoader.loadImageViewRadiusNoCenter(mContext,"",helper.getView(R.id.iv_icon));
        if(item.getClassScheduleCard()!=null){
            helper.setText(R.id.tv_type_2,item.getClassScheduleCard().getClassName());
        }
        if(item.getCourse()!=null){
            GlideImgLoader.loadImageViewRadiusNoCenter(mContext,item.getCourse().getImgUrl(),helper.getView(R.id.iv_icon));
            helper.setText(R.id.tv_name,item.getCourse().getCourseName())
                    .setText(R.id.tv_type_1,item.getCourse().getLables().replace(",","|"));
            if(item.getCourse().getCourseSectionVoList().size()>0 && item.getClassScheduleCard()!=null){
                helper.setText(R.id.tv_kssk,"开始上课");
            }
        }
    }
}
