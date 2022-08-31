package com.jxxx.tiyu_app.view.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.bean.SchoolCourseBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBeanSmall;
import com.jxxx.tiyu_app.utils.GlideImgLoader;

import java.util.List;

public class HomeOneAdapterSmall extends BaseQuickAdapter<SchoolCourseBeanSmall, BaseViewHolder> {


    public HomeOneAdapterSmall(@Nullable List<SchoolCourseBeanSmall> data) {
        super(R.layout.item_home_one, data);
    }

    /**
     *
     * @param helper
     * @param item
     */


    @Override
    protected void convert(BaseViewHolder helper, SchoolCourseBeanSmall item) {
        helper.addOnClickListener(R.id.tv_kcxq).addOnClickListener(R.id.tv_kssk);
        GlideImgLoader.loadImageViewRadiusNoCenter(mContext,item.getImgUrl(),helper.getView(R.id.iv_icon));
        helper.setText(R.id.tv_name,item.getCourseName()).setText(R.id.tv_type_1,item.getLables())
            .setText(R.id.tv_type_2,"每组"+item.getGroupNum()+"人  |  共"+item.getStepNum()+"个步骤");
    }
}
