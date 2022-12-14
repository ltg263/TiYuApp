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

    public void setNotifyDataSetChanged(int pos){
        if(mHomeTwoTwoListAdapter!=null){
            mHomeTwoTwoListAdapter.setCurrentPos(pos);
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
        System.out.println("HomeTwoListFragment1:"+HomeTwoXueShengActivity.mMapSchoolStudentBeans);
        if(HomeTwoXueShengActivity.mMapSchoolStudentBeans!=null){
            mHomeTwoTwoListAdapter = new HomeTwoTwoListAdapter(HomeTwoXueShengActivity.mMapSchoolStudentBeans.get(id));
            mRvTwoList.setAdapter(mHomeTwoTwoListAdapter);
        }
    }

    @Override
    protected void initData() {
        System.out.println("HomeTwoListFragment2:"+ConstValues.mSchoolCourseInfoBeanSmall);
        if(ConstValues.mSchoolCourseInfoBean!=null){//???????????????
            List<SchoolCourseBean.CourseSectionVoListBean> mCourseSectionVoLists = ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList();
            if(mCourseSectionVoLists!=null && mCourseSectionVoLists.size()>HomeTwoXueShengActivity.current_course_section){
                SchoolCourseBean.CourseSectionVoListBean mCourseSectionVoList
                        = mCourseSectionVoLists.get(HomeTwoXueShengActivity.current_course_section);
                SchoolCourseBeanSmall mSmallCourseVo = mCourseSectionVoList.getSmallCourseVo();
                if(mSmallCourseVo != null){
                    GlideImgLoader.loadImageViewRadiusNoCenter(mContext,mSmallCourseVo.getImgUrl(),mIvIcon);
                    mTvName.setText(mSmallCourseVo.getCourseName());
                    mTvType1.setText(mSmallCourseVo.getLables().replace(",", "|"));
                    mTvType2.setText("???"+mSmallCourseVo.getQueueNum()+"?????????   |  ???" + mSmallCourseVo.getStepNum() + "?????????");
                }
            }
        }else{//???????????????
            if(ConstValues.mSchoolCourseInfoBeanSmall != null){
                GlideImgLoader.loadImageViewRadiusNoCenter(mContext,ConstValues.mSchoolCourseInfoBeanSmall.getImgUrl(),mIvIcon);
                mTvName.setText(ConstValues.mSchoolCourseInfoBeanSmall.getCourseName());
                mTvType1.setText(ConstValues.mSchoolCourseInfoBeanSmall.getLables().replace(",", "|"));
                mTvType2.setText("???"+ConstValues.mSchoolCourseInfoBeanSmall.getQueueNum()+"?????????   |  ???" + ConstValues.mSchoolCourseInfoBeanSmall.getStepNum() + "?????????");
            }
        }
    }
}
