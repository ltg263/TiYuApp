package com.jxxx.tiyu_app.view.fragment;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxxx.tiyu_app.MainActivity;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.api.RetrofitUtil;
import com.jxxx.tiyu_app.app.ConstValues;
import com.jxxx.tiyu_app.base.BaseFragment;
import com.jxxx.tiyu_app.base.Result;
import com.jxxx.tiyu_app.bean.AuthLoginBean;
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
import com.jxxx.tiyu_app.utils.StringUtil;
import com.jxxx.tiyu_app.utils.ToastUtil;
import com.jxxx.tiyu_app.utils.WifiMessageReceiver;
import com.jxxx.tiyu_app.utils.view.DialogUtils;
import com.jxxx.tiyu_app.view.activity.LoginActivity;
import com.jxxx.tiyu_app.view.adapter.HomeTwoOneListAdapter;
import com.jxxx.tiyu_app.view.adapter.HomeTwoTwoListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeTwoFragment extends BaseFragment{


    @BindView(R.id.iv_icon)
    ImageView mIvIcon;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_type_1)
    TextView mTvType1;
    @BindView(R.id.tv_type_2)
    TextView mTvType2;
    @BindView(R.id.tv_chongzhi)
    TextView tv_chongzhi;
    @BindView(R.id.tv_shangchuan)
    TextView tv_shangchuan;
    @BindView(R.id.tv_jishi)
    TextView tv_jishi;
    @BindView(R.id.rv_one_list)
    RecyclerView mRvOneList;
    @BindView(R.id.rv_two_list)
    RecyclerView mRvTwoList;
    String TAG = "HomeTwoFragment";
    HomeTwoOneListAdapter mHomeTwoOneListAdapter;
    HomeTwoTwoListAdapter mHomeTwoTwoListAdapter;
    MyReceiver mMyReceiver;

    List<List<SchoolStudentBean>> mAllSchoolStudentBeans;//全部的数据
    private int current_course_section = 0;//当前小节课程的下标
    private int current_course_section_loop_num = 0;//当前小节课程的中步骤循环执行的次数
    private int current_course_section_queueing_num = 0;//当前小节课程的执行的次数
    List<PostStudentResults> mPostStudentResults;//最终提交的数据
    List<Integer> listOkDl = new ArrayList<>();
    boolean isWanCheng = false;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home_two;
    }

    @Override
    protected void initView() {
        tv_chongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStart){
                    ToastUtil.showShortToast(mContext,"正在执行中");
                    return;
                }
                DialogUtils.showDialogHint(mContext, "确定重置当前小课程吗?", false, new DialogUtils.ErrorDialogInterface() {
                    @Override
                    public void btnConfirm() {
                        initViewResume();
                    }
                });
            }
        });
        tv_shangchuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("BroadcastReceiver","完成全部课程:"+mPostStudentResults.toString());

                DialogUtils.showDialogHint(mContext, "当前课程未完成\n确定要断开连接并上传吗?", false, new DialogUtils.ErrorDialogInterface() {
                    @Override
                    public void btnConfirm() {
                        ClientTcpUtils.mClientTcpUtils.sendData_B1();
                        ClientTcpUtils.mClientTcpUtils.sendData_B0();
                        DialogUtils.showDialogWanChengSuoYou(mContext, "请断开连接，成绩将自动上传！","断开连接", new DialogUtils.ErrorDialogInterfaceA() {
                            @Override
                            public void btnConfirm(int index) {
                                isWanCheng = true;
                                ClientTcpUtils.mClientTcpUtils.onDestroy();
                                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
        mHomeTwoOneListAdapter = new HomeTwoOneListAdapter(null);
        mRvOneList.setAdapter(mHomeTwoOneListAdapter);
        mHomeTwoOneListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(isStart){
                    ToastUtil.showShortToast(mContext,"正在执行队列"+(current_class_group+1));
                    return;
                }
                current_class_group_show = position;
                mHomeTwoOneListAdapter.setId(mHomeTwoOneListAdapter.getData().get(position).getId());
                mHomeTwoOneListAdapter.notifyDataSetChanged();
                mHomeTwoTwoListAdapter.setNewData(mAllSchoolStudentBeans.get(position));
            }
        });

        mHomeTwoTwoListAdapter = new HomeTwoTwoListAdapter(null);
        mRvTwoList.setAdapter(mHomeTwoTwoListAdapter);
    }

    private void initViewResume() {
        mSchoolStudentBeansReceiver = null;
        listOkDl.clear();
        String mActionInfoJson = null;
        if(ConstValues.mSchoolCourseInfoBean!=null){//大课程信息
            SchoolCourseBean.CourseSectionVoListBean mCourseSectionVoList = ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().get(current_course_section);

            SchoolCourseBeanSmall mSmallCourseVo = mCourseSectionVoList.getSmallCourseVo();
            current_course_section_loop_num = mCourseSectionVoList.getLoopNum();
            current_course_section_queueing_num = mCourseSectionVoList.getQueueingNum();
            GlideImgLoader.loadImageViewRadiusNoCenter(mContext,mSmallCourseVo.getImgUrl(),mIvIcon);
            mTvName.setText(mSmallCourseVo.getCourseName());
            mTvType1.setText(mSmallCourseVo.getLables().replace(",", "|"));
            mTvType2.setText("每组" + mSmallCourseVo.getGroupNum() + "人  |  共" + mSmallCourseVo.getStepNum() + "个步骤");
            //让控制球
            mActionInfoJson = mSmallCourseVo.getActionInfo();
            //准备提交的数据
            for(int j = 0;j<ConstValues.mSchoolStudentInfoBean.size();j++){
                PostStudentResults mPostStudentResult = new PostStudentResults();
                mPostStudentResult.setCourseId(mCourseSectionVoList.getCourseId());
                mPostStudentResult.setSmallCourseId(mCourseSectionVoList.getId());
                mPostStudentResult.setStudentId(ConstValues.mSchoolStudentInfoBean.get(j).getId());
                mPostStudentResults.add(mPostStudentResult);
            }
        }
        if(ConstValues.mSchoolCourseInfoBeanSmall!=null){//小课程信息
            current_course_section_loop_num = 1;
            current_course_section_queueing_num = 1;
            GlideImgLoader.loadImageViewRadiusNoCenter(mContext,ConstValues.mSchoolCourseInfoBeanSmall.getImgUrl(),mIvIcon);
            mTvName.setText(ConstValues.mSchoolCourseInfoBeanSmall.getCourseName());
            mTvType1.setText(ConstValues.mSchoolCourseInfoBeanSmall.getLables().replace(",", "|"));
            mTvType2.setText("每组" + ConstValues.mSchoolCourseInfoBeanSmall.getGroupNum() + "人  |  共" + ConstValues.mSchoolCourseInfoBeanSmall.getStepNum() + "个步骤");
            //让控制球
            mActionInfoJson = ConstValues.mSchoolCourseInfoBeanSmall.getActionInfo();


            //准备提交的数据
            for(int j = 0;j<ConstValues.mSchoolStudentInfoBean.size();j++){
                PostStudentResults mPostStudentResult = new PostStudentResults();
//                mPostStudentResult.setCourseId(ConstValues.mSchoolCourseInfoBeanSmall.getCourseId());
                mPostStudentResult.setSmallCourseId(ConstValues.mSchoolCourseInfoBeanSmall.getId());
                mPostStudentResult.setStudentId(ConstValues.mSchoolStudentInfoBean.get(j).getId());
                mPostStudentResults.add(mPostStudentResult);
            }
        }

        mHomeTwoOneListAdapter.setId(ConstValues.mSchoolClassInfoBean.getClassGroupList().get(0).getId());
        mHomeTwoOneListAdapter.setNewData(ConstValues.mSchoolClassInfoBean.getClassGroupList());

        Log.w(TAG,"mActionInfoJson:"+mActionInfoJson);
        if(StringUtil.isBlank(mActionInfoJson)) {
            ToastUtil.showShortToast(mContext,"此课程数据有问题");
            return;
        }
        List<SchoolCourseBeanSmallActionInfoJson> mSchoolCourseBeanSmallActionInfoJson = JSON.parseArray(mActionInfoJson, SchoolCourseBeanSmallActionInfoJson.class);
        if(mAllSchoolStudentBeans!=null){
            mAllSchoolStudentBeans.clear();
            mAllSchoolStudentBeans = null;
        }
        mAllSchoolStudentBeans = new ArrayList<>();
        List<SchoolClassBean.ClassGroupListBean> mClassGroupLists = ConstValues.mSchoolClassInfoBean.getClassGroupList();
        for(int i = 0;i<mClassGroupLists.size();i++){
            List<SchoolStudentBean> mSchoolStudentBeans = new ArrayList<>();//1个队列的数据
            List<String> mStudentIds = Arrays.asList(mClassGroupLists.get(i).getStudentIds().split(","));
            for(int j = 0;j<ConstValues.mSchoolStudentInfoBean.size();j++){
                if(mStudentIds.contains(ConstValues.mSchoolStudentInfoBean.get(j).getId())){
                    SchoolStudentBean mSchoolStudentBean = ConstValues.mSchoolStudentInfoBean.get(j);
                    mSchoolStudentBean.setPostWccs(0);
                    mSchoolStudentBean.setPostZjzs(0);
                    mSchoolStudentBean.setPostZys(0);
                    mSchoolStudentBean.setPostPjsd(0);
                    mSchoolStudentBean.setSteps(null);
                    mSchoolStudentBeans.add(mSchoolStudentBean);
                }
            }
            for(int j = 0;j<mSchoolCourseBeanSmallActionInfoJson.size();j++){
                if(mSchoolStudentBeans.size()>j){
                    List<SchoolCourseBeanSmallActionInfoJson> json = JSON.parseArray(mActionInfoJson, SchoolCourseBeanSmallActionInfoJson.class);
                    mSchoolStudentBeans.get(j).setSteps(json.get(j).getSteps());
                }
            }
            mAllSchoolStudentBeans.add(mSchoolStudentBeans);
        }

        mHomeTwoTwoListAdapter.setNewData(mAllSchoolStudentBeans.get(0));
    }


    @Override
    public void onResume() {
        super.onResume();
        if(!isWanCheng){
            mPostStudentResults = new ArrayList<>();
            initViewResume();
            /**
             * 广播动态注册
             */
            mMyReceiver = new MyReceiver();//集成广播的类
            IntentFilter filter = new IntentFilter("com.jxxx.tiyu_app.view.fragment");// 创建IntentFilter对象
            mContext.registerReceiver(mMyReceiver, filter);// 注册Broadcast Receive
            return;
        }
        if(!StringUtil.isNetworkConnected(mContext)){
            DialogUtils.showDialogWanChengSuoYou(mContext, "请连接可用的网络，成绩将自动上传！","连接网络", new DialogUtils.ErrorDialogInterfaceA() {
                @Override
                public void btnConfirm(int index) {
                    Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
            return;
        }
        //断开连接提交数据
        try {
            if(mMyReceiver!=null){
                mContext.unregisterReceiver(mMyReceiver);
                mMyReceiver= null;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        if(mPostStudentResults!=null && mPostStudentResults.size()>0){
            for (int i=mPostStudentResults.size()-1;i>=0;i--){
                if(mPostStudentResults.get(i).getTimes()==null){
                    mPostStudentResults.remove(i);
                }
            }
            Log.w("提交数据：","mPostStudentResults:"+mPostStudentResults.toString());
            if(mPostStudentResults.size()>0){
                postResultsBatchAdd();
            }else{
                MainActivity.indexPos = 0;
                ((MainActivity)mContext).onResume();
                isWanCheng = false;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void postResultsBatchAdd() {
        showLoading();
        RetrofitUtil.getInstance().apiService()
                .postResultsBatchAdd(mPostStudentResults)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result result) {
                        if(isResultOk(result)){
                            MainActivity.indexPos = 0;
                            ((MainActivity)mContext).onResume();
                            isWanCheng = false;
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

    @Override
    protected void initData() {

    }

    private boolean isStart = false;//是否开始
    private long current_time = 0;//执行的时间
    private Handler heartHandler = new Handler();
    /**
     * 计时器
     */
    private Runnable hearRunable = new Runnable() {
        @Override
        public void run() {
            if(isStart){
                current_time++;
                heartHandler.postDelayed(hearRunable, 1000);
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_jishi.setText(StringUtil.getValue(current_time));
                    }
                });
            }
        }
    };

    /**
     * 执行暂停|开始
     * @param isStart
     */
    public boolean startOrStop(boolean isStart){
        if(!isStart){
            this.isStart = false;
            return true;
        }
        if(listOkDl.contains(current_class_group_show)){
            ToastUtil.showShortToast(mContext,"当前队列已全部完成");
            return false;
        }

        if(mSchoolStudentBeansReceiver!=null){
            for(int i = 0;i<mSchoolStudentBeansReceiver.size();i++){
                if(mSchoolStudentBeansReceiver.get(i).getSteps()==null){
                    startYunXing();
                    return true;
                }
                if(mSchoolStudentBeansReceiver.get(i).getPostZys()==0){
                    if(current_class_group == current_class_group_show){
                        startYunXing();
                        return true;
                    }
                    ToastUtil.showShortToast(mContext,"队列"+(current_class_group+1)+"还未执行完毕");
                    return false;
                }
            }
        }else{
            mSchoolStudentBeansReceiver = mAllSchoolStudentBeans.get(current_class_group);
        }
        startYunXing();
        return true;
    }

    private void startYunXing() {
        if(current_class_group != current_class_group_show){
            current_time = 0;
        }
        current_class_group = current_class_group_show;
        mSchoolStudentBeansReceiver = mAllSchoolStudentBeans.get(current_class_group);
        if(current_time == 0){
            sendDataInit();
        }
        isStart = true;
        heartHandler.postDelayed(hearRunable, 1000);
    }

    /**
     * 初始化首个球
     */
    private void sendDataInit() {
//        String mActionInfoJson = mSchoolStudentBeansReceiver.getActionInfo();
//        Log.w(TAG,"mActionInfoJson:"+mActionInfoJson);
//        List<SchoolCourseBeanSmallActionInfoJson> mSchoolCourseBeanSmallActionInfoJson = JSON.parseArray(mActionInfoJson, SchoolCourseBeanSmallActionInfoJson.class);
        List<Byte> sendDatas = new ArrayList<>();
        for(int i=0;i<mSchoolStudentBeansReceiver.size();i++){
            List<SchoolCourseBeanSmallActionInfoJson.StepsBean> mSteps = mSchoolStudentBeansReceiver.get(i).getSteps();
            if(mSteps==null){
                break;
            }
            //让第一队列的球亮起来
            List<List<Byte>> mSets = mSteps.get(0).getSets();
            for(int j=0;j<mSets.size();j++){
                if(mSets.get(j).size()==6){
                    sendDatas.addAll(mSets.get(j));
                }else{
                    ToastUtil.showShortToast(getActivity(),"有错误数据-->>"+j);
                }
            }
        }
        ClientTcpUtils.mClientTcpUtils.sendData_A0_dg(sendDatas);
        ClientTcpUtils.mClientTcpUtils.sendData_B0();
    }

    private int current_class_group = 0;//正在执行的学生队列
    private int current_class_group_show = 0;//当前显示的队列
    private List<SchoolStudentBean> mSchoolStudentBeansReceiver;//学生队列的信息
    private int schoolStudentPos;//学生队列中某一个学生的信息
    private byte startBroadcastData;//球号
    private List<Byte> sendDatas;//发送的数据

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(!isStart){
                System.out.println("BroadcastReceiver：设备暂停中");
                return;
            }
            System.out.println("BroadcastReceiver：current_time：" + current_time);
            startBroadcastData = intent.getByteArrayExtra(WifiMessageReceiver.START_BROADCAST_DATA)[0];
            startBroadcastData = (byte) (1+Math.random()*6);
            System.out.println("BroadcastReceiver：球号：" + startBroadcastData);
            sendDatas = new ArrayList<>();
            if(mSchoolStudentBeansReceiver==null){
                mSchoolStudentBeansReceiver = mAllSchoolStudentBeans.get(current_class_group);
            }
            schoolStudentPos = 0;
            getSchoolStudentBeans();
            if(sendDatas.size()>0){
                ClientTcpUtils.mClientTcpUtils.sendData_A0_dg(sendDatas);
                ClientTcpUtils.mClientTcpUtils.sendData_B0();
            }
            mHomeTwoTwoListAdapter.notifyDataSetChanged();
        }
    }
    private void getSchoolStudentBeans() {
        if(mSchoolStudentBeansReceiver.size()>schoolStudentPos){
            getSets(mSchoolStudentBeansReceiver.get(schoolStudentPos),0);
        }else{
            Log.w("BroadcastReceiver","击中错误的球:getSchoolStudentBeans");
        }
    }

    private void getSets(SchoolStudentBean mSchoolStudentBean, int stepsPos) {
        if(mSchoolStudentBean.getSteps()==null){
            Log.w("BroadcastReceiver","击中错误的球:getSets");
            return;
        }
        if(mSchoolStudentBean.getPostZys()>0){
            schoolStudentPos++;
            getSchoolStudentBeans();
            Log.w("BroadcastReceiver",mSchoolStudentBean.getStudentName()+"已全部完成");
            for(int i=0;i<mPostStudentResults.size();i++){
                if(mPostStudentResults.get(i).getStudentId().equals(mSchoolStudentBean.getId())){
                    Log.w("BroadcastReceiver", mPostStudentResults.get(i).toString());
                }
            }
            return;
        }
        if(mSchoolStudentBean.getSteps().size()>stepsPos){
            getSetsList(mSchoolStudentBean,stepsPos,
                    mSchoolStudentBean.getSteps().get(stepsPos).getSets_cz(),
                    mSchoolStudentBean.getSteps().get(stepsPos).getSets(),0);
        }else{
            Log.w("BroadcastReceiver","击中错误的球:getSets-else");
        }
    }

    private void getSetsList(SchoolStudentBean mSchoolStudentBean,int stepsPos,List<List<Byte>> sets_cz, List<List<Byte>> sets, int setsListPos) {
        if(sets.size() > setsListPos){
            List<Byte> lists = sets.get(setsListPos);
            if(sets_cz.equals(sets)){
                Log.w("BroadcastReceiver","已被击打过的球");
                getSetsList(mSchoolStudentBean,stepsPos,sets_cz,sets,setsListPos+1);
            }else{
                if(startBroadcastData==lists.get(0)){
                    mSchoolStudentBean.setPostZjzs(mSchoolStudentBean.getPostZjzs()+1);
                    if(mSchoolStudentBean.getSteps().size()-1 > stepsPos){
                        sets_cz.add(lists);
                        List<Byte> dldq = mSchoolStudentBean.getSteps().get(stepsPos + 1).getSets().get(setsListPos);//准备下一组被亮的球
                        sendDatas.addAll(dldq);
                        Log.w("BroadcastReceiver",mSchoolStudentBean.getStudentName()+"击中了一次去发送数据:"+sendDatas);
                        if(sets.size()-1>setsListPos){
                            Log.w("BroadcastReceiver",mSchoolStudentBean.getStudentName()+"击中了其中一个"+sendDatas);
                            getSetsList(mSchoolStudentBean,stepsPos,sets_cz,sets,setsListPos+1);
                        }
                    }else{
                        mSchoolStudentBean.setPostWccs(mSchoolStudentBean.getPostWccs()+1);
                        if(current_course_section_loop_num == mSchoolStudentBean.getPostWccs()){//已全部完成
                            mSchoolStudentBean.setPostZys(current_time);
                            double pjsd = current_time / ((mSchoolStudentBean.getSteps().size() - 1d) * mSchoolStudentBean.getPostWccs());
                            mSchoolStudentBean.setPostPjsd(Double.parseDouble(StringUtil.getValue(pjsd)));
                            //保存某个学习的信息
                            for(int i=0;i<mPostStudentResults.size();i++){
                                if(mPostStudentResults.get(i).getStudentId().equals(mSchoolStudentBean.getId())){
                                    mPostStudentResults.get(i).setTimeUse(mSchoolStudentBean.getPostZys()+"");
                                    mPostStudentResults.get(i).setSpeed(mSchoolStudentBean.getPostPjsd()+"");
                                    mPostStudentResults.get(i).setTimes(mSchoolStudentBean.getPostWccs()+"");
                                    mPostStudentResults.get(i).setFinishTimes(mSchoolStudentBean.getPostZjzs()+"");
                                    Log.w("BroadcastReceiver","已完成:"+mPostStudentResults.get(i));
                                }
                            }
                            boolean isDuiOk = false;
                            for(int i = 0;i<mSchoolStudentBeansReceiver.size();i++){
                                if(mSchoolStudentBeansReceiver.get(i).getSteps()==null){
                                    isDuiOk = true;
                                    break;
                                }
                                if(mSchoolStudentBeansReceiver.get(i).getPostZys()==0){
                                    isDuiOk = false;
                                    break;
                                }
                                isDuiOk = true;
                            }
                            if(isDuiOk){
                                ToastUtil.showShortToast(mContext,"当前队列已全部完成");
                                listOkDl.add(current_class_group);
                                ((MainActivity)mContext).setFragmentStartOrStop();
                            }
                            if(listOkDl.size() == mHomeTwoOneListAdapter.getData().size()){
                                ToastUtil.showShortToast(mContext,"已完成小课程");
                                showDialogKaiShiShangKeXiaYiJie();
                            }

                        } else {//从0位置执行下次循环
                            List<Byte> dldq = mSchoolStudentBean.getSteps().get(0).getSets().get(setsListPos);//准备下一组被亮的球
                            sendDatas.addAll(dldq);
                            Log.w("BroadcastReceiver","完成一次清空标记:"+mSchoolStudentBean.getStudentName());
                            for(int i=0;i<mSchoolStudentBean.getSteps().size();i++){
                                mSchoolStudentBean.getSteps().get(i).getSets_cz().clear();
                            }
                        }
                    }
                }else{
                    schoolStudentPos++;
                    getSchoolStudentBeans();
                    Log.w("BroadcastReceiver","切换学生");
                }
            }
        }else{
            getSets(mSchoolStudentBean,stepsPos+1);
        }

    }

    private void showDialogKaiShiShangKeXiaYiJie() {
        Log.w("BroadcastReceiver","全部提交的数据:"+mPostStudentResults.toString());
        current_course_section++;
        if(ConstValues.mSchoolCourseInfoBean!=null && ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().size() > current_course_section){
            DialogUtils.showDialogKaiShiShangKeXiaYiJie(mContext, ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().get(current_course_section), new DialogUtils.ErrorDialogInterfaceA() {
                @Override
                public void btnConfirm(int index) {
                    if(index==1){//下一节
                        initViewResume();
                    }else{//跳过下一节
                        showDialogKaiShiShangKeXiaYiJie();
                    }
                }
            });
        }else{
            Log.w("BroadcastReceiver","完成全部课程:"+mPostStudentResults.toString());
            ClientTcpUtils.mClientTcpUtils.sendData_B1();
            ClientTcpUtils.mClientTcpUtils.sendData_B0();
            DialogUtils.showDialogWanChengSuoYou(mContext, "所有课程已完成！\n请断开连接，成绩将自动上传！","断开连接", new DialogUtils.ErrorDialogInterfaceA() {
                @Override
                public void btnConfirm(int index) {
                    isWanCheng = true;
                    ClientTcpUtils.mClientTcpUtils.onDestroy();
                    Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        }
    }
}



