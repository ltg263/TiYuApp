package com.jxxx.tiyu_app.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.bean.DictDataTypeBean;

import java.util.List;

public class PopupWindowAdapter_home extends BaseQuickAdapter<DictDataTypeBean, BaseViewHolder> {

    public PopupWindowAdapter_home(List<DictDataTypeBean> data) {
        super(R.layout.item_spinner_product_type, data);
    }

    @Override
    protected void convert(BaseViewHolder helper,DictDataTypeBean item) {
        helper.setText(R.id.text,item.getDictLabel())
                .setTextColor(R.id.text,mContext.getResources().getColor(R.color.color_333333))
                .setVisible(R.id.view,true);
        if(helper.getLayoutPosition()==mData.size()-1){
            helper.setVisible(R.id.view,false);
        }
        if(item.isSelect()){
            helper.setTextColor(R.id.text,mContext.getResources().getColor(R.color.red40));
        }

    }

}
