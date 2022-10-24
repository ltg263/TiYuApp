package com.jxxx.tiyu_app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.jxxx.tiyu_app.MainActivity;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.app.ConstValues;
import com.jxxx.tiyu_app.base.BaseActivity;
import com.jxxx.tiyu_app.bean.SchoolClassBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBeanSmallActionInfoJson;
import com.jxxx.tiyu_app.bean.SchoolStudentBean;
import com.jxxx.tiyu_app.utils.GlideImgLoader;
import com.jxxx.tiyu_app.view.adapter.HomeTwoXueShengAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            mTvType2.setText("共" + ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().size() + "个小节");
        }
        if(ConstValues.mSchoolCourseInfoBeanSmall!=null){
            GlideImgLoader.loadImageViewRadiusNoCenter(this,ConstValues.mSchoolCourseInfoBeanSmall.getImgUrl(),mIvIcon);
            mTvName.setText(ConstValues.mSchoolCourseInfoBeanSmall.getCourseName());
            mTvType1.setText(ConstValues.mSchoolCourseInfoBeanSmall.getLables().replace(",", "|"));
            mTvType2.setText("共"+ConstValues.mSchoolCourseInfoBeanSmall.getQueueNum()+"个队列  |  共" + ConstValues.mSchoolCourseInfoBeanSmall.getStepNum() + "个步骤");
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
                current_course_section = 0;
                current_course_section_loop_num = 0;
                initYuDongData();
                MainActivity.indexPos = 1;
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    //开始运动前 初始所有数据；
    /**
     * Map队列的KEY
     */
    public static List<String> mMapKey_id = new ArrayList<>();
    /**
     * 学生、队列、每个学生的运动步骤
     */
    public static Map<String,List<SchoolStudentBean>> mMapSchoolStudentBeans;
    /**
     * 步骤的下标
     */
    public static List<String> mDataList = new ArrayList<>();
    /**
     * 当前小节课程的下标
     */
    public static int current_course_section = 0;
    /**
     * 当前小节课程的中步骤循环执行的次数
     */
    public static  int current_course_section_loop_num = 0;


    public static boolean initYuDongData() {
        String actionInfo = null;
        if(ConstValues.mSchoolCourseInfoBean!=null){
            SchoolCourseBean.CourseSectionVoListBean mCourseSectionVoList = ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().get(current_course_section);
            actionInfo = mCourseSectionVoList.getSmallCourseVo().getActionInfo();
            current_course_section_loop_num = mCourseSectionVoList.getLoopNum();
        }else{
            current_course_section_loop_num = 1;
            actionInfo = ConstValues.mSchoolCourseInfoBeanSmall.getActionInfo();
        }
        mDataList.clear();
        mMapKey_id.clear();
        mMapSchoolStudentBeans = new HashMap<>();
        List<SchoolClassBean.ClassGroupListBean> mClassGroupLists = ConstValues.mSchoolClassInfoBean.getClassGroupList();
        for (int i=0;i<mClassGroupLists.size();i++){
            List<SchoolStudentBean> mSchoolStudentBeans= new ArrayList<>();
            for(int j=0;j<ConstValues.mSchoolStudentInfoBean.size();j++){
                if(mClassGroupLists.get(i).getStudentIds().contains(ConstValues.mSchoolStudentInfoBean.get(j).getId())
                        && !ConstValues.mSchoolStudentInfoBean.get(j).isAskForLeave()){
                    SchoolStudentBean mSchoolStudentBean = ConstValues.mSchoolStudentInfoBean.get(j);
                    mSchoolStudentBean.setCurrentStepNo(0);
                    mSchoolStudentBean.setPostWccs(0);
                    mSchoolStudentBean.setPostZjzs(0);
                    mSchoolStudentBean.setPostZys(0);
                    mSchoolStudentBean.setPostPjsd(0);
                    List<SchoolCourseBeanSmallActionInfoJson> json = JSON.parseArray(actionInfo,SchoolCourseBeanSmallActionInfoJson.class);
                    if(json!=null && i<=json.size()-1 && json.get(i)!=null){
                        List<SchoolCourseBeanSmallActionInfoJson.StepsBean> mSteps = json.get(i).getSteps();
                        mSchoolStudentBean.setSteps(mSteps);
                        List<Byte> allQiuNo = new ArrayList<>();
                        for(int a = 0;a<mSteps.size();a++){
                            List<List<Byte>> mSets = mSteps.get(a).getSets();
                            if(mSets!=null){
                                mSteps.get(a).setStepNoOkNum(0);
                                for(int b = 0;b<mSets.size();b++){
                                    if(!allQiuNo.contains(mSets.get(b).get(1))){
                                        allQiuNo.add(mSets.get(b).get(1));
                                    }
                                }
                            }
                        }
                        mSchoolStudentBean.setAllQiuNo(allQiuNo);
                    } else {
                        mSchoolStudentBean.setSteps(null);
                    }
                    mSchoolStudentBeans.add(ConstValues.mSchoolStudentInfoBean.get(j));
                }
            }
            mDataList.add("队列"+(i+1));
            mMapKey_id.add(mClassGroupLists.get(i).getId());
            mMapSchoolStudentBeans.put(mClassGroupLists.get(i).getId(),mSchoolStudentBeans);
        }

        return mMapSchoolStudentBeans.size()>0;
    }
}
