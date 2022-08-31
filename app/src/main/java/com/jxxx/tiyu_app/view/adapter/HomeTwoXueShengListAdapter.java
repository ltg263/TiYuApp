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
        GlideImgLoader.loadImageViewRadiusNoCenter(mContext, "https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png", helper.getView(R.id.iv_img));
        helper.setText(R.id.tv_studentNo,"学号："+item.getStudentNo()).setText(R.id.tv_studentName,"姓名："+item.getStudentName())
                .setText(R.id.tv_age,"年纪："+item.getAge()+"岁").setText(R.id.tv_chaoshi,"性别："+(Integer.parseInt(item.getGender())==1?"男":"女"));
    }
}

