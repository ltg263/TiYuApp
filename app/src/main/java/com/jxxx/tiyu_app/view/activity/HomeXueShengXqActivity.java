package com.jxxx.tiyu_app.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.api.RetrofitUtil;
import com.jxxx.tiyu_app.base.BaseActivity;
import com.jxxx.tiyu_app.base.Result;
import com.jxxx.tiyu_app.bean.SchoolStudentBean;
import com.jxxx.tiyu_app.bean.SchoolStudentDetailBean;
import com.jxxx.tiyu_app.utils.ChartHelper;
import com.jxxx.tiyu_app.utils.GlideImgLoader;
import com.jxxx.tiyu_app.utils.StringUtil;
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
    private List<Entry> mData = new ArrayList<>();

    @Override
    public int intiLayout() {
        return R.layout.activity_xuesheng_xiangq;
    }

    @Override
    public void initView() {
        mTvTitle.setText("同学详情");
        List<String> lists = new ArrayList<>();
        lists.add("");
        lists.add("");
        lists.add("");
        lists.add("");
        lists.add("");
        initVP(lists);

        ChartHelper.initChart(mData, mLineChart, -1);
        ChartHelper.addEntry(mData, mLineChart, 100);
        ChartHelper.addEntry(mData, mLineChart, 110);
        ChartHelper.addEntry(mData, mLineChart, 160);
        ChartHelper.addEntry(mData, mLineChart, 120);
        ChartHelper.addEntry(mData, mLineChart, 10);
        ChartHelper.addEntry(mData, mLineChart, 120);
        ChartHelper.addEntry(mData, mLineChart, 10);
        ChartHelper.addEntry(mData, mLineChart, 120);
        ChartHelper.addEntry(mData, mLineChart, 10);
        ChartHelper.addEntry(mData, mLineChart, 120);
        ChartHelper.addEntry(mData, mLineChart, 100);
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
                            mTvUserNo.setText("学号："+result.getData().getStudentNo());
                            mTvUserName.setText("姓名："+result.getData().getStudentName());
                            mTvUserAge.setText("年龄："+result.getData().getAge()+"岁");
                            if(StringUtil.isNotBlank(result.getData().getWeight())){
                                mTvUserAge.setText("年龄："+result.getData().getAge()+"岁  体重："+StringUtil.getValue(result.getData().getWeight())+"公斤");
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
