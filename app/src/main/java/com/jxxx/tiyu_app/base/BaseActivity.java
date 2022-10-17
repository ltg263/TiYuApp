package com.jxxx.tiyu_app.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.jxxx.tiyu_app.MainActivity;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.app.MainApplication;
import com.jxxx.tiyu_app.utils.SharedUtils;
import com.jxxx.tiyu_app.utils.StringUtil;
import com.jxxx.tiyu_app.utils.ToastUtil;
import com.jxxx.tiyu_app.utils.view.DialogUtils;
import com.jxxx.tiyu_app.utils.view.LoadingDialog;
import com.jxxx.tiyu_app.view.activity.HomeOneChuangJianSjActivity;
import com.jxxx.tiyu_app.view.activity.HomeOneChuangJianSj_YdActivity;
import com.jxxx.tiyu_app.view.activity.HomeTwoShangKeActivity;
import com.jxxx.tiyu_app.view.activity.HomeTwoXueShengActivity;
import com.jxxx.tiyu_app.view.activity.LoginActivity;
import com.jxxx.tiyu_app.wifi.WifiUtil;

import butterknife.ButterKnife;


/**
 * Created by Administrator on 2018/8/25.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();
    private LoadingDialog mLoading;

    protected String tag = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setStatusBarMode(this, true, R.color.transparent);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(intiLayout());
        ButterKnife.bind(this);

        //初始化控件
        initView();
        //设置数据
        initData();
        Log.w("this.getPackageName()","this.getPackageName()"+this.getLocalClassName());
        if(!getLocalClassName().contains("view.activity.device.")){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        }

        if(!(this instanceof HomeOneChuangJianSj_YdActivity)
                && !(this instanceof HomeOneChuangJianSjActivity)
                && !(this instanceof HomeTwoShangKeActivity)
                && !(this instanceof HomeTwoXueShengActivity)
                && !(this instanceof MainActivity) ){
            isWifiMeagerEsp();
        }
    }


    WifiInfo mWifiInfo;
    WifiUtil mWifiUtil;
    protected boolean isWifiMeagerEsp(){
        mWifiUtil = new WifiUtil(this);
        mWifiInfo = mWifiUtil.getWifiManager().getConnectionInfo();
        if(mWifiUtil.getWifiManager().isWifiEnabled() && mWifiInfo.getSSID().contains("ESP8266")){
            DialogUtils.showDialogHint(this, "请将WIFI连接到其他可用网络",
                    true, new DialogUtils.ErrorDialogInterface() {
                @Override
                public void btnConfirm() {
                    Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
            return true;
        }
        return false;
    }
    /**
     * 设置布局
     *
     * @return
     */
    public abstract int intiLayout();

    /**
     * 初始化布局
     */
    public abstract void initView();

    /**
     * 设置数据
     */
    public abstract void initData();
    public void setToolbar(Toolbar mToolbar, String title) {
        this.setToolbar(mToolbar, title, true,null);
    }
    public void setToolbarR(Toolbar mToolbar, String title,String strR) {
        this.setToolbar(mToolbar, title, true,strR);
    }

    public void setToolbar(Toolbar mToolbar, String title, Boolean isBack,String strR) {
        Log.w("strR","strR"+strR);
        TextView mViewToolBarTitle = mToolbar.findViewById(R.id.toolbar_title);
        mViewToolBarTitle.setText(title);
        if(StringUtil.isNotBlank(strR)){
            TextView tv_xz = mToolbar.findViewById(R.id.tv_xz);
            tv_xz.setVisibility(View.VISIBLE);
            tv_xz.setText(strR);
        }
        if (isBack) {
            mToolbar.setNavigationIcon(R.mipmap.back_b);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressedA();
                }
            });
        }
    }

    public void onBackPressedA(){
        finish();
    }

    public void readyGoActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public void showLoading() {
        if (mLoading != null && !mLoading.isShowing()) {
            mLoading.show();
        } else {
            mLoading = LoadingDialog.show(this, R.string.loading_text, false, null);
        }
    }

    public void showLoading(String name) {
        if (mLoading != null && !mLoading.isShowing()) {
            mLoading.show();
        } else {
            mLoading = LoadingDialog.show(this, name, false, null);
        }
    }


    public void hideLoading() {
        if (mLoading != null && mLoading.isShowing()) {
            mLoading.dismiss();
        }
    }



    public boolean isResultOk(Result mResult) {
        if(mResult.getCode()==200){
            return true;
        }
        String msg = mResult.getMsg();
        if(StringUtil.isBlank(msg)){
            msg = mResult.getCode()+"";
        }
        ToastUtil.showLongStrToast(MainApplication.getContext(),msg);
        return false;
    }
    /**
     * 点击屏幕隐藏软键盘方法
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    //处理Editext的光标隐藏、显示逻辑
                    v.clearFocus();
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideLoading();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(!(this instanceof LoginActivity) && StringUtil.isBlank(SharedUtils.getToken())){
            startActivity(new Intent(this,LoginActivity.class));
        }
        if(!(this instanceof HomeOneChuangJianSj_YdActivity)
                && !(this instanceof HomeOneChuangJianSjActivity)
                && !(this instanceof HomeTwoShangKeActivity)
                && !(this instanceof HomeTwoXueShengActivity)
                && !(this instanceof MainActivity) ){
            isWifiMeagerEsp();
        }
    }
}
