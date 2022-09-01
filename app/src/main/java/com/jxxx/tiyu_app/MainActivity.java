package com.jxxx.tiyu_app;

import android.Manifest;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jxxx.tiyu_app.api.RetrofitUtil;
import com.jxxx.tiyu_app.app.MainApplication;
import com.jxxx.tiyu_app.base.BaseActivity;
import com.jxxx.tiyu_app.base.Result;
import com.jxxx.tiyu_app.bean.DictDataTypeBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBean;
import com.jxxx.tiyu_app.bean.VersionResponse;
import com.jxxx.tiyu_app.utils.StringUtil;
import com.jxxx.tiyu_app.utils.ToastUtil;
import com.jxxx.tiyu_app.utils.view.DialogUtils;
import com.jxxx.tiyu_app.view.activity.HomeTwoShangKeActivity;
import com.jxxx.tiyu_app.view.activity.LoginActivity;
import com.jxxx.tiyu_app.view.fragment.HomeOneFragment;
import com.jxxx.tiyu_app.view.fragment.HomeThreeFragment;
import com.jxxx.tiyu_app.view.fragment.HomeTwoFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends BaseActivity {
    @BindView(R.id.bnv_home_navigation)
    BottomNavigationView mBnvHomeNavigation;
    @BindView(R.id.ma_iv_index)
    ImageView ma_iv_index;
    private Fragment mFragment;
    private HomeTwoFragment mHomeTwoFragment;
    private HomeOneFragment mHomeOneFragment;
    private HomeThreeFragment mHomeThreeFragment;
    public static int indexPos = 0;
    public static Map<String,List<DictDataTypeBean>> mDictDataTypeBeans;

    @Override
    public int intiLayout() {
        return R.layout.activity_main;
    }

    public static String[] params = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static final int PERMISSION_CAMERA = 110;
    @Override
    public void initView() {
        mDictDataTypeBeans = new HashMap<>();
        getDictDataType("sys_age_range");//年龄
        getDictDataType("sys_train_type");//类型
        getDictDataType("sys_train_part");//训练部位
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//保持屏幕唤醒
        MainApplication.addActivity(this);
        initBottomBar();

        if (EasyPermissions.hasPermissions(this, params)) {
            //具备权限 直接进行操作
        } else {
            //权限拒绝 申请权限
            EasyPermissions.requestPermissions(this, "为了您更好使用本应用，请允许应用获取以下权限", PERMISSION_CAMERA, params);
        }
    }

    @Override
    public void initData() {
        ma_iv_index.getDrawable().setLevel(0);
        getSchoolCourseQueryCourse();
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
//                            if(!result.getData().getVersionNo().equals(getVersionName(MainActivity.this))){
//                                DialogUtils.goUpdating(MainActivity.this,result.getData());
//                            }
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
    private void getSchoolCourseQueryCourse() {
        showLoading();
        RetrofitUtil.getInstance().apiService()
                .getSchoolCourseQueryCourse()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<SchoolCourseBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<SchoolCourseBean> result) {
                        if(isResultOk(result)){
                            if(result.getData()!=null){
                                showDialogKaiShiShangKe(result.getData());
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
    private void showDialogKaiShiShangKe(SchoolCourseBean mSchoolCourseBean) {
        DialogUtils.showDialogKaiShiShangKe(this, mSchoolCourseBean,
                new DialogUtils.ErrorDialogInterfaceA() {
                    @Override
                    public void btnConfirm(int index) {
                        HomeTwoShangKeActivity.startActivityIntent(MainActivity.this,mSchoolCourseBean.getId(),false);
                    }
                });
    }

    private void initBottomBar() {
        mHomeOneFragment = new HomeOneFragment();
        mHomeTwoFragment = new HomeTwoFragment();
        mHomeThreeFragment = new HomeThreeFragment();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frameLayout, mHomeOneFragment).commit();

        mFragment = mHomeOneFragment;

        // 不使用图标默认变色
        mBnvHomeNavigation.setItemIconTintList(null);
        mBnvHomeNavigation.setOnNavigationItemSelectedListener(item -> {
            if(indexPos==1){
                ToastUtil.showShortToast(MainActivity.this,"请先断开课程链接");
                return false;
            }
            switch (item.getItemId()){
                case R.id.menu_home_1:
                    indexPos = 0;
                    switchFragment(mHomeOneFragment);
                    break;
                case R.id.menu_home_2:
                    indexPos = 1;
                    switchFragment(mHomeTwoFragment);
                    break;
                case R.id.menu_home_3:
                    indexPos = 2;
                    switchFragment(mHomeThreeFragment);
                    break;
            }
            return true;
        });
        mBnvHomeNavigation.setSelectedItemId(R.id.menu_home_1);

        ma_iv_index.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(indexPos!=1){
                    ToastUtil.showShortToast(MainActivity.this,"请先链接课程");
                    getSchoolCourseQueryCourse();
                    return;
                }
                if(ma_iv_index.getDrawable().getLevel()==1){
                    ma_iv_index.getDrawable().setLevel(2);
                    if(mHomeTwoFragment!=null){
                        mHomeTwoFragment.startOrStop(false);
                    }

                }else{
                    if(mHomeTwoFragment!=null){
                        boolean isStart = mHomeTwoFragment.startOrStop(true);
                        if(isStart){
                            ma_iv_index.getDrawable().setLevel(1);
                        }
                    }

                }
            }
        });
    }

    public void setFragmentStartOrStop(){
        if(mHomeTwoFragment!=null && ma_iv_index!=null){
            ma_iv_index.getDrawable().setLevel(2);
            mHomeTwoFragment.startOrStop(false);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        switch (indexPos){
            case 0:
                mBnvHomeNavigation.setSelectedItemId(R.id.menu_home_1);
                switchFragment(mHomeOneFragment);
                break;
            case 1:
                mBnvHomeNavigation.setSelectedItemId(R.id.menu_home_2);
                switchFragment(mHomeTwoFragment);
                ma_iv_index.getDrawable().setLevel(2);
                mHomeTwoFragment.startOrStop(false);
                break;
            case 2:
                mBnvHomeNavigation.setSelectedItemId(R.id.menu_home_3);
                switchFragment(mHomeThreeFragment);
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void switchFragment(Fragment fragment) {
        //判断当前显示的Fragment是不是切换的Fragment
        if (mFragment != fragment) {
            if (!fragment.isAdded()) {
                //如果没有，则先把当前的Fragment隐藏，把切换的Fragment添加上
                getSupportFragmentManager().beginTransaction().hide(mFragment).add(R.id.frameLayout, fragment).commit();
            } else {
                //如果已经添加过，则先把当前的Fragment隐藏，把切换的Fragment显示出来
                getSupportFragmentManager().beginTransaction().hide(mFragment).show(fragment).commit();
            }
            mFragment = fragment;
        }
    }

    @Override
    public void onBackPressed() {
        ToastUtil.showToast("再按一次退出程序");
        if (isSlowDoubleClick()) {
            this.finish();
            System.exit(0);
        } else {

        }
    }
    private static long lastClickTime = 0;
    public static boolean isSlowDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 2000) {
            return true;
        }

        lastClickTime = time;

        return false;
    }

    /**
     * 获取筛选的条件
     */
    private void getDictDataType(String dictType) {
        RetrofitUtil.getInstance().apiService()
                .getDictDataType(dictType)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<List<DictDataTypeBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<List<DictDataTypeBean>> result) {
                        if(isResultOk(result)){
                            mDictDataTypeBeans.put(dictType,result.getData());
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
}