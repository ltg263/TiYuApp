package com.jxxx.tiyu_app.view.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.bean.SchoolStudentBean;
import com.jxxx.tiyu_app.utils.StringUtil;

import java.util.List;

public class HomeTwoTwoListAdapter extends BaseQuickAdapter<SchoolStudentBean, BaseViewHolder> {

    public HomeTwoTwoListAdapter(@Nullable List<SchoolStudentBean> data) {
        super(R.layout.item_home_two_two, data);
    }


    @Override
    protected void convert(BaseViewHolder helper,SchoolStudentBean item) {
        helper.setText(R.id.tv_1,item.getStudentName()+"")
                .setText(R.id.tv_2,item.getPostWccs()+"")
                .setText(R.id.tv_3,item.getPostZjzs()+"")
                .setText(R.id.tv_4, StringUtil.getTime(item.getPostZys()))
                .setText(R.id.tv_5, item.getPostPjsd()+"s");
        if(item.getSteps()==null){
            helper.setText(R.id.tv_2,"-")
                    .setText(R.id.tv_3,"-")
                    .setText(R.id.tv_4,"-")
                    .setText(R.id.tv_5,"-");
        }
    }
}
