package com.jxxx.tiyu_app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
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
import com.jxxx.tiyu_app.app.ConstValues;
import com.jxxx.tiyu_app.app.MainApplication;
import com.jxxx.tiyu_app.base.BaseActivity;
import com.jxxx.tiyu_app.base.Result;
import com.jxxx.tiyu_app.bean.CourseTypeListAllBean;
import com.jxxx.tiyu_app.bean.DictDataTypeBean;
import com.jxxx.tiyu_app.bean.SceduleCourseBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBean;
import com.jxxx.tiyu_app.bean.VersionResponse;
import com.jxxx.tiyu_app.loginfo.LogcatHelper;
import com.jxxx.tiyu_app.tcp_tester.ConstValuesHttps;
import com.jxxx.tiyu_app.utils.HttpRequestUtils;
import com.jxxx.tiyu_app.utils.StringUtil;
import com.jxxx.tiyu_app.utils.ToastUtil;
import com.jxxx.tiyu_app.utils.view.DialogUtils;
import com.jxxx.tiyu_app.view.activity.HomeOneChuangJianSjActivity;
import com.jxxx.tiyu_app.view.activity.HomeTwoShangKeActivity;
import com.jxxx.tiyu_app.view.activity.LoginActivity;
import com.jxxx.tiyu_app.view.fragment.HomeOneFragment;
import com.jxxx.tiyu_app.view.fragment.HomeThreeFragment;
import com.jxxx.tiyu_app.view.fragment.HomeTwoFragment;

