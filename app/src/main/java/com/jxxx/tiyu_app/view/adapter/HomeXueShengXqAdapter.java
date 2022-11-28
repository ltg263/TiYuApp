package com.jxxx.tiyu_app.view.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.bean.SchoolClassRecordBean;
import com.jxxx.tiyu_app.bean.SchoolStudentDetailBean;
import com.jxxx.tiyu_app.utils.GlideImgLoader;

import java.util.List;

public class HomeXueShengXqAdapter extends BaseQuickAdapter<SchoolStudentDetailBean.StudentClassRecordsBean, BaseViewHolder> {


    public HomeXueShengXqAdapter(@Nullable List<SchoolStudentDetailBean.StudentClassRecordsBean> data) {
        super(R.layout.fragment_home_banji, data);
    }


    @Override
    protected void convert(BaseViewHolder helper,SchoolStudentDetailBean.StudentClassRecordsBean item) {
        GlideImgLoader.loadImageViewRadiusNoCenter(mContext,item.getImgUrl(),helper.getView(R.id.iv_icon));
        helper.setText(R.id.tv_name,item.getCourseName());
    }
}
