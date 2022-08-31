package com.jxxx.tiyu_app.view.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.bean.SchoolClassBean;

import java.util.List;

public class ShangKeBanJiAdapter extends BaseQuickAdapter<SchoolClassBean, BaseViewHolder> {

    String id = "0";
    public ShangKeBanJiAdapter(@Nullable List<SchoolClassBean> data) {
        super(R.layout.item_shangke_banji, data);
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    protected void convert(BaseViewHolder helper, SchoolClassBean item) {
        helper.setBackgroundRes(R.id.ll,R.drawable.shape_select_banji_2)
                .setImageResource(R.id.iv_select,R.drawable.ic_yuan_dui_2)
                .setText(R.id.tv_name,item.getClassName());
        if(id.equals(item.getId())){
            helper.setBackgroundRes(R.id.ll,R.drawable.shape_select_banji_1)
                    .setImageResource(R.id.iv_select,R.drawable.ic_yuan_dui_1);
        }
    }
}
