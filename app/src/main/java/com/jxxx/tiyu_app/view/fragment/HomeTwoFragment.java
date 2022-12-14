package com.jxxx.tiyu_app.view.fragment;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.jxxx.tiyu_app.MainActivity;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.api.RetrofitUtil;
import com.jxxx.tiyu_app.app.ConstValues;
import com.jxxx.tiyu_app.base.BaseFragment;
import com.jxxx.tiyu_app.base.Result;
import com.jxxx.tiyu_app.bean.CourseTypeListAllBean;
import com.jxxx.tiyu_app.bean.PostStudentBean;
import com.jxxx.tiyu_app.bean.PostStudentResults;
import com.jxxx.tiyu_app.bean.SchoolClassBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBeanSmall;
import com.jxxx.tiyu_app.bean.SchoolCourseBeanSmallActionInfoJson;
import com.jxxx.tiyu_app.bean.SchoolStudentBean;
import com.jxxx.tiyu_app.tcp_tester.ClientTcpUtils;
import com.jxxx.tiyu_app.tcp_tester.ConstValuesHttps;
import com.jxxx.tiyu_app.utils.GlideImgLoader;
import com.jxxx.tiyu_app.utils.SharedUtils;
import com.jxxx.tiyu_app.utils.StatusBarUtil;
import com.jxxx.tiyu_app.utils.StringUtil;
import com.jxxx.tiyu_app.utils.ToastUtil;
import com.jxxx.tiyu_app.utils.WifiMessageReceiver;
import com.jxxx.tiyu_app.utils.view.DialogUtils;
import com.jxxx.tiyu_app.view.activity.HomeTwoShangKeActivity;
import com.jxxx.tiyu_app.view.activity.HomeTwoXueShengActivity;
import com.jxxx.tiyu_app.view.adapter.HomeTwoTwoListAdapter;
import com.jxxx.tiyu_app.wifi.WifiUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    //???????????????
    private List<Byte> sendDatas;//???????????????
    /**
     * ?????????????????????
     */
    private int current_class_group = 0;
    /**
     * ???????????????
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
                DialogUtils.showDialogHint(mContext, "???????????????????????????????", false, new DialogUtils.ErrorDialogInterface() {
                    @Override
                    public void btnConfirm() {
                        isWanCheng = false;
                        current_time = 0;
                        current_class_group = 0;
                        if (mPostStudentResults != null) {
                            String smallCourseId;
                            if (ConstValues.mSchoolCourseInfoBean != null) {//???????????????
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
//                    ToastUtil.showShortToast(mContext,"???????????????");
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
//                    ToastUtil.showShortToast(mContext,"???????????????");
//                    return;
//                }
                Log.w("BroadcastReceiver", "??????????????????:" + mPostStudentResults.toString());
                if (isWanCheng) {
                    DialogUtils.showDialogWanChengSuoYou(mContext, "????????????????????????\n????????????????????????", "??????", new DialogUtils.ErrorDialogInterfaceA() {
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
                            title = "??????????????????";
                            mPostStudentResults.clear();
                        } else {
                            title = "??????????????????\n????????????????????????";
                            setPostStudentResults();
                        }
                        DialogUtils.showDialogWanChengSuoYou(mContext, title, "??????", new DialogUtils.ErrorDialogInterfaceA() {
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
    }

    public boolean startOrStop(boolean isStart) {
        if (isWanCheng) {
            ToastUtil.showShortToast(mContext, "?????????????????????");
            return false;
        }
        this.isStart = isStart;
        if (isStart) {
            current_time = 0;
            heartHandler.postDelayed(hearRunable, 1000);
            initFirstSendData();
        }
        return isStart;
    }

    /**
     * ?????????????????????
     */
    private void initFirstSendData() {
        System.out.println("???????????????-->>initFirstSendData");
        setNotifyDataSetChanged_Fragment();
        current_class_group_max = 0;
        for (int i = 0; i < HomeTwoXueShengActivity.mMapKey_id.size(); i++) {
            //???????????????????????????
            List<SchoolStudentBean> mSchoolStudentBeans = HomeTwoXueShengActivity.mMapSchoolStudentBeans.get(HomeTwoXueShengActivity.mMapKey_id.get(i));
            if (mSchoolStudentBeans != null && mSchoolStudentBeans.size() > current_class_group) {
                SchoolStudentBean mSchoolStudentBean_First = mSchoolStudentBeans.get(current_class_group);
                if (mSchoolStudentBean_First.getSteps() != null) {
                    if (mSchoolStudentBeans.size() > current_class_group_max) {
                        current_class_group_max = mSchoolStudentBeans.size();
                    }
                    for (int f = 0; f < mSchoolStudentBean_First.getSteps().size(); f++) {
                        mSchoolStudentBean_First.getSteps().get(f).setStepNoOkNum(0);
                    }
                    //???????????????????????????????????????
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
    private long current_time = 0;//???????????????
    List<PostStudentResults> mPostStudentResults;//?????????????????????
    MyReceiver mMyReceiver;
    WifiMessageReceiver mWifiMessageReceiver;

    @Override
    public void onResume() {
        super.onResume();
        Log.w("BroadcastReceiver???", "current_yundong_yikaishi"+HomeTwoXueShengActivity.current_yundong_yikaishi);
        if (!HomeTwoXueShengActivity.current_yundong_yikaishi) {
            HomeTwoXueShengActivity.current_yundong_yikaishi = true;
            current_time = 0;
            current_class_group = 0;
            mPostStudentResults = new ArrayList<>();
            if (mWifiMessageReceiver == null) {
                mWifiMessageReceiver = new WifiMessageReceiver();//??????????????????
                IntentFilter mIntentFilter = new IntentFilter(WifiMessageReceiver.START_BROADCAST_ACTION_START);// ??????IntentFilter??????
                mContext.registerReceiver(mWifiMessageReceiver, mIntentFilter);// ??????Broadcast Receive
                Log.w("BroadcastReceiver???", "????????????:WifiMessageReceiver");
            }
            /**
             * ??????????????????
             */
            if (mMyReceiver == null) {
                mMyReceiver = new MyReceiver();//??????????????????
                IntentFilter filter = new IntentFilter("com.jxxx.tiyu_app.view.fragment");// ??????IntentFilter??????
                mContext.registerReceiver(mMyReceiver, filter);// ??????Broadcast Receive
                Log.w("BroadcastReceiver???", "????????????:MyReceiver");
            }
            initVP();
        }
    }

    private void strataSetFlags() {
        //????????????????????????
        isStart = false;
        try {
            if (mMyReceiver != null) {
                mContext.unregisterReceiver(mMyReceiver);
                mMyReceiver = null;
                Log.w("BroadcastReceiver???", "????????????:MyReceiver");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            if (mWifiMessageReceiver != null) {
                mContext.unregisterReceiver(mWifiMessageReceiver);
                mWifiMessageReceiver = null;
                Log.w("BroadcastReceiver???", "????????????:mWifiMessageReceiver");
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
        PostStudentBean mPostStudentBean = new PostStudentBean();
        mPostStudentBean.setStudentResultsList(mPostStudentResults);
        mPostStudentBean.setClassId(mPostStudentResults.get(0).getClassId());
//        mPostStudentBean.setClassSceduleCardId(mPostStudentResults.get(0).getClassSceduleCardId());
        mPostStudentBean.setCourseId(mPostStudentResults.get(0).getCourseId());
        mPostStudentBean.setSmallCourseId(mPostStudentResults.get(0).getSmallCourseId());
        mPostStudentBean.setTeacherId(mPostStudentResults.get(0).getTeacherId());
        mPostStudentBean.setEndTime(StringUtil.getTimeToYMD(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
        mPostStudentBean.setBeginTime(mPostStudentResults.get(0).getBeginTime());
        mPostStudentBean.setClassDate(mPostStudentResults.get(0).getBeginTime());
        Log.w("postResultsBatchAdd", "???????????????" + mPostStudentBean.toString());
        RetrofitUtil.getInstance().apiService()
                .postResultsBatchAdd(mPostStudentBean)
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
                        DialogUtils.showDialogHint(mContext, "????????????????????????", true, new DialogUtils.ErrorDialogInterface() {
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


    private boolean isStart = false;//????????????
    private Handler heartHandler = new Handler();
    /**
     * ?????????
     */
    private Runnable hearRunable = new Runnable() {
        @Override
        public void run() {
            if (isStart) {
                current_time++;
                heartHandler.postDelayed(hearRunable, 1000);
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_jishi.setText(StringUtil.getValue(current_time));
                    }
                });
            }
        }
    };

    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!isStart || isWanCheng) {
                System.out.println("BroadcastReceiver??????????????????");
                return;
            }
            System.out.println("BroadcastReceiver???current_time???" + current_time);
            byte[] mData = intent.getByteArrayExtra(WifiMessageReceiver.START_BROADCAST_DATA);
            System.out.println("BroadcastReceiver????????????" + Arrays.toString(mData));
            for (int i = 0; i < mData.length; i++) {
                getMapKayYunDong(mData[i], 0);
            }
        }
    }

    private void getMapKayYunDong(byte qiuhao, int pos) {
        System.out.println("BroadcastReceiver???pos" + pos);
        System.out.println("BroadcastReceiver???HomeTwoXueShengActivity.mMapKey_id.size()" + HomeTwoXueShengActivity.mMapKey_id.size());
        if (HomeTwoXueShengActivity.mMapKey_id.size() <= pos) {
            return;
        }
        //??????mMapKey_id?????????mMapSchoolStudentBeans??????????????????????????????????????????
        List<SchoolStudentBean> mSchoolStudentBeans = HomeTwoXueShengActivity.mMapSchoolStudentBeans.get(HomeTwoXueShengActivity.mMapKey_id.get(pos));
        //?????????????????????????????????????????????????????????????????????
        if (mSchoolStudentBeans != null && mSchoolStudentBeans.size() > current_class_group) {
            //?????????????????????????????????????????????
            SchoolStudentBean mSchoolStudentBean = mSchoolStudentBeans.get(current_class_group);
            System.out.println("BroadcastReceiver???mSchoolStudentBean" + mSchoolStudentBean.getStudentName());
            //??????????????????????????????
            List<SchoolCourseBeanSmallActionInfoJson.StepsBean> mSteps = mSchoolStudentBean.getSteps();
            //???????????????????????? ???????????????????????????????????????
            if (mSteps != null && mSteps.size() > 0 && mSchoolStudentBean.getAllQiuNo() != null) {
                System.out.println("BroadcastReceiver???getAllQiuNo" + mSchoolStudentBean.getAllQiuNo());
                //[16,12]
                for (int q = 0; q < mSchoolStudentBean.getAllQiuNo().size(); q++) {
                    //??????????????????????????????Map????????????????????????
                    Byte key = ConstValuesHttps.MESSAGE_ALL_TOTAL_MAP.get(mSchoolStudentBean.getAllQiuNo().get(q));
                    //??????????????????????????????????????????????????????????????????????????????[10,16] [1]
                    if (key != null && qiuhao == key) {
                        mSchoolStudentBean.setPostZjzs(mSchoolStudentBean.getPostZjzs() + 1);
                        System.out.println("BroadcastReceiver???" + mSchoolStudentBean.getStudentName() + "???????????????:" + mSchoolStudentBean.getPostZjzs());
                        setNotifyDataSetChanged_Fragment();
                        duiBiXueShengStep(mSchoolStudentBean, mSteps, 0);
                        return;
                    }
                }
                System.out.println("BroadcastReceiver???else" + 1);
                getMapKayYunDong(qiuhao, pos + 1);
            } else {
                System.out.println("BroadcastReceiver???else" + 2);
                getMapKayYunDong(qiuhao, pos + 1);
            }
        } else {
            System.out.println("BroadcastReceiver???else" + 3);
            getMapKayYunDong(qiuhao, pos + 1);
        }
    }

    /**
     * @param mSchoolStudentBean ?????????????????????
     * @param mSteps             ???????????????????????????
     * @param posSteps           ???????????????????????????
     */
    private void duiBiXueShengStep(SchoolStudentBean mSchoolStudentBean, List<SchoolCourseBeanSmallActionInfoJson.StepsBean> mSteps, int posSteps) {
        System.out.println("BroadcastReceiver???duiBiXueShengStep()" + mSchoolStudentBean.getStudentName());
        SchoolCourseBeanSmallActionInfoJson.StepsBean mStepsBean = mSteps.get(posSteps);
        System.out.println("BroadcastReceiver???mStepsBean.getCurrentStepNo()" + mSchoolStudentBean.getCurrentStepNo());
        if (mSchoolStudentBean.getCurrentStepNo() == mStepsBean.getStepNo()) {
            mStepsBean.setStepNoOkNum(mStepsBean.getStepNoOkNum() + 1);
        }
        System.out.println("BroadcastReceiver???mStepsBean.getStepNoOkNum():" + mStepsBean.getStepNoOkNum());
        if (mStepsBean.getStepNoOkNum() == mStepsBean.getSets().size()) {
            System.out.println("BroadcastReceiver???duiBiXueShengStep()??????????????????");
            if (mSteps.size() - 1 > posSteps) {
                duiBiXueShengStep(mSchoolStudentBean, mSteps, posSteps + 1);
            } else {
                for (int i = 0; i < mSchoolStudentBean.getSteps().size(); i++) {
                    mSchoolStudentBean.getSteps().get(i).setStepNoOkNum(0);
                }
                mSchoolStudentBean.setPostWccs(mSchoolStudentBean.getPostWccs() + 1);
                setNotifyDataSetChanged_Fragment();
                if (mSchoolStudentBean.getPostWccs() >= HomeTwoXueShengActivity.current_course_section_loop_num) {
//                    ToastUtil.showShortToast(mContext,mSchoolStudentBean.getStudentName()+"?????????");
                    mSchoolStudentBean.setPostZys(current_time);
                    double pjsd = current_time / (mSchoolStudentBean.getSteps().size() * mSchoolStudentBean.getPostWccs());
                    Log.w("BroadcastReceiver", "pjsd:" + StringUtil.getValue(pjsd));
                    mSchoolStudentBean.setPostPjsd(Double.parseDouble(StringUtil.getValue(pjsd)));
                    System.out.println("BroadcastReceiver???duiBiXueShengStep()????????????" + mSchoolStudentBean.getStudentName());
                    setNotifyDataSetChanged_Fragment();
                    isAllOk();
                } else {
                    System.out.println("BroadcastReceiver???duiBiXueShengStep()???????????????--???????????????");
                    duiBiXueShengStep(mSchoolStudentBean, mSteps, 0);
                }
            }
            return;
        }
        System.out.println("BroadcastReceiver???mSteps.size()???" + mSteps.size());
        System.out.println("BroadcastReceiver???posSteps???" + posSteps);
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
                    ToastUtil.showShortToast(getActivity(), "???????????????-->>" + 0);
                }
            }
//            if(mSets.size()==1){//???????????????
//                System.out.println("BroadcastReceiver??????????????????");
//                if(mSets.get(0).size()==7){
//                    sendDatas = new ArrayList<>(mSets.get(0));
//                    sendDatas.set(1, ConstValuesHttps.MESSAGE_ALL_TOTAL_MAP.get(sendDatas.get(1)));
//                    ClientTcpUtils.mClientTcpUtils.sendData_A0_A1(sendDatas.get(0),sendDatas);
//                    mSchoolStudentBean.setCurrentStepNo(mStepsBean.getStepNo());
//                }else{
//                    ToastUtil.showShortToast(getActivity(),"???????????????-->>"+0);
//                }
//            }else {
//                System.out.println("BroadcastReceiver???????????????");
//                sendDatas = new ArrayList<>();
//                byte msg  = 0;
//                for (int j = 0; j < mSets.size(); j++) {
//                    if (mSets.get(j).size() == 7) {
//                        msg = mSets.get(j).get(0);
//                        sendDatas.add(ConstValuesHttps.MESSAGE_ALL_TOTAL_MAP.get(mSets.get(j).get(1)));
//                        sendDatas.add(mSets.get(j).get(2));
//                        sendDatas.add(mSets.get(j).get(3));
//                        sendDatas.add(mSets.get(j).get(4));
//                        sendDatas.add(mSets.get(j).get(5));
//                        sendDatas.add(mSets.get(j).get(6));
//                    } else {
//                        ToastUtil.showShortToast(getActivity(), "???????????????-->>" + j);
//                    }
//                }
//                mSchoolStudentBean.setCurrentStepNo(mStepsBean.getStepNo());
//                ClientTcpUtils.mClientTcpUtils.sendData_A0_A1_dg(msg,sendDatas);
//            }
            return;
        }
        System.out.println("BroadcastReceiver?????????????????????" + mStepsBean.getStepNoOkNum() + "???");
    }

    /**
     * ??????????????????
     */
    private void isAllOk() {
        for (int i = 0; i < HomeTwoXueShengActivity.mMapKey_id.size(); i++) {
            //???????????????????????????
            List<SchoolStudentBean> mSchoolStudentBeans = HomeTwoXueShengActivity.mMapSchoolStudentBeans.get(HomeTwoXueShengActivity.mMapKey_id.get(i));
            if (mSchoolStudentBeans.size() > current_class_group && mSchoolStudentBeans.get(current_class_group).getSteps() != null) {
                if (mSchoolStudentBeans.get(current_class_group).getPostWccs() < HomeTwoXueShengActivity.current_course_section_loop_num) {
                    return;
                }
            }
        }
        isStart = false;
        ((MainActivity) mContext).setFragmentStartOrStop();
        System.out.println("BroadcastReceiver??????????????????");
        current_class_group++;
        if (current_class_group_max > current_class_group) {
            ToastUtil.showShortToast(mContext, "???" + current_class_group + "??????????????????");
        } else {
            current_class_group = 0;
            ToastUtil.showShortToast(mContext, "???????????????");
            if (ConstValues.mSchoolCourseInfoBean != null || ConstValues.mSchoolCourseInfoBeanSmall != null) {
                showDialogKaiShiShangKeXiaYiJie(false);
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
            tv_xieyijie.setText("?????????");
            if (ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().size() - 1 == HomeTwoXueShengActivity.current_course_section) {
                tv_xieyijie.setVisibility(View.GONE);
                tv_xieyijie.setText("?????????");
            }
        } else {
            tv_xieyijie.setVisibility(View.GONE);
        }
    }

    private void showDialogKaiShiShangKeXiaYiJie(boolean isNoOk) {
        Log.w("BroadcastReceiver", "?????????????????????:" + mPostStudentResults.toString());
        if (!isNoOk) {
            HomeTwoXueShengActivity.current_course_section++;
        }
        if (ConstValues.mSchoolCourseInfoBean != null && ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().size() > HomeTwoXueShengActivity.current_course_section) {
            DialogUtils.showDialogKaiShiShangKeXiaYiJie(mContext, isNoOk, ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList()
                    .get(HomeTwoXueShengActivity.current_course_section), new DialogUtils.ErrorDialogInterfaceA() {
                @Override
                public void btnConfirm(int index) {
                    setPostStudentResults();
                    if (index == 1) {//?????????
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
                    } else {//???????????????
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
        setPostStudentResults();
        isWanCheng = true;
        Log.w("BroadcastReceiver", "??????????????????:" + mPostStudentResults.toString());
//        DialogUtils.showDialogWanChengSuoYou(mContext, "????????????????????????\n????????????????????????","??????", new DialogUtils.ErrorDialogInterfaceA() {
//            @Override
//            public void btnConfirm(int index) {
//                ClientTcpUtils.mClientTcpUtils.sendData_B3_add00(true,index==0);
//                isWanCheng = true;
//                strataSetFlags();
//            }
//        });
    }

    /**
     * ????????????
     */
    private void setPostStudentResults() {
        String courseId = null;
        String smallCourseId = null;
        if (ConstValues.mSchoolCourseInfoBean != null) {
            courseId = ConstValues.mSchoolCourseInfoBean.getId();
            smallCourseId = ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().get(HomeTwoXueShengActivity.current_course_section).getSmallCourseId();
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
//                            mPostStudentResult.setClassSceduleCardId("2");
                            mPostStudentResult.setClassId(mSchoolStudentBean.getClassId());
                            mPostStudentResult.setEndTime(StringUtil.getTimeToYMD(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
                            mPostStudentResult.setBeginTime(SharedUtils.singleton().get("postSchoolClassRecord_time", ""));
                            mPostStudentResult.setClassDate(SharedUtils.singleton().get("postSchoolClassRecord_time", ""));
                            mPostStudentResult.setTimeUse(mSchoolStudentBean.getPostZys() + "");
                            mPostStudentResult.setSpeed(mSchoolStudentBean.getPostPjsd() + "");
                            mPostStudentResult.setTimes(mSchoolStudentBean.getPostWccs() + "");
                            mPostStudentResult.setFinishTimes(mSchoolStudentBean.getPostZjzs() + "");
                            mPostStudentResults.add(mPostStudentResult);
                        }
                    }
                }
            }
        }
    }

    private void lianjie() {
        int mBallNum;
        int mPlateNum;
        if (ConstValues.mSchoolCourseInfoBeanSmall != null) {
            mBallNum = ConstValues.mSchoolCourseInfoBeanSmall.getBallNum();
            mPlateNum = ConstValues.mSchoolCourseInfoBeanSmall.getPlateNum();
        } else {
            mBallNum = ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().get(HomeTwoXueShengActivity.current_course_section).getBallNum();
            mPlateNum = ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().get(HomeTwoXueShengActivity.current_course_section).getPlateNum();
        }
        DialogUtils.showDialogLianJieSheBei(mContext, false, mBallNum, mPlateNum,
                new DialogUtils.ErrorDialogInterfaceLianJieSheBei() {
                    @Override
                    public void lianJieNum(int guangQiu, int guangBan, int dengGuang) {
                        int sbNum = guangQiu + guangBan;
                        ConstValuesHttps.MESSAGE_ALL_TOTAL.clear();
                        ConstValuesHttps.MESSAGE_ALL_TOTAL_ZJ.clear();
                        ConstValuesHttps.MESSAGE_ALL_TOTAL_MAP.clear();
                        mWifiMessageReceiver.onWifiMessageReceiverInter(mContext, sbNum, dengGuang, new WifiMessageReceiver.WifiMessageReceiverInter() {
                            @Override
                            public void messageReceiverInter() {
                                current_time = 0;
                                current_class_group = 0;
                                HomeTwoXueShengActivity.initYuDongData();
                                setNotifyDataSetChanged_Fragment();
                            }
                        });
                    }
                });
    }
}



