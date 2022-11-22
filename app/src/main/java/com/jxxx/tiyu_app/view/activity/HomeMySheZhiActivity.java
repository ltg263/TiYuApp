package com.jxxx.tiyu_app.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jxxx.tiyu_app.MainActivity;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.api.RetrofitUtil;
import com.jxxx.tiyu_app.app.ConstValues;
import com.jxxx.tiyu_app.base.BaseActivity;
import com.jxxx.tiyu_app.base.Result;
import com.jxxx.tiyu_app.bean.UserInfoProfileBean;
import com.jxxx.tiyu_app.bean.VersionResponse;
import com.jxxx.tiyu_app.utils.GlideImgLoader;
import com.jxxx.tiyu_app.utils.SharedUtils;
import com.jxxx.tiyu_app.utils.ToastUtil;
import com.jxxx.tiyu_app.utils.view.DialogUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeMySheZhiActivity extends BaseActivity {

    @BindView(R.id.tv_userName)
    TextView mTvUserName;
    @BindView(R.id.tv_phonenumber)
    TextView mTvPhonenumber;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.tv_tuichu)
    TextView mTvTuichu;
    @BindView(R.id.tv_age)
    TextView mTvAge;
    @BindView(R.id.tv_banbenhao)
    TextView tv_banbenhao;
    @BindView(R.id.tv_schoolName)
    TextView mTvSchoolName;
    @BindView(R.id.iv_head)
    ImageView mIvHead;

    @Override
    public int intiLayout() {
        return R.layout.activity_home_my_she_zhi;
    }

    @Override
    public void initView() {
        tv_banbenhao.setText("V:"+ MainActivity.getVersionName(this));
    }

    @Override
    public void initData() {
        getSchoolTeacherCurrent();
    }


    @OnClick({R.id.tv_tuichu,R.id.iv_back, R.id.iv_head,R.id.tv_banbenhao})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_tuichu:
                DialogUtils.showDialogHint(this, "您确定要退出登录吗？",
                        false, new DialogUtils.ErrorDialogInterface() {
                            @Override
                            public void btnConfirm() {
                                deleteAuthLogout();
                            }
                        });
                break;
            case R.id.iv_head:
                break;
            case R.id.tv_banbenhao:
                getLast();
                break;
        }
    }

    private void getLast() {
        RetrofitUtil.getInstance().apiService()
                .getLast("1")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<VersionResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<VersionResponse> result) {
                        if(isResultOk(result)){
                            if(!result.getData().getVersionNo().equals(
                                    MainActivity.getVersionName(HomeMySheZhiActivity.this))){
                                DialogUtils.goUpdating(HomeMySheZhiActivity.this,result.getData());
                            }else{
                                ToastUtil.showShortToast(HomeMySheZhiActivity.this,"当前是最新版本");
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

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
                                GlideImgLoader.loadImageViewRadiusNoCenter(HomeMySheZhiActivity.this, userInfo.getAvatar(), mIvHead);
                                mTvUserName.setText(userInfo.getUserName());
                                mTvPhonenumber.setText(userInfo.getMobile());
                                mTvSex.setText(userInfo.getGender()==0?"男":"女");
                                mTvAge.setText(userInfo.getAge()+"");
                                mTvSchoolName.setText(userInfo.getSchoolName());
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

    private void deleteAuthLogout() {
        showLoading();
        RetrofitUtil.getInstance().apiService()
                .deleteAuthLogout()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result result) {
                        if (isResultOk(result)) {
                            SharedUtils.singleton().put(ConstValues.TOKEN, "");
                            startActivity(new Intent(HomeMySheZhiActivity.this, LoginActivity.class));
                            finish();
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
