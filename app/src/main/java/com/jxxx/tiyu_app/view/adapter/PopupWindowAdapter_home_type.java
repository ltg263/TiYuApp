package com.jxxx.tiyu_app.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.bean.CourseTypeListAllBean;
import com.jxxx.tiyu_app.bean.DictDataTypeBean;

import java.util.List;

public class PopupWindowAdapter_home_type extends BaseQuickAdapter<CourseTypeListAllBean, BaseViewHolder> {

    boolean isYou = false;
    public PopupWindowAdapter_home_type(List<CourseTypeListAllBean> data) {
        super(R.layout.item_spinner_product_type, data);
    }

    public void setYou(boolean you) {
        isYou = you;
    }

    @Override
    protected void convert(BaseViewHolder helper,CourseTypeListAllBean item) {
        helper.setText(R.id.text,item.getTypeName())
                .setTextColor(R.id.text,mContext.getResources().getColor(R.color.color_333333))
                .setGone(R.id.view,false);
        if(helper.getLayoutPosition()==mData.size()-1){
//            helper.setVisible(R.id.view,false);
        }
        if(isYou){
            helper.setVisible(R.id.iv,true);
        }
        if(item.isSelect()){
            helper.setTextColor(R.id.text,mContext.getResources().getColor(R.color.red40));
        }

    }

}
