package com.jxxx.tiyu_app.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.EditText;

import androidx.core.app.ActivityCompat;

import com.jxxx.tiyu_app.MainActivity;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.api.RetrofitUtil;
import com.jxxx.tiyu_app.app.ConstValues;
import com.jxxx.tiyu_app.app.MainApplication;
import com.jxxx.tiyu_app.base.BaseActivity;
import com.jxxx.tiyu_app.base.Result;
import com.jxxx.tiyu_app.bean.AuthLoginBean;
import com.jxxx.tiyu_app.bean.VersionResponse;
import com.jxxx.tiyu_app.tcp_tester.SelectActivity;
import com.jxxx.tiyu_app.utils.SharedUtils;
import com.jxxx.tiyu_app.utils.StringUtil;
import com.jxxx.tiyu_app.utils.ToastUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_user)
    EditText mEtUser;
    @BindView(R.id.et_pas)
    EditText mEtPas;

    public static void startActivityIntent(Context mContext) {
        mContext.startActivity(new Intent(mContext, LoginActivity.class));
    }

    @Override
    public int intiLayout() {
        MainApplication.getContext().AppExit();
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        String userName = SharedUtils.singleton().get(ConstValues.USER_NAME,"");
        String userPas = SharedUtils.singleton().get(ConstValues.USER_PAS,"");
        if(StringUtil.isNotBlank(userName)){
            mEtUser.setText(userName);
        }
        if(StringUtil.isNotBlank(userPas)){
            mEtPas.setText(userPas);
        }
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.btn_login)
    public void onClick() {
//        if(true){
//            startActivity(new Intent(this, SelectActivity.class));
//            return;
//        }
        String[] permissions = new String[]{Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.CHANGE_NETWORK_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, permissions[1]) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, permissions[2]) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, permissions[3]) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, permissions[4]) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, permissions[5]) != PackageManager.PERMISSION_GRANTED) {
//            String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, 1);
            return;
        }
        postAuthLogin();
    }

    private void postAuthLogin() {
        String username = mEtUser.getText().toString();
        String password = mEtPas.getText().toString();
        if(StringUtil.isBlank(username) || StringUtil.isBlank(password)){
            ToastUtil.showLongStrToast(this,"账号或密码不能为空");
            return;
        }
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", username);
        paramMap.put("password", password);
        paramMap.put("userType", "sys_user");
        showLoading();
        RetrofitUtil.getInstance().apiService()
                .postAuthLogin(RetrofitUtil.createJsonRequest(paramMap))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<AuthLoginBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<AuthLoginBean> result) {
                        if(isResultOk(result)){
                            SharedUtils.singleton().put(ConstValues.TOKEN,result.getData().getAccess_token());
                            SharedUtils.singleton().put(ConstValues.TEACHER_ID,result.getData().getTeacherId());
                            SharedUtils.singleton().put(ConstValues.SCHOOL_ID,result.getData().getSchoolId());
                            SharedUtils.singleton().put(ConstValues.USER_ID,result.getData().getUserId());
                            SharedUtils.singleton().put(ConstValues.USER_NAME,username);
                            SharedUtils.singleton().put(ConstValues.USER_PAS,password);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
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
