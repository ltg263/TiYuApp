package com.jxxx.tiyu_app.view.adapter;

import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.bean.DictDataTypeBean;
import com.jxxx.tiyu_app.bean.SchoolClassBean;

import java.util.List;

public class HomeTwoType_SxAdapter extends BaseQuickAdapter<DictDataTypeBean, BaseViewHolder> {

    String dictValue = null;
    public HomeTwoType_SxAdapter(@Nullable List<DictDataTypeBean> data) {
        super(R.layout.item_shangke_banji, data);
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getDictValue() {
        return dictValue;
    }

    @Override
    protected void convert(BaseViewHolder helper, DictDataTypeBean item) {
        helper.setBackgroundRes(R.id.ll,R.drawable.shape_select_banji_2).setGone(R.id.iv_select,false)
                .setImageResource(R.id.iv_select,R.drawable.ic_yuan_dui_2)
                .setText(R.id.tv_name,item.getDictLabel());
        if(dictValue!=null && dictValue.equals(item.getDictValue())){
            helper.setBackgroundRes(R.id.ll,R.drawable.shape_select_banji_1)
                    .setImageResource(R.id.iv_select,R.drawable.ic_yuan_dui_1);
        }
        helper.getView(R.id.ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dictValue!=null && dictValue.equals(item.getDictValue())){
                    setDictValue(null);
                }else{
                    setDictValue(item.getDictValue());
                }
                notifyDataSetChanged();
            }
        });
    }
}
