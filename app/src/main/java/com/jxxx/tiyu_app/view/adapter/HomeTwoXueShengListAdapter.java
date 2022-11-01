package com.jxxx.tiyu_app.view.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.bean.SchoolStudentBean;
import com.jxxx.tiyu_app.utils.GlideImgLoader;

import java.util.List;

public class HomeTwoXueShengListAdapter extends BaseQuickAdapter<SchoolStudentBean, BaseViewHolder> {

    public HomeTwoXueShengListAdapter(@Nullable List<SchoolStudentBean> data) {
        super(R.layout.item_home_two_xuesheng_list, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, SchoolStudentBean item) {
        GlideImgLoader.loadImageViewRadiusNoCenter(mContext, item.getImgUrl(), helper.getView(R.id.iv_img));
        helper.setText(R.id.tv_studentNo,"学号："+item.getStudentNo()).setText(R.id.tv_studentName,"姓名："+item.getStudentName())
                .setText(R.id.tv_age,"年龄："+item.getAge()+"岁").setText(R.id.tv_chaoshi,"性别："+(item.getGender()==0?"男":"女"));
    }
}

