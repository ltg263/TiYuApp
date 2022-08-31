package com.jxxx.tiyu_app.view.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.bean.SchoolCourseBeanSmall;
import com.jxxx.tiyu_app.utils.StringUtil;

import java.util.List;

public class KeChengXiangQingAdapterSmall extends BaseQuickAdapter<SchoolCourseBeanSmall.StepGroupsBean.CourseStepListBean, BaseViewHolder> {
    public KeChengXiangQingAdapterSmall(@Nullable List<SchoolCourseBeanSmall.StepGroupsBean.CourseStepListBean> data) {
        super(R.layout.item_kechengxiangqing_small, data);

    }


    @Override
    protected void convert(BaseViewHolder helper,SchoolCourseBeanSmall.StepGroupsBean.CourseStepListBean item) {
        helper.setGone(R.id.ll,false).setVisible(R.id.view_line,false);
        if(helper.getLayoutPosition()==0){
            helper.setGone(R.id.ll,true);

        }
        helper.setText(R.id.tv_duilie,item.getDuilie_pos())
                .setText(R.id.tv_buzhou,item.getBuzhou_pos())
                .setText(R.id.tv_bianma,item.getSortNum())
                .setText(R.id.tv_mingling,item.getActionMode());
        if(StringUtil.isNotBlank(item.getDuilie_pos())){
            helper.setVisible(R.id.view_line,true);
        }
    }
}
