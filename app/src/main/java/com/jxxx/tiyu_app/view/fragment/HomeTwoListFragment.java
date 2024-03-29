package com.jxxx.tiyu_app.view.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.app.ConstValues;
import com.jxxx.tiyu_app.base.BaseFragment;
import com.jxxx.tiyu_app.bean.SchoolCourseBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBeanSmall;
import com.jxxx.tiyu_app.utils.GlideImgLoader;
import com.jxxx.tiyu_app.view.activity.HomeTwoXueShengActivity;
import com.jxxx.tiyu_app.view.adapter.HomeTwoTwoListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class HomeTwoListFragment extends BaseFragment {

    @BindView(R.id.iv_icon)
    ImageView mIvIcon;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_type_1)
    TextView mTvType1;
    @BindView(R.id.tv_type_2)
    TextView mTvType2;
    @BindView(R.id.rv_two_list)
    RecyclerView mRvTwoList;
    HomeTwoTwoListAdapter mHomeTwoTwoListAdapter;

    public void setNotifyDataSetChanged(int pos, List<Integer> current_class_group_lists){
        if(mHomeTwoTwoListAdapter!=null){
            mHomeTwoTwoListAdapter.setCurrentPos(pos);
            mHomeTwoTwoListAdapter.setCurrent_class_group_lists(current_class_group_lists);
            mHomeTwoTwoListAdapter.notifyDataSetChanged();
        }
    }

    public HomeTwoTwoListAdapter getHomeTwoTwoListAdapter() {
        return mHomeTwoTwoListAdapter;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home_two_list;
    }

    @Override
    protected void initView() {
        String id = getArguments().getString("mMapKey");
        int pos = getArguments().getInt("pos",0);
        System.out.println("HomeTwoListFragment1:"+HomeTwoXueShengActivity.mMapSchoolStudentBeans);
        if(HomeTwoXueShengActivity.mMapSchoolStudentBeans!=null){
            mHomeTwoTwoListAdapter = new HomeTwoTwoListAdapter(HomeTwoXueShengActivity.mMapSchoolStudentBeans.get(id));
            mHomeTwoTwoListAdapter.setPos(pos);
            mRvTwoList.setAdapter(mHomeTwoTwoListAdapter);
        }
    }

    @Override
    protected void initData() {
        System.out.println("HomeTwoListFragment2:"+ConstValues.mSchoolCourseInfoBeanSmall);
        if(ConstValues.mSchoolCourseInfoBean!=null){//大课程信息
            List<SchoolCourseBean.CourseSectionVoListBean> mCourseSectionVoLists = ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList();
            if(mCourseSectionVoLists!=null && mCourseSectionVoLists.size()>HomeTwoXueShengActivity.current_course_section){
                SchoolCourseBean.CourseSectionVoListBean mCourseSectionVoList
                        = mCourseSectionVoLists.get(HomeTwoXueShengActivity.current_course_section);
                SchoolCourseBeanSmall mSmallCourseVo = mCourseSectionVoList.getSmallCourseVo();
                if(mSmallCourseVo != null){
                    GlideImgLoader.loadImageViewRadiusNoCenter(mContext,mSmallCourseVo.getImgUrl(),mIvIcon);
                    mTvName.setText(mSmallCourseVo.getCourseName());
                    mTvType1.setText(mSmallCourseVo.getLables().replace(",", "|"));
                    mTvType2.setText("共"+mSmallCourseVo.getQueueNum()+"个队列   |  共" + mSmallCourseVo.getStepNum() + "个步骤");
                }
            }
        }else{//小课程信息
            if(ConstValues.mSchoolCourseInfoBeanSmall != null){
                GlideImgLoader.loadImageViewRadiusNoCenter(mContext,ConstValues.mSchoolCourseInfoBeanSmall.getImgUrl(),mIvIcon);
                mTvName.setText(ConstValues.mSchoolCourseInfoBeanSmall.getCourseName());
                mTvType1.setText(ConstValues.mSchoolCourseInfoBeanSmall.getLables().replace(",", "|"));
                mTvType2.setText("共"+ConstValues.mSchoolCourseInfoBeanSmall.getQueueNum()+"个队列   |  共" + ConstValues.mSchoolCourseInfoBeanSmall.getStepNum() + "个步骤");
            }
        }
    }
}
