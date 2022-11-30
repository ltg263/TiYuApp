package com.jxxx.tiyu_app.view.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.bean.SchoolClassRecordBean;
import com.jxxx.tiyu_app.bean.SchoolStudentBean;
import com.jxxx.tiyu_app.utils.StringUtil;
import com.jxxx.tiyu_app.view.activity.HomeTwoXueShengActivity;

import java.util.List;

public class HomeBanJiXqAdapter extends BaseQuickAdapter<SchoolClassRecordBean.StudentResultsListBean, BaseViewHolder> {



    public HomeBanJiXqAdapter(@Nullable List<SchoolClassRecordBean.StudentResultsListBean> data) {
        super(R.layout.item_home_two_two, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, SchoolClassRecordBean.StudentResultsListBean item) {
        helper.setText(R.id.tv_1,item.getStudentName()+"")
                .setTextColor(R.id.tv_1,mContext.getResources().getColor(R.color.color_000000))
                .setText(R.id.tv_2,item.getTimes()+"")
                .setTextColor(R.id.tv_2,mContext.getResources().getColor(R.color.color_000000))
                .setText(R.id.tv_3,item.getFinishTimes()+"")
                .setTextColor(R.id.tv_3,mContext.getResources().getColor(R.color.color_000000))
                .setText(R.id.tv_4, StringUtil.getTime(item.getTimeUse()))
                .setTextColor(R.id.tv_4,mContext.getResources().getColor(R.color.color_000000))
                .setText(R.id.tv_5, item.getSpeed()+"s")
                .setTextColor(R.id.tv_5,mContext.getResources().getColor(R.color.color_000000));

        if(true){//不考虑总反馈次数
            helper.setGone(R.id.tv_6,false);
        }
    }
}