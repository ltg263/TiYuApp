package com.jxxx.tiyu_app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jxxx.tiyu_app.MainActivity;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.app.ConstValues;
import com.jxxx.tiyu_app.base.BaseActivity;
import com.jxxx.tiyu_app.utils.GlideImgLoader;
import com.jxxx.tiyu_app.view.adapter.HomeTwoXueShengAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeTwoXueShengActivity extends BaseActivity {

    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_icon)
    ImageView mIvIcon;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_type_1)
    TextView mTvType1;
    @BindView(R.id.tv_type_2)
    TextView mTvType2;
    @BindView(R.id.tv_jishi)
    TextView mTvJishi;
    @BindView(R.id.btn_kaishiyundong)
    Button mBtnKaishiyundong;

    @Override
    public int intiLayout() {
        return R.layout.activity_home_two_xuesheng;
    }

    @Override
    public void initView() {
        //课程信息
        if(ConstValues.mSchoolCourseInfoBean!=null){
            GlideImgLoader.loadImageViewRadiusNoCenter(this,ConstValues.mSchoolCourseInfoBean.getImgUrl(),mIvIcon);
            mTvName.setText(ConstValues.mSchoolCourseInfoBean.getCourseName());
            mTvType1.setText(ConstValues.mSchoolCourseInfoBean.getLables().replace(",", "|"));
            mTvType2.setText("每组" + ConstValues.mSchoolCourseInfoBean.getGroupNum() +
                    "人  |  共" + ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().size() + "个小节");
        }
        if(ConstValues.mSchoolCourseInfoBeanSmall!=null){
            GlideImgLoader.loadImageViewRadiusNoCenter(this,ConstValues.mSchoolCourseInfoBeanSmall.getImgUrl(),mIvIcon);
            mTvName.setText(ConstValues.mSchoolCourseInfoBeanSmall.getCourseName());
            mTvType1.setText(ConstValues.mSchoolCourseInfoBeanSmall.getLables().replace(",", "|"));
            mTvType2.setText("每组" + ConstValues.mSchoolCourseInfoBeanSmall.getGroupNum() + "人  |  共" + ConstValues.mSchoolCourseInfoBeanSmall.getStepNum() + "个步骤");
        }
        //班级信息
        mTvTitle.setText(ConstValues.mSchoolClassInfoBean.getClassName());
        rv_list.setAdapter(new HomeTwoXueShengAdapter(ConstValues.mSchoolClassInfoBean.getClassGroupList()));
    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.iv_back, R.id.tv_jishi, R.id.btn_kaishiyundong})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_jishi:
                break;
            case R.id.btn_kaishiyundong:
                MainActivity.indexPos = 1;
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
