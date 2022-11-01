package com.jxxx.tiyu_app.view.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.bean.SchoolClassBean;
import com.jxxx.tiyu_app.utils.GlideImgLoader;

import java.util.List;

public class HomeThreeListAdapter extends BaseQuickAdapter<SchoolClassBean, BaseViewHolder> {


    public HomeThreeListAdapter(@Nullable List<SchoolClassBean> data) {
        super(R.layout.item_home_three, data);
    }


    @Override
    protected void convert(BaseViewHolder helper,SchoolClassBean item) {
        GlideImgLoader.loadImageViewRadiusNoCenter(mContext, null, helper.getView(R.id.iv_icon));
        helper.setText(R.id.tv_name,item.getClassName()).setText(R.id.tv_type_1,item.getStudentNum()+"个学生")
        .setText(R.id.tv_type_2,"已上"+11+"次课");
    }
}
