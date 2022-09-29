package com.jxxx.tiyu_app.view.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.bean.SchoolClassRecordBean;
import com.jxxx.tiyu_app.utils.GlideImgLoader;

import java.util.List;

public class HomeYiShangKeListAdapter extends BaseQuickAdapter<SchoolClassRecordBean, BaseViewHolder> {


    public HomeYiShangKeListAdapter(@Nullable List<SchoolClassRecordBean> data) {
        super(R.layout.item_home_yishangke, data);
    }


    @Override
    protected void convert(BaseViewHolder helper,SchoolClassRecordBean item) {
        helper.addOnClickListener(R.id.tv_type_2);
        GlideImgLoader.loadImageViewRadiusNoCenter(mContext,item.getImgUrl(),helper.getView(R.id.iv_icon));
        helper.setText(R.id.tv_name,item.getCourseName()).setText(R.id.tv_type_1,item.getLabels());
    }
}
