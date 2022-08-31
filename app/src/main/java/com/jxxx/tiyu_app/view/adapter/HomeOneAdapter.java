package com.jxxx.tiyu_app.view.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.bean.SchoolCourseBean;
import com.jxxx.tiyu_app.utils.GlideImgLoader;

import java.util.List;

public class HomeOneAdapter extends BaseQuickAdapter<SchoolCourseBean, BaseViewHolder> {


    public HomeOneAdapter(@Nullable List<SchoolCourseBean> data) {
        super(R.layout.item_home_one, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, SchoolCourseBean item) {
        helper.addOnClickListener(R.id.tv_kcxq).addOnClickListener(R.id.tv_kssk);
        GlideImgLoader.loadImageViewRadiusNoCenter(mContext,item.getImgUrl(),helper.getView(R.id.iv_icon));
        helper.setText(R.id.tv_name,item.getCourseName())
                .setText(R.id.tv_type_1,item.getLables().replace(",","|"))
                .setText(R.id.tv_kssk,"无法上课")
                .setText(R.id.tv_type_2,"每组"+item.getGroupNum()+"人  |  共"+item.getCourseSectionVoList().size()+"个小节");
        if(item.getCourseSectionVoList().size()>0){
            helper.setText(R.id.tv_kssk,"开始上课");
        }
    }
}
