package com.jxxx.tiyu_app.view.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.bean.SchoolClassBean;

import java.util.List;

public class HomeTwoOneListAdapter extends BaseQuickAdapter<SchoolClassBean.ClassGroupListBean, BaseViewHolder> {

    private String id = "";

    public void setId(String id) {
        this.id = id;
    }

    public HomeTwoOneListAdapter(@Nullable List<SchoolClassBean.ClassGroupListBean> data) {
        super(R.layout.item_home_two_one, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, SchoolClassBean.ClassGroupListBean item) {

        helper.setVisible(R.id.tv_content_1,true).setText(R.id.tv_content_1,"队列"+(helper.getLayoutPosition()+1))
                .setVisible(R.id.tv_content_2,false).setText(R.id.tv_content_2,"队列"+(helper.getLayoutPosition()+1)) ;
        if(id.equals(item.getId())){
            helper.setVisible(R.id.tv_content_1,false)
                    .setVisible(R.id.tv_content_2,true);
        }
    }
}
