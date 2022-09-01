package com.jxxx.tiyu_app.view.fragment;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.api.RetrofitUtil;
import com.jxxx.tiyu_app.app.ConstValues;
import com.jxxx.tiyu_app.base.BaseFragment;
import com.jxxx.tiyu_app.base.Result;
import com.jxxx.tiyu_app.bean.SchoolClassBean;
import com.jxxx.tiyu_app.bean.UserInfoProfileBean;
import com.jxxx.tiyu_app.utils.GlideImgLoader;
import com.jxxx.tiyu_app.utils.SharedUtils;
import com.jxxx.tiyu_app.view.activity.HomeMySheZhiActivity;
import com.jxxx.tiyu_app.view.activity.HomeTwoXueShengListActivity;
import com.jxxx.tiyu_app.view.activity.HomeYiShangKeListActivity;
import com.jxxx.tiyu_app.view.adapter.HomeThreeListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeThreeFragment extends BaseFragment {


    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    HomeThreeListAdapter mHomeThreeListAdapter;
    @BindView(R.id.iv_head)
    ImageView mIvHead;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_user_phone)
    TextView mTvUserPhone;
    @BindView(R.id.tv_banji)
    TextView mTvBanji;
    @BindView(R.id.tv_xuesheng)
    TextView mTvXuesheng;
    @BindView(R.id.tv_yishangke)
    TextView mTvYishangke;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home_three;
    }

    @Override
    protected void initView() {
        mHomeThreeListAdapter = new HomeThreeListAdapter(null);
        mRvList.setAdapter(mHomeThreeListAdapter);
        mHomeThreeListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent mIntent = new Intent(getActivity(), HomeTwoXueShengListActivity.class);
                mIntent.putExtra("classId",mHomeThreeListAdapter.getData().get(position).getId());
                mIntent.putExtra("className",mHomeThreeListAdapter.getData().get(position).getClassName());
                startActivity(mIntent);
            }
        });
    }

    @Override
    protected void initData() {
        showLoading();
        getSchoolTeacherCurrent();
        getSchoolClassList();
    }

    @OnClick({R.id.ll_three_3, R.id.iv_shezhi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_three_3:
                startActivity(new Intent(getActivity(), HomeYiShangKeListActivity.class));
                break;
            case R.id.iv_shezhi:
                startActivity(new Intent(getActivity(), HomeMySheZhiActivity.class));
                break;
        }
    }

    private void getSchoolClassList() {
        RetrofitUtil.getInstance().apiService()
                .getSchoolClassList(SharedUtils.singleton().get(ConstValues.TEACHER_ID,""),
                        SharedUtils.singleton().get(ConstValues.SCHOOL_ID,""),0, ConstValues.PAGE_SIZE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<List<SchoolClassBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<List<SchoolClassBean>> result) {
                        if (isResultOk(result) && result.getData() != null) {
                            mHomeThreeListAdapter.setNewData(result.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void getSchoolTeacherCurrent() {
        RetrofitUtil.getInstance().apiService()
                .getSchoolTeacherCurrent()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<UserInfoProfileBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<UserInfoProfileBean> result) {
                        if (isResultOk(result) && result.getData() != null) {
                            UserInfoProfileBean userInfo = result.getData();
                            if (userInfo != null) {
                                GlideImgLoader.loadImageViewRadiusNoCenter(mContext, userInfo.getAvatar(), mIvHead);
                                mTvUserName.setText(userInfo.getUserName());
                                mTvUserPhone.setText(userInfo.getMobile());
                                mTvBanji.setText("0");
                                mTvXuesheng.setText("0");
                                mTvYishangke.setText("0");
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

}



