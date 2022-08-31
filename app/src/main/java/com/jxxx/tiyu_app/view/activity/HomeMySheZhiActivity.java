package com.jxxx.tiyu_app.view.activity;

import android.content.Intent;
import android.view.View;

import com.jxxx.tiyu_app.MainActivity;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.api.RetrofitUtil;
import com.jxxx.tiyu_app.app.ConstValues;
import com.jxxx.tiyu_app.base.BaseActivity;
import com.jxxx.tiyu_app.base.Result;
import com.jxxx.tiyu_app.bean.AuthLoginBean;
import com.jxxx.tiyu_app.utils.SharedUtils;
import com.jxxx.tiyu_app.utils.view.DialogUtils;

import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeMySheZhiActivity extends BaseActivity {

    @Override
    public int intiLayout() {
        return R.layout.activity_home_my_she_zhi;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.tv_tuichu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tuichu:
                DialogUtils.showDialogHint(this, "您确定要退出登录吗？",
                        false, new DialogUtils.ErrorDialogInterface() {
                    @Override
                    public void btnConfirm() {
                        deleteAuthLogout();
                    }
                });
                break;
        }
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
                        if(isResultOk(result)){
                            SharedUtils.singleton().put(ConstValues.TOKEN,"");
                            startActivity(new Intent(HomeMySheZhiActivity.this,LoginActivity.class));
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
