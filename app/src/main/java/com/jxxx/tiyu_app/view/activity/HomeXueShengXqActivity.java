package com.jxxx.tiyu_app.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.api.RetrofitUtil;
import com.jxxx.tiyu_app.base.BaseActivity;
import com.jxxx.tiyu_app.base.Result;
import com.jxxx.tiyu_app.bean.SchoolStudentDetailBean;
import com.jxxx.tiyu_app.utils.ChartHelper;
import com.jxxx.tiyu_app.utils.GlideImgLoader;
import com.jxxx.tiyu_app.utils.StringUtil;
import com.jxxx.tiyu_app.view.adapter.HomeXueShengXqAdapter;
import com.jxxx.tiyu_app.view.fragment.HomeBanJiFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeXueShengXqActivity extends BaseActivity {
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_head)
    ImageView mIvHead;
    @BindView(R.id.tv_user_no)
    TextView mTvUserNo;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_user_age)
    TextView mTvUserAge;
    @BindView(R.id.line_chart)
    LineChart mLineChart;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.tv_timeUse)
    TextView mTvTimeUse;
    @BindView(R.id.tv_speed)
    TextView mTvSpeed;
    @BindView(R.id.tv_finishTimes)
    TextView mTvFinishTimes;
    @BindView(R.id.tv_times)
    TextView mTvTimes;
    private List<Entry> mData = new ArrayList<>();
    HomeXueShengXqAdapter mHomeXueShengXqAdapter;

    @Override
    public int intiLayout() {
        return R.layout.activity_xuesheng_xiangq;
    }

    @Override
    public void initView() {
        mTvTitle.setText("同学详情");

        mHomeXueShengXqAdapter = new HomeXueShengXqAdapter(null);
        rv_list.setAdapter(mHomeXueShengXqAdapter);
//        initVP(lists);
        mHomeXueShengXqAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<SchoolStudentDetailBean.StudentClassRecordsBean.StudentResultsListBean> mStudentResultsList
                        = mHomeXueShengXqAdapter.getData().get(position).getStudentResultsList();
                if(mStudentResultsList !=null && mStudentResultsList.size()>0){
                    mTvTimeUse.setText(mStudentResultsList.get(0).getTimeUse()+"s");
                    mTvTimes.setText(mStudentResultsList.get(0).getTimes()+"次");
                    mTvFinishTimes.setText(mStudentResultsList.get(0).getFinishTimes()+"次");
                    mTvSpeed.setText(mStudentResultsList.get(0).getSpeed()+"s");
                    ChartHelper.initChart(mData, mLineChart, mStudentResultsList.get(0).getTimeUse());
                    List<Float> mMotionFrequency = mStudentResultsList.get(0).getMotionFrequency();
                    if(mMotionFrequency!=null && mMotionFrequency.size()>0){
                        for(int i=0;i<mMotionFrequency.size();i++){
                            ChartHelper.addEntry(mData, mLineChart, mMotionFrequency.get(i));
                        }
                    }
                }else{
                    ChartHelper.initChart(mData, mLineChart, 10);
                }
            }
        });
    }

    @Override
    public void initData() {
        showLoading();
        RetrofitUtil.getInstance().apiService()
                .getSchoolStudentDetail(getIntent().getStringExtra("id"))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<SchoolStudentDetailBean>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<SchoolStudentDetailBean> result) {
                        if (isResultOk(result) && result.getData() != null) {
                            GlideImgLoader.loadImageViewRadiusNoCenter(HomeXueShengXqActivity.this, result.getData().getImgUrl(), mIvHead);
                            mTvUserNo.setText("学号：" + result.getData().getStudentNo());
                            mTvUserName.setText("姓名：" + result.getData().getStudentName());
                            mTvUserAge.setText("年龄：" + result.getData().getAge() + "岁");

                            ChartHelper.initChart(mData, mLineChart, 10);
                            if (StringUtil.isNotBlank(result.getData().getWeight())) {
                                mTvUserAge.setText("年龄：" + result.getData().getAge() + "岁  体重：" + StringUtil.getValue(result.getData().getWeight()) + "公斤");
                            }
                            if (result.getData().getStudentClassRecords() != null && result.getData().getStudentClassRecords().size()>0) {
                                mHomeXueShengXqAdapter.setNewData(result.getData().getStudentClassRecords());
                                if(result.getData().getStudentClassRecords().get(0).getStudentResultsList() !=null
                                        &&result.getData().getStudentClassRecords().get(0).getStudentResultsList().size()>0){

                                    ChartHelper.initChart(mData, mLineChart, result.getData()
                                            .getStudentClassRecords().get(0).getStudentResultsList().get(0).getTimeUse());
                                    mTvTimeUse.setText(result.getData().getStudentClassRecords().get(0)
                                            .getStudentResultsList().get(0).getTimeUse()+"s");
                                    mTvTimes.setText(result.getData().getStudentClassRecords().get(0)
                                            .getStudentResultsList().get(0).getTimes()+"次");
                                    mTvFinishTimes.setText(result.getData().getStudentClassRecords().get(0)
                                            .getStudentResultsList().get(0).getFinishTimes()+"次");
                                    mTvSpeed.setText(result.getData().getStudentClassRecords().get(0)
                                            .getStudentResultsList().get(0).getSpeed()+"s");
                                    List<Float> mMotionFrequency = result.getData().getStudentClassRecords().get(0).getStudentResultsList().get(0).getMotionFrequency();
                                    if(mMotionFrequency!=null && mMotionFrequency.size()>0){
                                        for(int i=0;i<mMotionFrequency.size();i++){
                                            ChartHelper.addEntry(mData, mLineChart, mMotionFrequency.get(i));
                                        }
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                    }
                });
    }


    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void initVP(List<String> list) {
        getFragments(list);
        mViewPager.setOffscreenPageLimit(list.size());
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public float getPageWidth(int position) {
                return (float) 0.4;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return "";
            }
        });
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    List<Fragment> fragments = new ArrayList<>();

    private List<Fragment> getFragments(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            HomeBanJiFragment fragment = new HomeBanJiFragment();
            fragments.add(fragment);
        }
        return fragments;
    }
}
