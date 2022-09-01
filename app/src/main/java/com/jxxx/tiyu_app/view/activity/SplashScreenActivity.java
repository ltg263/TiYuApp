package com.jxxx.tiyu_app.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jxxx.tiyu_app.MainActivity;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.api.RetrofitUtil;
import com.jxxx.tiyu_app.app.ConstValues;
import com.jxxx.tiyu_app.app.MainApplication;
import com.jxxx.tiyu_app.base.Result;
import com.jxxx.tiyu_app.bean.UserInfoProfileBean;
import com.jxxx.tiyu_app.utils.GlideImgLoader;
import com.jxxx.tiyu_app.utils.SharedUtils;
import com.jxxx.tiyu_app.utils.StringUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.splashscreen);
        new Handler().postDelayed(() -> getSchoolTeacherCurrent(), 2000);
    }

    private void startUi(boolean isLogin) {
        MainApplication.addActivity(this);
        if(!isLogin){
            startActivity(new Intent(this, MainActivity.class));
        }else{
            LoginActivity.startActivityIntent(this);
        }
        finish();
    }
    private void getSchoolTeacherCurrent() {
        if(StringUtil.isBlank(SharedUtils.getToken())){
            startUi(true);
            return;
        }
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
                        if (result.getCode()==200 && result.getData() != null) {
                            startUi(false);
                        }else{
                            startUi(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        startUi(true);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


}
