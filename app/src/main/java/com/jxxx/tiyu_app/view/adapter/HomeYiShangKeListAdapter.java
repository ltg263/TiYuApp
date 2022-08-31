package com.jxxx.tiyu_app.view.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxx.tiyu_app.R;

import java.util.List;

public class HomeYiShangKeListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public HomeYiShangKeListAdapter(@Nullable List<String> data) {
        super(R.layout.item_home_yishangke, data);
    }


    @Override
    protected void convert(BaseViewHolder helper,String item) {
        helper.addOnClickListener(R.id.tv_type_2);
    }
}
