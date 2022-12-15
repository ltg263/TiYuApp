package com.jxxx.tiyu_app.view.fragment;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.jxxx.tiyu_app.MainActivity;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.api.RetrofitUtil;
import com.jxxx.tiyu_app.app.ConstValues;
import com.jxxx.tiyu_app.base.BaseFragment;
import com.jxxx.tiyu_app.base.Result;
import com.jxxx.tiyu_app.bean.PostStudentBean;
import com.jxxx.tiyu_app.bean.PostStudentResults;
import com.jxxx.tiyu_app.bean.SchoolCourseBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBeanSmallActionInfoJson;
import com.jxxx.tiyu_app.bean.SchoolStudentBean;
import com.jxxx.tiyu_app.tcp_tester.ClientTcpUtils;
import com.jxxx.tiyu_app.tcp_tester.ConstValuesHttps;
import com.jxxx.tiyu_app.utils.SharedUtils;
import com.jxxx.tiyu_app.utils.StatusBarUtil;
import com.jxxx.tiyu_app.utils.StringUtil;
import com.jxxx.tiyu_app.utils.ToastUtil;
import com.jxxx.tiyu_app.utils.WifiMessageReceiver;
import com.jxxx.tiyu_app.utils.view.DialogUtils;
import com.jxxx.tiyu_app.view.activity.HomeTwoXueShengActivity;
import com.jxxx.tiyu_app.view.adapter.HomeTwoTwoListAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeTwoFragment extends BaseFragment {


    @BindView(R.id.tv_chongzhi)
    TextView tv_chongzhi;
    @BindView(R.id.tv_shangchuan)
    TextView tv_shangchuan;
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tv_jishi)
    TextView tv_jishi;
    @BindView(R.id.tv_xieyijie)
    TextView tv_xieyijie;
    //非正常回显
    private List<Byte> sendDatas;//发送的数据
    /**
     * 正在执行的排数
     */
    private int current_class_group = 0;
    /**
     * 正在执行小课程的循环次数
     */
    private int current_course_section_num_yx = 0;
    /**
     * 最大的排数
     */
    private int current_class_group_max = 0;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home_two;
    }

    @Override
    protected void initView() {
        tv_chongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showDialogHint(mContext, "确定重置当前小课程吗?", false, new DialogUtils.ErrorDialogInterface() {
                    @Override
                    public void btnConfirm() {
                        isWanCheng = false;
                        current_time = 0;
                        current_class_group = 0;
                        current_course_section_num_yx = 0;
                        if (mPostStudentResults != null) {
                            String smallCourseId;
                            if (ConstValues.mSchoolCourseInfoBean != null) {//大课程信息
                                SchoolCourseBean.CourseSectionVoListBean mCourseSectionVoList =
                                        ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().get(HomeTwoXueShengActivity.current_course_section);
                                smallCourseId = mCourseSectionVoList.getId();
                            } else {
                                smallCourseId = ConstValues.mSchoolCourseInfoBeanSmall.getId();
                            }
                            for (int i = mPostStudentResults.size() - 1; i >= 0; i--) {
                                if (smallCourseId.equals(mPostStudentResults.get(i).getSmallCourseId())) {
                                    mPostStudentResults.remove(i);
                                }
                            }
                        }
                        isStart = false;
                        ((MainActivity) mContext).setFragmentStartOrStop();
                        HomeTwoXueShengActivity.initYuDongData();
                        setNotifyDataSetChanged_Fragment();
                    }
                });
            }
        });
        tv_xieyijie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(isStart){
//                    ToastUtil.showShortToast(mContext,"正在执行中");
//                    return;
//                }[-2, -96, 5, 7, 10, 5, 1, 2, -1]
                // [-2, -96, 4, 2, 3, 10, 1, 5, -1]
                showDialogKaiShiShangKeXiaYiJie(true);
            }
        });
        tv_shangchuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(isStart){
//                    ToastUtil.showShortToast(mContext,"正在执行中");
//                    return;
//                }
                if (isWanCheng) {
                    DialogUtils.showDialogWanChengSuoYou(mContext, "所有课程已完成！\n成绩将自动上传！", "确定", new DialogUtils.ErrorDialogInterfaceA() {
                        @Override
                        public void btnConfirm(int index) {
                            showLoading();
                            ClientTcpUtils.mClientTcpUtils.sendData_B3_add00(true, index == 0,
                                    new ClientTcpUtils.SendDataOkInterface() {
                                        @Override
                                        public void sendDataOk(byte msg) {
                                            ((Activity)mContext).runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    hideLoading();
                                                    strataSetFlags();
                                                }
                                            });
                                        }
                                    });
                        }
                    });
                    return;
                }
                DialogUtils.showDialogHintSelect(mContext, new DialogUtils.ErrorDialogInterfaceA() {
                    @Override
                    public void btnConfirm(int index) {
                        String title = "";
                        if (index == 0) {
                            title = "该课程已结束";
                            mPostStudentResults.clear();
                        } else {
                            title = "该课程已结束\n成绩将自动上传！";
                            setPostStudentResults(1,HomeTwoXueShengActivity.current_course_section);
                        }
                        DialogUtils.showDialogWanChengSuoYou(mContext, title, "确定", new DialogUtils.ErrorDialogInterfaceA() {
                            @Override
                            public void btnConfirm(int index) {
                                showLoading();
                                ClientTcpUtils.mClientTcpUtils.sendData_B3_add00(true, index == 0,
                                        new ClientTcpUtils.SendDataOkInterface() {
                                            @Override
                                            public void sendDataOk(byte msg) {
                                                ((Activity)mContext).runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        hideLoading();
                                                        isWanCheng = true;
                                                        strataSetFlags();
                                                    }
                                                });
                                            }
                                        });
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    protected void initData() {
        current_class_group = 0;
        current_course_section_num_yx = 0;
    }

    public boolean startOrStop(boolean isStart) {
        if (isWanCheng) {
            ToastUtil.showShortToast(mContext, "课程已全部完成");
            return false;
        }
        this.isStart = isStart;
        if (isStart) {
            if(isDurationOk_type()==0){
                this.isStart = false;
                ToastUtil.showShortToast(mContext, "当前课程时间已结束");
                return false;
            }
            current_time = 0;
            heartHandler.postDelayed(hearRunable, 1000);
            initFirstSendData();
        }
        return isStart;
    }

    /**
     * 首次发送的数据
     */
    private void initFirstSendData() {
        System.out.println("发送的数据-->>initFirstSendData");
        setNotifyDataSetChanged_Fragment();
        current_class_group_max = 0;
        for (int i = 0; i < HomeTwoXueShengActivity.mMapKey_id.size(); i++) {
            //获取队列的全部学生
            List<SchoolStudentBean> mSchoolStudentBeans = HomeTwoXueShengActivity.mMapSchoolStudentBeans.get(HomeTwoXueShengActivity.mMapKey_id.get(i));
            if (mSchoolStudentBeans != null && mSchoolStudentBeans.size() > current_class_group) {
                SchoolStudentBean mSchoolStudentBean_First = mSchoolStudentBeans.get(current_class_group);
                mSchoolStudentBean_First.setPostDqbz(0);
                if (mSchoolStudentBean_First.getSteps() != null) {
                    if (mSchoolStudentBeans.size() > current_class_group_max) {
                        current_class_group_max = mSchoolStudentBeans.size();
                    }
                    for (int f = 0; f < mSchoolStudentBean_First.getSteps().size(); f++) {
                        mSchoolStudentBean_First.getSteps().get(f).setStepNoOkNum(0);
                    }
                    //让所有队列第一排的球亮起来
                    SchoolCourseBeanSmallActionInfoJson.StepsBean mStepsBean = mSchoolStudentBean_First.getSteps().get(0);
                    mStepsBean.setStepNoOkNum(0);
                    List<List<Byte>> mSets = mStepsBean.getSets();
                    for (int j = 0; j < mSets.size(); j++) {
                        if (mSets.get(j).size() == 7) {
                            sendDatas = new ArrayList<>(mSets.get(j));
                            sendDatas.set(1, ConstValuesHttps.MESSAGE_ALL_TOTAL_MAP.get(sendDatas.get(1)));
                            mSchoolStudentBean_First.setCurrentStepNo(mStepsBean.getStepNo());
                            ClientTcpUtils.mClientTcpUtils.sendData_A0_A1(sendDatas.get(0), sendDatas);
                        }
                    }
                }
            }
        }
    }

    List<Fragment> fragments = new ArrayList<>();
    FragmentStatePagerAdapter mFragmentStatePagerAdapter;
    private void initVP() {
        Log.w("initVP","mDataList:"+HomeTwoXueShengActivity.mDataList);
        StatusBarUtil.initMagicIndicator_1(mContext, false, HomeTwoXueShengActivity.mDataList, mMagicIndicator, mViewPager);
        fragments.clear();
        Log.w("initVP","fragments:"+fragments);
        for (int i = 0; i < HomeTwoXueShengActivity.mDataList.size(); i++) {
            Bundle mBundle1 = new Bundle();
            mBundle1.putString("mMapKey", HomeTwoXueShengActivity.mMapKey_id.get(i));
            HomeTwoListFragment mHomeOrderListFragment = new HomeTwoListFragment();
            mHomeOrderListFragment.setArguments(mBundle1);
            fragments.add(mHomeOrderListFragment);
        }
        Log.w("initVP","fragments:"+fragments);
        Log.w("initVP","mFragmentStatePagerAdapter:"+mFragmentStatePagerAdapter);
        if(mFragmentStatePagerAdapter!=null){
            Log.w("initVP","mFragmentStatePagerAdapter:"+mFragmentStatePagerAdapter.getCount());
            mFragmentStatePagerAdapter.notifyDataSetChanged();
        }
        mViewPager.setOffscreenPageLimit(HomeTwoXueShengActivity.mDataList.size());
        mFragmentStatePagerAdapter = new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return "";
            }
        };
        mViewPager.setAdapter(mFragmentStatePagerAdapter);
        mViewPager.setCurrentItem(0);
        setNotifyDataSetChanged_Fragment();
    }

    boolean isWanCheng = false;
    private long current_time = 0;//执行的时间
    List<PostStudentResults> mPostStudentResults;//最终提交的数据
    MyReceiver mMyReceiver;
    WifiMessageReceiver mWifiMessageReceiver;

    @Override
    public void onResume() {
        super.onResume();
        Log.w("BroadcastReceiver：", "current_yundong_yikaishi"+HomeTwoXueShengActivity.current_yundong_yikaishi);
        if (!HomeTwoXueShengActivity.current_yundong_yikaishi) {
            HomeTwoXueShengActivity.current_yundong_yikaishi = true;
            current_time = 0;
            current_class_group = 0;
            current_course_section_num_yx = 0;
            mPostStudentResults = new ArrayList<>();
            if (mWifiMessageReceiver == null) {
                mWifiMessageReceiver = new WifiMessageReceiver();//集成广播的类
                IntentFilter mIntentFilter = new IntentFilter(WifiMessageReceiver.START_BROADCAST_ACTION_START);// 创建IntentFilter对象
                mContext.registerReceiver(mWifiMessageReceiver, mIntentFilter);// 注册Broadcast Receive
                Log.w("BroadcastReceiver：", "注册广播:WifiMessageReceiver");
            }
            /**
             * 广播动态注册
             */
            if (mMyReceiver == null) {
                mMyReceiver = new MyReceiver();//集成广播的类
                IntentFilter filter = new IntentFilter("com.jxxx.tiyu_app.view.fragment");// 创建IntentFilter对象
                mContext.registerReceiver(mMyReceiver, filter);// 注册Broadcast Receive
                Log.w("BroadcastReceiver：", "注册广播:MyReceiver");
            }
            initVP();
        }
    }

    private void strataSetFlags() {
        //断开连接提交数据
        isStart = false;
        try {
            if (mMyReceiver != null) {
                mContext.unregisterReceiver(mMyReceiver);
                mMyReceiver = null;
                Log.w("BroadcastReceiver：", "注销广播:MyReceiver");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            if (mWifiMessageReceiver != null) {
                mContext.unregisterReceiver(mWifiMessageReceiver);
                mWifiMessageReceiver = null;
                Log.w("BroadcastReceiver：", "注销广播:mWifiMessageReceiver");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (mPostStudentResults != null && mPostStudentResults.size() > 0) {
            for (int i = mPostStudentResults.size() - 1; i >= 0; i--) {
                if (mPostStudentResults.get(i).getTimes() == null) {
                    mPostStudentResults.remove(i);
                }
            }
            if (mPostStudentResults.size() > 0) {
                postResultsBatchAdd();
            } else {
                MainActivity.indexPos = 0;
                ((MainActivity) mContext).setOnResume();
                isWanCheng = false;
            }
        } else {
            MainActivity.indexPos = 0;
            ((MainActivity) mContext).setOnResume();
            isWanCheng = false;
        }
    }
    private void postResultsBatchAdd() {
        showLoading();
        List<PostStudentBean> mPostStudentBeans = new ArrayList<>();
        if (ConstValues.mSchoolCourseInfoBean != null && ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().size()>1) {
            List<SchoolCourseBean.CourseSectionVoListBean> mCourseSectionVoLists = ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList();
            for(int i=0;i<mCourseSectionVoLists.size();i++){
                List<PostStudentResults> studentResultsLists = new ArrayList<>();
                for(int j=0;j<mPostStudentResults.size();j++){
                    if(mCourseSectionVoLists.get(i).getSmallCourseId().equals(mPostStudentResults.get(j).getSmallCourseId())){
                        studentResultsLists.add(mPostStudentResults.get(j));
                    }
                }
                if(studentResultsLists.size()>0){
                    PostStudentBean mPostStudentBean = new PostStudentBean();
                    mPostStudentBean.setStudentResultsList(studentResultsLists);
                    mPostStudentBean.setClassId(studentResultsLists.get(0).getClassId());
                    mPostStudentBean.setClassSceduleCardId(ConstValues.classSceduleCardId);
                    mPostStudentBean.setCourseId(studentResultsLists.get(0).getCourseId());
                    mPostStudentBean.setSmallCourseId(studentResultsLists.get(0).getSmallCourseId());
                    mPostStudentBean.setTeacherId(studentResultsLists.get(0).getTeacherId());
                    mPostStudentBean.setEndTime(studentResultsLists.get(0).getEndTime());
                    mPostStudentBean.setBeginTime(studentResultsLists.get(0).getBeginTime());
                    mPostStudentBean.setClassDate(studentResultsLists.get(0).getClassDate());
                    mPostStudentBeans.add(mPostStudentBean);
                }
            }
        } else {//小课程
            PostStudentBean mPostStudentBean = new PostStudentBean();
            mPostStudentBean.setStudentResultsList(mPostStudentResults);
            mPostStudentBean.setClassId(mPostStudentResults.get(0).getClassId());
            mPostStudentBean.setClassSceduleCardId(ConstValues.classSceduleCardId);
            mPostStudentBean.setCourseId(mPostStudentResults.get(0).getCourseId());
            mPostStudentBean.setSmallCourseId(mPostStudentResults.get(0).getSmallCourseId());
            mPostStudentBean.setTeacherId(mPostStudentResults.get(0).getTeacherId());
            mPostStudentBean.setEndTime(mPostStudentResults.get(0).getEndTime());
            mPostStudentBean.setBeginTime(mPostStudentResults.get(0).getBeginTime());
            mPostStudentBean.setClassDate(mPostStudentResults.get(0).getClassDate());
            mPostStudentBeans.add(mPostStudentBean);
        }
        Log.w("postResultsBatchAdd", "提交的数据-全部：" + mPostStudentBeans.toString());
        RetrofitUtil.getInstance().apiService()
                .postResultsBatchAdds(mPostStudentBeans)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result result) {
                        if (isResultOk(result)) {
                            isWanCheng = false;
                            MainActivity.indexPos = 0;
                            ((MainActivity) mContext).setOnResume();
//                            HomeTwoShangKeActivity.startActivityIntentFragment(mContext);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                        DialogUtils.showDialogHint(mContext, "上传失败重新上传", true, new DialogUtils.ErrorDialogInterface() {
                            @Override
                            public void btnConfirm() {
                                postResultsBatchAdd();
                            }
                        });
                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                    }
                });
    }


    private boolean isStart = false;//是否开始
    private Handler heartHandler = new Handler();
    /**
     * 计时器
     */
    private Runnable hearRunable = new Runnable() {
        @Override
        public void run() {
            if (isStart) {
                current_time++;
                heartHandler.postDelayed(hearRunable, 1000);
                if(isDurationOk_type()==0){
                    ToastUtil.showShortToast(mContext, "当前课程时间已结束");
                    isStart = false;
                    ((MainActivity) mContext).setFragmentStartOrStop();
                }
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_jishi.setText(StringUtil.getValue(current_time));
                    }
                });
            }
        }
    };

    /**
     * 检测时间是否已完成
     * return:0:完成；1：未完成；2：非时长模式
     */
    private int isDurationOk_type(){
        if(HomeTwoXueShengActivity.current_course_total_duration > 0){
            long strataTime = SharedUtils.singleton().get(HomeTwoXueShengActivity.STRATA_JISHI_SHANGKE,0L);
            long currentTime = System.currentTimeMillis()/1000L;
            if(currentTime-strataTime > HomeTwoXueShengActivity.current_course_total_duration){
                int wccs_ccg = 0;//当前排最大的完成次数
                boolean isBuTong = false;//每排都是一直的 false
                if(current_class_group == 0) {
                    wccs_ccg = ((HomeTwoListFragment) fragments.get(0))
                            .getHomeTwoTwoListAdapter().getData().get(current_class_group).getPostWccs();
                    for (int a = 0; a < fragments.size(); a++) {
                        HomeTwoListFragment f = (HomeTwoListFragment) fragments.get(a);
                        if (f != null && f.getHomeTwoTwoListAdapter() != null) {
                            List<SchoolStudentBean> mData = f.getHomeTwoTwoListAdapter().getData();
                            if(mData.get(current_class_group).getPostWccs() != wccs_ccg){
                                isBuTong = true;
                            }
                            if (mData.get(current_class_group).getPostWccs() > wccs_ccg) {
                                wccs_ccg = mData.get(current_class_group).getPostWccs();
                            }
                        }
                    }
                }
                for (int i = 0; i < fragments.size(); i++) {
                    HomeTwoListFragment mFragment = (HomeTwoListFragment) fragments.get(i);
                    if (mFragment != null && current_time!=0) {
                        HomeTwoTwoListAdapter mHomeTwoTwoListAdapter = mFragment.getHomeTwoTwoListAdapter();
                        if (mHomeTwoTwoListAdapter != null) {
                            List<SchoolStudentBean> mData = mHomeTwoTwoListAdapter.getData();
                            if(mData.size()>current_class_group){
                                SchoolStudentBean mSchoolStudentBean = mData.get(current_class_group);
                                if(mSchoolStudentBean.getSteps()!=null){
                                    if(current_class_group > 0){
                                        int wccs = mData.get(0).getPostWccs();
                                        if(mSchoolStudentBean.getPostWccs() < wccs){
                                            mSchoolStudentBean.setPostZys(mSchoolStudentBean.getPostZys()+current_time);
                                            if(mSchoolStudentBean.getPostZjzs()!=0){
                                                double pjsd = 1D * mSchoolStudentBean.getPostZys()/ mSchoolStudentBean.getPostZjzs() ;
                                                Log.w("BroadcastReceiver", "pjsd:" + StringUtil.getValue(pjsd));
                                                mSchoolStudentBean.setPostPjsd(Double.parseDouble(StringUtil.getValue(pjsd)));
                                            }
                                        }
                                    }else{
                                        if(!isBuTong || mSchoolStudentBean.getPostWccs() < wccs_ccg){
                                            mSchoolStudentBean.setPostZys(mSchoolStudentBean.getPostZys()+current_time);
                                            if(mSchoolStudentBean.getPostZjzs()!=0){
                                                double pjsd = 1D * mSchoolStudentBean.getPostZys()/ mSchoolStudentBean.getPostZjzs() ;
                                                Log.w("BroadcastReceiver", "pjsd:" + StringUtil.getValue(pjsd));
                                                mSchoolStudentBean.setPostPjsd(Double.parseDouble(StringUtil.getValue(pjsd)));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        setNotifyDataSetChanged_Fragment();
                    }
                }
                current_time = 0;
                System.out.println("BroadcastReceiver：时间已到:"+HomeTwoXueShengActivity.current_course_total_duration);
                return 0;
            }
            System.out.println("BroadcastReceiver：时间未到还剩："+(HomeTwoXueShengActivity.current_course_total_duration-(currentTime-strataTime))+"秒");
            return 1;
        }
        return 2;
    }

    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!isStart || isWanCheng) {
                System.out.println("BroadcastReceiver：设备暂停中");
                return;
            }
            System.out.println("BroadcastReceiver：current_time：" + current_time);
            byte mStartBroadcastType = intent.getByteExtra(WifiMessageReceiver.START_BROADCAST_TYPE, (byte) 0X00);
            byte[] mData = intent.getByteArrayExtra(WifiMessageReceiver.START_BROADCAST_DATA);
            System.out.println("BroadcastReceiver："+mStartBroadcastType+"球号：" + Arrays.toString(mData));
            for (int i = 0; i < mData.length; i++) {
                getMapKayYunDong(mData[i], 0,mStartBroadcastType);
            }
        }
    }

    private void getMapKayYunDong(byte qiuhao, int pos,byte mStartBroadcastType) {
        System.out.println("BroadcastReceiver：pos" + pos);
        System.out.println("BroadcastReceiver：HomeTwoXueShengActivity.mMapKey_id.size()" + HomeTwoXueShengActivity.mMapKey_id.size());
        if (HomeTwoXueShengActivity.mMapKey_id.size() <= pos) {
            return;
        }
        //根据mMapKey_id获取从mMapSchoolStudentBeans中获取某个队列对应的全部学生
        List<SchoolStudentBean> mSchoolStudentBeans = HomeTwoXueShengActivity.mMapSchoolStudentBeans.get(HomeTwoXueShengActivity.mMapKey_id.get(pos));
        //当某个队列不为空并且队列中的学生大于执行的行数
        if (mSchoolStudentBeans != null && mSchoolStudentBeans.size() > current_class_group) {
            //根据执行的行数获取某个学生信息
            SchoolStudentBean mSchoolStudentBean = mSchoolStudentBeans.get(current_class_group);
            System.out.println("BroadcastReceiver：mSchoolStudentBean" + mSchoolStudentBean.getStudentName());
            //获取某个学生的步骤层
            List<SchoolCourseBeanSmallActionInfoJson.StepsBean> mSteps = mSchoolStudentBean.getSteps();
            //步骤不为空的时候 某个学生的球数不为空的时候
            if (mSteps != null && mSteps.size() > 0 && mSchoolStudentBean.getAllQiuNo() != null) {
                System.out.println("BroadcastReceiver：getAllQiuNo" + mSchoolStudentBean.getAllQiuNo());
                //[16,12]
                for (int q = 0; q < mSchoolStudentBean.getAllQiuNo().size(); q++) {
                    //遍历某个学生的球并从Map中获取对应的球号
                    Byte key = ConstValuesHttps.MESSAGE_ALL_TOTAL_MAP.get(mSchoolStudentBean.getAllQiuNo().get(q));
                    //如果设备回调的球号包含这个学生控制的球内开始执行逻辑[10,16] [1]
                    if (key != null && qiuhao == key) {
                        mSchoolStudentBean.setPostZfks(mSchoolStudentBean.getPostZfks() + 1);
                        if(mStartBroadcastType == ConstValuesHttps.MESSAGE_GET_C5){
                            mSchoolStudentBean.setPostZjzs(mSchoolStudentBean.getPostZjzs() + 1);
                            mSchoolStudentBean.addCurrentTime(System.currentTimeMillis());
                            System.out.println("BroadcastReceiver：" + mSchoolStudentBean.getStudentName() + "：总击中数:" + mSchoolStudentBean.getPostZjzs());
                        }
                        System.out.println("BroadcastReceiver：" + mSchoolStudentBean.getStudentName() + "：总反馈数:" + mSchoolStudentBean.getPostZfks());
                        setNotifyDataSetChanged_Fragment();
                        duiBiXueShengStep(mSchoolStudentBean, mSteps, 0);
                        return;
                    }
                }
                System.out.println("BroadcastReceiver：else" + 1);
                getMapKayYunDong(qiuhao, pos + 1,mStartBroadcastType);
            } else {
                System.out.println("BroadcastReceiver：else" + 2);
                getMapKayYunDong(qiuhao, pos + 1,mStartBroadcastType);
            }
        } else {
            System.out.println("BroadcastReceiver：else" + 3);
            getMapKayYunDong(qiuhao, pos + 1,mStartBroadcastType);
        }
    }

    /**
     * @param mSchoolStudentBean 某个同学的信息
     * @param mSteps             某个同学步骤的信息
     * @param posSteps           某个同学步骤的下标
     */
    private void duiBiXueShengStep(SchoolStudentBean mSchoolStudentBean, List<SchoolCourseBeanSmallActionInfoJson.StepsBean> mSteps, int posSteps) {
        System.out.println("BroadcastReceiver：duiBiXueShengStep()" + mSchoolStudentBean.getStudentName());
        SchoolCourseBeanSmallActionInfoJson.StepsBean mStepsBean = mSteps.get(posSteps);
        System.out.println("BroadcastReceiver：mStepsBean.getCurrentStepNo()" + mSchoolStudentBean.getCurrentStepNo());
        if (mSchoolStudentBean.getCurrentStepNo() == mStepsBean.getStepNo()) {
            mStepsBean.setStepNoOkNum(mStepsBean.getStepNoOkNum() + 1);
        }
        System.out.println("BroadcastReceiver：mStepsBean.getStepNoOkNum():" + mStepsBean.getStepNoOkNum());
        if (mStepsBean.getStepNoOkNum() == mStepsBean.getSets().size()) {
            System.out.println("BroadcastReceiver：duiBiXueShengStep()执行下一个球");
            if (mSteps.size() - 1 > posSteps) {
                duiBiXueShengStep(mSchoolStudentBean, mSteps, posSteps + 1);
            } else {
                for (int i = 0; i < mSchoolStudentBean.getSteps().size(); i++) {
                    mSchoolStudentBean.getSteps().get(i).setStepNoOkNum(0);
                }
                mSchoolStudentBean.setPostWccs(mSchoolStudentBean.getPostWccs() + 1);
                mSchoolStudentBean.setPostDqbz(mSchoolStudentBean.getPostDqbz() + 1);
                setNotifyDataSetChanged_Fragment();
                if (mSchoolStudentBean.getPostDqbz() >= HomeTwoXueShengActivity.current_course_section_loop_num) {
                    mSchoolStudentBean.setPostZys(mSchoolStudentBean.getPostZys()+current_time);
                    if(mSchoolStudentBean.getPostZjzs()!=0){
                        double pjsd = 1D * mSchoolStudentBean.getPostZys()/ mSchoolStudentBean.getPostZjzs();
                        Log.w("BroadcastReceiver", "pjsd:" + StringUtil.getValue(pjsd));
                        mSchoolStudentBean.setPostPjsd(Double.parseDouble(StringUtil.getValue(pjsd)));
                    }
                    System.out.println("BroadcastReceiver：duiBiXueShengStep()已完成：" + mSchoolStudentBean.getStudentName());
                    setNotifyDataSetChanged_Fragment();
                    isAllOk();
                } else {
                    System.out.println("BroadcastReceiver：duiBiXueShengStep()完成了一次--执行下一层");
                    duiBiXueShengStep(mSchoolStudentBean, mSteps, 0);
                }
            }
            return;
        }
        System.out.println("BroadcastReceiver：mSteps.size()：" + mSteps.size());
        System.out.println("BroadcastReceiver：posSteps：" + posSteps);
        if (mSchoolStudentBean.getCurrentStepNo() != mStepsBean.getStepNo() && mSteps.size() > posSteps) {
            SchoolCourseBeanSmallActionInfoJson.StepsBean mStepsBean_xia = mSteps.get(posSteps);
            List<List<Byte>> mSets = mStepsBean_xia.getSets();
            for (int j = 0; j < mSets.size(); j++) {
                if (mSets.get(j).size() == 7) {
                    sendDatas = new ArrayList<>(mSets.get(j));
                    sendDatas.set(1, ConstValuesHttps.MESSAGE_ALL_TOTAL_MAP.get(sendDatas.get(1)));
                    ClientTcpUtils.mClientTcpUtils.sendData_A0_A1(sendDatas.get(0), sendDatas);
                    mSchoolStudentBean.setCurrentStepNo(mStepsBean.getStepNo());
                } else {
                    ToastUtil.showShortToast(getActivity(), "有错误数据-->>" + 0);
                }
            }
            return;
        }
        System.out.println("BroadcastReceiver：多个球反馈了" + mStepsBean.getStepNoOkNum() + "个");
    }

    /**
     * 是否全部完成
     */
    private void isAllOk() {
        for (int i = 0; i < HomeTwoXueShengActivity.mMapKey_id.size(); i++) {
            //获取队列的全部学生
            List<SchoolStudentBean> mSchoolStudentBeans = HomeTwoXueShengActivity.mMapSchoolStudentBeans.get(HomeTwoXueShengActivity.mMapKey_id.get(i));
            if (mSchoolStudentBeans.size() > current_class_group && mSchoolStudentBeans.get(current_class_group).getSteps() != null) {
                if (mSchoolStudentBeans.get(current_class_group).getPostDqbz() < HomeTwoXueShengActivity.current_course_section_loop_num) {
                    return;
                }
            }
        }
        isStart = false;
        ((MainActivity) mContext).setFragmentStartOrStop();
        System.out.println("BroadcastReceiver：已全部完成");
        current_class_group++;
        if (current_class_group_max > current_class_group) {
            ((MainActivity) mContext).setFragmentStart();
            ToastUtil.showShortToast(mContext, "第" + current_class_group + "排已全部完成");
        } else {
            current_class_group = 0;
            current_course_section_num_yx++;
            if (ConstValues.mSchoolCourseInfoBean != null || ConstValues.mSchoolCourseInfoBeanSmall != null) {
                System.out.println("BroadcastReceiver：current_course_section_num"+HomeTwoXueShengActivity.current_course_section_num);
                System.out.println("BroadcastReceiver：current_course_section_num_yx"+current_course_section_num_yx);
//                if(isDurationOk_type()==0){
//                    ToastUtil.showShortToast(mContext, "已全部完成");
//                    showDialogKaiShiShangKeXiaYiJie(false);
//                }else
                if(isDurationOk_type()==1){
                    ((MainActivity) mContext).setFragmentStart();
                    ToastUtil.showShortToast(mContext, "已全部第" + current_course_section_num_yx + "次循环");
                }else{
                    if (HomeTwoXueShengActivity.current_course_section_num == current_course_section_num_yx) {
                        ToastUtil.showShortToast(mContext, "已全部完成");
                        showDialogKaiShiShangKeXiaYiJie(false);
                    } else {
                        ((MainActivity) mContext).setFragmentStart();
                        ToastUtil.showShortToast(mContext, "已全部第" + current_course_section_num_yx + "次循环");
                    }
                }
            }
        }
        setNotifyDataSetChanged_Fragment();
    }

    public void setNotifyDataSetChanged_Fragment() {
        for (int i = 0; i < fragments.size(); i++) {
            HomeTwoListFragment mFragment = (HomeTwoListFragment) fragments.get(i);
            if (mFragment != null) {
                mFragment.setNotifyDataSetChanged(current_class_group);
            }
        }

        if (ConstValues.mSchoolCourseInfoBean != null && ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().size() > 1) {
            tv_xieyijie.setVisibility(View.VISIBLE);
            tv_xieyijie.setText("下一节");
            if (ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().size() - 1 == HomeTwoXueShengActivity.current_course_section) {
                tv_xieyijie.setVisibility(View.GONE);
                tv_xieyijie.setText("上一节");
            }
        } else {
            tv_xieyijie.setVisibility(View.GONE);
        }
    }

    private void showDialogKaiShiShangKeXiaYiJie(boolean isNoOk) {
        current_course_section_num_yx = 0;
        if (!isNoOk) {
            HomeTwoXueShengActivity.current_course_section++;
        }
        if (ConstValues.mSchoolCourseInfoBean != null && ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().size() > HomeTwoXueShengActivity.current_course_section) {
            DialogUtils.showDialogKaiShiShangKeXiaYiJie(mContext, isNoOk, ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList()
                    .get(HomeTwoXueShengActivity.current_course_section), new DialogUtils.ErrorDialogInterfaceA() {
                @Override
                public void btnConfirm(int index) {
                    int pos = HomeTwoXueShengActivity.current_course_section;
                    if(!isNoOk){
                        pos = HomeTwoXueShengActivity.current_course_section-1;
                    }
                    setPostStudentResults(2,pos);
                    if (index == 1) {//下一节
                        if (isNoOk) {
                            HomeTwoXueShengActivity.current_course_section++;
                        }
                        DialogUtils.showDialogXiaYiJieIsXunQiu(mContext,
                                ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().get(HomeTwoXueShengActivity.current_course_section), new DialogUtils.ErrorDialogInterfaceA() {
                                    @Override
                                    public void btnConfirm(int index) {
                                        if (index == 1) {
                                            current_time = 0;
                                            current_class_group = 0;
                                            HomeTwoXueShengActivity.current_course_total_duration =
                                                    ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList()
                                                            .get(HomeTwoXueShengActivity.current_course_section).getTotalDuration()*60;
                                            SharedUtils.singleton().put(HomeTwoXueShengActivity.STRATA_JISHI_SHANGKE,0);
                                            if(HomeTwoXueShengActivity.current_course_total_duration > 0){
                                                SharedUtils.singleton().put(HomeTwoXueShengActivity.STRATA_JISHI_SHANGKE,System.currentTimeMillis());
                                            }
                                            SharedUtils.singleton().put("postSchoolClassRecord_time",StringUtil.getTimeToYMD(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss"));
                                            HomeTwoXueShengActivity.initYuDongData();
                                            setNotifyDataSetChanged_Fragment();
                                        } else {
                                            showLoading();
                                            ClientTcpUtils.mClientTcpUtils.sendData_B3_add00(false, false,
                                                    new ClientTcpUtils.SendDataOkInterface() {
                                                        @Override
                                                        public void sendDataOk(byte msg) {
                                                            ((Activity)mContext).runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    hideLoading();
                                                                    lianjie();
                                                                }
                                                            });
                                                        }
                                                    });
                                        }
                                    }
                                });
                    } else {//跳过下一节
                        if (!isNoOk) {
                            showDialogKaiShiShangKeXiaYiJie(isNoOk);
                        }
                    }
                }
            });
            return;
        }
        if (!isNoOk) {
            HomeTwoXueShengActivity.current_course_section--;
        }
        setPostStudentResults(3,HomeTwoXueShengActivity.current_course_section);
        isWanCheng = true;
//        DialogUtils.showDialogWanChengSuoYou(mContext, "所有课程已完成！\n成绩将自动上传！","确定", new DialogUtils.ErrorDialogInterfaceA() {
//            @Override
//            public void btnConfirm(int index) {
//                ClientTcpUtils.mClientTcpUtils.sendData_B3_add00(true,index==0);
//                isWanCheng = true;
//                strataSetFlags();
//            }
//        });
    }
    /**
     * 设置数据
     */
    private void setPostStudentResults(int a,int pos) {
        String courseId = null;
        String smallCourseId = null;
        if (ConstValues.mSchoolCourseInfoBean != null) {
            courseId = ConstValues.mSchoolCourseInfoBean.getId();
            smallCourseId = ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().get(pos).getSmallCourseId();
        } else {
            smallCourseId = ConstValues.mSchoolCourseInfoBeanSmall.getId();
        }
        for (int i = 0; i < fragments.size(); i++) {
            HomeTwoListFragment mFragment = (HomeTwoListFragment) fragments.get(i);
            if (mFragment != null) {
                HomeTwoTwoListAdapter mHomeTwoTwoListAdapter = mFragment.getHomeTwoTwoListAdapter();
                if (mHomeTwoTwoListAdapter != null) {
                    List<SchoolStudentBean> mData = mHomeTwoTwoListAdapter.getData();
                    for (int j = 0; j < mData.size(); j++) {
                        SchoolStudentBean mSchoolStudentBean = mData.get(j);
                        if (mSchoolStudentBean.getSteps() != null) {
                            PostStudentResults mPostStudentResult = new PostStudentResults();
                            mPostStudentResult.setTeacherId(SharedUtils.singleton().get(ConstValues.TEACHER_ID, ""));
                            mPostStudentResult.setCourseId(courseId);
                            mPostStudentResult.setSmallCourseId(smallCourseId);
                            mPostStudentResult.setStudentId(mSchoolStudentBean.getId());
                            mPostStudentResult.setClassId(mSchoolStudentBean.getClassId());
//                            mPostStudentResult.setClassSceduleCardId(ConstValues.classSceduleCardId);
                            mPostStudentResult.setEndTime(StringUtil.getTimeToYMD(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
                            mPostStudentResult.setBeginTime(SharedUtils.singleton().get("postSchoolClassRecord_time", ""));
                            mPostStudentResult.setClassDate(SharedUtils.singleton().get("postSchoolClassRecord_time", ""));
                            mPostStudentResult.setTimeUse(mSchoolStudentBean.getPostZys() + "");
                            if(mSchoolStudentBean.getPostPjsd()!=Double.POSITIVE_INFINITY
                                && mSchoolStudentBean.getPostPjsd()!=Double.NEGATIVE_INFINITY){
                                mPostStudentResult.setSpeed(mSchoolStudentBean.getPostPjsd() + "");
                            }else{
                                mPostStudentResult.setSpeed("0");
                            }
                            mPostStudentResult.setTimes(mSchoolStudentBean.getPostWccs() + "");
                            mPostStudentResult.setFinishTimes(mSchoolStudentBean.getPostZjzs() + "");
                            mPostStudentResult.setTimeNode(mSchoolStudentBean.getCurrentTime());
                            mPostStudentResults.add(mPostStudentResult);
                        }
                    }
                }
            }
        }
        Log.w("BroadcastReceiver", a+"提交的数据-当前"+pos+"小课程:" + mPostStudentResults.toString());
    }
    private void lianjie() {
        int mBallNum;
        int mPlateNum;
        int totalDuration;
        if (ConstValues.mSchoolCourseInfoBeanSmall != null) {
            mBallNum = ConstValues.mSchoolCourseInfoBeanSmall.getBallNum();
            mPlateNum = ConstValues.mSchoolCourseInfoBeanSmall.getPlateNum();
            totalDuration = 0;
        } else {
            mBallNum = ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().get(HomeTwoXueShengActivity.current_course_section).getBallNum();
            mPlateNum = ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().get(HomeTwoXueShengActivity.current_course_section).getPlateNum();
            totalDuration = ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().get(HomeTwoXueShengActivity.current_course_section).getTotalDuration();
        }
        DialogUtils.showDialogLianJieSheBei(mContext, false, mBallNum, mPlateNum,totalDuration,
                new DialogUtils.ErrorDialogInterfaceLianJieSheBei() {
                    @Override
                    public void lianJieNum(int guangQiu, int guangBan, int dengGuang,int totalDuration) {
                        int sbNum = guangQiu + guangBan;
                        ConstValuesHttps.MESSAGE_ALL_TOTAL.clear();
                        ConstValuesHttps.MESSAGE_ALL_TOTAL_ZJ.clear();
                        ConstValuesHttps.MESSAGE_ALL_TOTAL_MAP.clear();
                        ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().get(HomeTwoXueShengActivity.current_course_section).setTotalDuration(totalDuration);
                        mWifiMessageReceiver.onWifiMessageReceiverInter(mContext, sbNum, dengGuang, new WifiMessageReceiver.WifiMessageReceiverInter() {
                            @Override
                            public void messageReceiverInter() {
                                current_time = 0;
                                current_class_group = 0;
                                HomeTwoXueShengActivity.current_course_total_duration =
                                        ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList()
                                                .get(HomeTwoXueShengActivity.current_course_section).getTotalDuration()*60;
                                SharedUtils.singleton().put(HomeTwoXueShengActivity.STRATA_JISHI_SHANGKE,0);
                                if(HomeTwoXueShengActivity.current_course_total_duration > 0){
                                    SharedUtils.singleton().put(HomeTwoXueShengActivity.STRATA_JISHI_SHANGKE,System.currentTimeMillis());
                                }
                                SharedUtils.singleton().put("postSchoolClassRecord_time",StringUtil.getTimeToYMD(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss"));
                                HomeTwoXueShengActivity.initYuDongData();
                                setNotifyDataSetChanged_Fragment();
                            }
                        });
                    }
                });
    }
}