import java.io.File;
import java.text.DecimalFormat;
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
    public static Map<String,List<CourseTypeListAllBean>> mCourseTypeListAllBeans;

    @Override
    public int intiLayout() {
        return R.layout.activity_main;
    }

    public static String[] params = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static final int PERMISSION_CAMERA = 110;
    @Override
    public void initView() {
        mDictDataTypeBeans = new HashMap<>();
        mCourseTypeListAllBeans = new HashMap<>();
        HomeOneChuangJianSjActivity.getDictDataType();
        //大课程
        // 'sys_grade', //年级
        // 'sys_content_type', //教学内容
        // 'sys_category', //大类别
        // 'sys_theme', //主题内容
        //小课程
        // 'sys_grade', //年级
        // 'sys_content_type', //教学内容
        // 'sys_process_type', //流程
        // 'sys_train_type', //核心指标
        getDictDataType("sys_grade");//年级
        getDictDataType("sys_content_type");//教学内容
        getDictDataType("sys_category");//大类别
        getDictDataType("sys_theme"); //主题内容
        getDictDataType("sys_process_type"); //流程
        getDictDataType("sys_train_type"); //核心指标

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//保持屏幕唤醒
        MainApplication.getContext().addActivity(this);
        initBottomBar();

        if (EasyPermissions.hasPermissions(this, params)) {
            //具备权限 直接进行操作
        } else {
            //权限拒绝 申请权限
            EasyPermissions.requestPermissions(this, "为了您更好使用本应用，请允许应用获取以下权限", PERMISSION_CAMERA, params);
        }
        postUploadFile();
    }

    private void postUploadFile() {
        File file = new File(ConstValues.PATH_LOGCAT);
        Log.w("postUploadFile","pathLogCat"+ConstValues.PATH_LOGCAT);
        if(file.exists() && file.isDirectory()){
            File[] files = file.listFiles(); // 声明目录下所有的文件 files[];

            if(files!=null && files.length>0){
                uploadFiles(0,files);
            }
        }
    }
    private void uploadFiles(int pos, File[] files) {
        DecimalFormat df = new DecimalFormat("#.00");
        String size = df.format((double) files[pos].length() / 1024);
        Log.w("mFile","size:"+size);
        if(Double.parseDouble(size)<1){
            if(pos == files.length-1){
                for (int i = files.length-1; i >= 0; i--) { // 遍历目录下所有的文件
                    if(!files[i].getName().contains(LogcatHelper.LOGCAT_FILE_NAME)){
                        files[i].delete();
                    }
                }
                Log.w("mFile","parseDouble删除成功:");
                return;
            }
            uploadFiles(pos + 1,files);
            return;
        }
        HttpRequestUtils.uploadFiles(files[pos].getPath(), getVersionName(this),new HttpRequestUtils.UploadFileInterface() {
            @Override
            public void succeed(String path) {
                Log.w(LogcatHelper.MESSAGE_LOG,"LOG文件已上传："+files[pos].getPath());
                Log.w("mFile","上传成功:"+path);
                if(pos == files.length-1){
                    for (int i = files.length-1; i >= 0; i--) { // 遍历目录下所有的文件
                        if(!files[i].getName().contains(LogcatHelper.LOGCAT_FILE_NAME)){
                            files[i].delete();
                        }
                    }
                    Log.w("mFile","删除成功:");
                }else{
                    uploadFiles(pos + 1,files);
                }
            }

            @Override
            public void failure() {
            }
        });
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
                            if(!result.getData().getVersionNo().equals(getVersionName(MainActivity.this))){
                                DialogUtils.goUpdating(MainActivity.this,result.getData());
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
    private void getSchoolCourseQueryCourse() {
        showLoading();
        RetrofitUtil.getInstance().apiService()
                .getSchoolCourseQueryCourse()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<SceduleCourseBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<SceduleCourseBean> result) {
                        if(isResultOk(result)){
                            if(result.getData()!=null){
                                SceduleCourseBean mSceduleCourseBean = result.getData();
                                if(mSceduleCourseBean.getCourse()==null){
                                    ToastUtil.showShortToast(MainActivity.this,"无绑定大课程");
                                    return;
                                }
                                if(mSceduleCourseBean.getCourse().getCourseSectionVoList().size()==0){
                                    ToastUtil.showShortToast(MainActivity.this,"无绑定小课程");
                                    return;
                                }
                                if(mSceduleCourseBean.getClassScheduleCard()==null){
                                    ToastUtil.showShortToast(MainActivity.this,"无绑定班级");
                                    return;
                                }
                                String id = mSceduleCourseBean.getId();
                                String mClassId = mSceduleCourseBean.getClassScheduleCard().getClassId();
                                String mClassName = mSceduleCourseBean.getClassScheduleCard().getClassName();
                                String mClassSceduleCardId = mSceduleCourseBean.getId();
                                HomeOneFragment.getSchoolSceduleCourseDetail(MainActivity.this,
                                        id,mClassId,mClassName,mClassSceduleCardId);
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
                ToastUtil.showShortToast(MainActivity.this,"请先断开课程连接");
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
                    getSchoolCourseQueryCourse();
                    return;
                }
                if(ma_iv_index.getDrawable().getLevel()==1){
//                    ma_iv_index.getDrawable().setLevel(2);
//                    if(mHomeTwoFragment!=null){
//                        mHomeTwoFragment.startOrStop(false);
//                    }

                }else{
                    if(mHomeTwoFragment!=null){
                        boolean isStart = mHomeTwoFragment.startOrStop(true);
                        if(isStart){
                            Log.w(LogcatHelper.MESSAGE_LOG ,"*********************开始*********************");
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

    public void setFragmentStart(){
        if(!ConstValuesHttps.IS_AUTO){
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        boolean isStart = mHomeTwoFragment.startOrStop(true);
                        if(isStart){
                            ma_iv_index.getDrawable().setLevel(1);
                        }
                    }
                });
            }
        }).start();
    }
    @Override
    public void onResume() {
        super.onResume();
        switch (mBnvHomeNavigation.getSelectedItemId()){
            case R.id.menu_home_1:
                if(indexPos!=0){
                    setOnResume();
                }
                break;
            case R.id.menu_home_2:
                if(indexPos!=1){
                    setOnResume();
                }
                break;
            case R.id.menu_home_3:
                if(indexPos!=2){
                    setOnResume();
                }
                break;
        }
    }

    public void setOnResume() {
        System.out.println("MainActivity:"+indexPos);
        switch (indexPos){
            case 0:
                mBnvHomeNavigation.setSelectedItemId(R.id.menu_home_1);
                ma_iv_index.getDrawable().setLevel(0);
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
        if("sys_category".equals(dictType)){
            RetrofitUtil.getInstance().apiService()
                    .getCourseTypeListAll()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<Result<List<CourseTypeListAllBean>>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Result<List<CourseTypeListAllBean>> result) {
                            if(isResultOk(result)){
                                mCourseTypeListAllBeans.put(dictType,result.getData());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                        }
                    });
            return;
        }
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
    /**
     * 获取版本名称
     *
     * @param context 上下文
     *
     * @return 版本名称
     */
    public static String getVersionName(Context context) {

        //获取包管理器
        PackageManager pm = context.getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            //返回版本号
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }
}