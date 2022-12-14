package com.jxxx.tiyu_app.view.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.bean.SchoolStudentBean;
import com.jxxx.tiyu_app.utils.StringUtil;
import com.jxxx.tiyu_app.view.activity.HomeTwoXueShengActivity;

import java.util.List;

public class HomeTwoTwoListAdapter extends BaseQuickAdapter<SchoolStudentBean, BaseViewHolder> {

    int mCurrentPos = 0;

    public void setCurrentPos(int currentPos) {
        mCurrentPos = currentPos;
    }

    public int getCurrentPos() {
        return mCurrentPos;
    }

    public HomeTwoTwoListAdapter(@Nullable List<SchoolStudentBean> data) {
        super(R.layout.item_home_two_two, data);
    }


    @Override
    protected void convert(BaseViewHolder helper,SchoolStudentBean item) {
        helper.setText(R.id.tv_1,item.getStudentName()+"").setTextColor(R.id.tv_1,mContext.getResources().getColor(R.color.color_000000))
                .setText(R.id.tv_2,item.getPostWccs()+"").setTextColor(R.id.tv_2,mContext.getResources().getColor(R.color.color_000000))
                .setText(R.id.tv_3,item.getPostZjzs()+"").setTextColor(R.id.tv_3,mContext.getResources().getColor(R.color.color_000000))
                .setText(R.id.tv_4, StringUtil.getTime(item.getPostZys())).setTextColor(R.id.tv_4,mContext.getResources().getColor(R.color.color_000000))
                .setText(R.id.tv_5, item.getPostPjsd()+"s").setTextColor(R.id.tv_5,mContext.getResources().getColor(R.color.color_000000));
        if(item.getSteps()==null && item.getLists()==null){
            helper.setText(R.id.tv_2,"-")
                    .setText(R.id.tv_3,"-")
                    .setText(R.id.tv_4,"-")
                    .setText(R.id.tv_5,"-");
        }
        if(helper.getLayoutPosition()==mCurrentPos){
            helper.setTextColor(R.id.tv_1,mContext.getResources().getColor(R.color.color_text_theme))
                    .setTextColor(R.id.tv_2,mContext.getResources().getColor(R.color.color_text_theme))
                    .setTextColor(R.id.tv_3,mContext.getResources().getColor(R.color.color_text_theme))
                    .setTextColor(R.id.tv_4,mContext.getResources().getColor(R.color.color_text_theme))
                    .setTextColor(R.id.tv_5,mContext.getResources().getColor(R.color.color_text_theme));
        }
        if(item.getPostWccs()>= HomeTwoXueShengActivity.current_course_section_loop_num){
            helper.setTextColor(R.id.tv_1,mContext.getResources().getColor(R.color.red40))
                    .setTextColor(R.id.tv_2,mContext.getResources().getColor(R.color.red40))
                    .setTextColor(R.id.tv_3,mContext.getResources().getColor(R.color.red40))
                    .setTextColor(R.id.tv_4,mContext.getResources().getColor(R.color.red40))
                    .setTextColor(R.id.tv_5,mContext.getResources().getColor(R.color.red40));
        }
        if(item.getLists()!=null){
            helper.setGone(R.id.tv_4,false).setGone(R.id.tv_5,false);
        }
    }
}
