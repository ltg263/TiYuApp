package com.jxxx.tiyu_app.view.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.bean.SchoolClassBean;

import java.util.List;

public class ShangKeBanJi_jlAdapter extends BaseQuickAdapter<SchoolClassBean, BaseViewHolder> {

    String id = "0";
    public ShangKeBanJi_jlAdapter(@Nullable List<SchoolClassBean> data) {
        super(R.layout.item_shangke_banji_jilu, data);
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    protected void convert(BaseViewHolder helper, SchoolClassBean item){
        helper.setVisible(R.id.tv_content_1,true).setText(R.id.tv_content_1,item.getClassName())
                .setVisible(R.id.tv_content_2,false).setText(R.id.tv_content_2,item.getClassName()) ;
        if(id.equals(item.getId())){
            helper.setVisible(R.id.tv_content_1,false)
                    .setVisible(R.id.tv_content_2,true);
        }
    }
}
