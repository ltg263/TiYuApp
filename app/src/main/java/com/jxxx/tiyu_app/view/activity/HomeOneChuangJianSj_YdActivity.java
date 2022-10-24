package com.jxxx.tiyu_app.view.activity;

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

import com.jxxx.tiyu_app.MainActivity;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.app.ConstValues;
import com.jxxx.tiyu_app.base.BaseActivity;
import com.jxxx.tiyu_app.bean.SchoolCourseBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBeanSmallActionInfoJson;
import com.jxxx.tiyu_app.bean.SchoolStudentBean;
import com.jxxx.tiyu_app.tcp_tester.ClientTcpUtils;
import com.jxxx.tiyu_app.tcp_tester.ConstValuesHttps;
import com.jxxx.tiyu_app.utils.StringUtil;
import com.jxxx.tiyu_app.utils.ToastUtil;
import com.jxxx.tiyu_app.utils.WifiMessageReceiver;
import com.jxxx.tiyu_app.utils.view.DialogUtils;
import com.jxxx.tiyu_app.view.adapter.HomeTwoTwoListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class HomeOneChuangJianSj_YdActivity extends BaseActivity {
    byte[] sendInitData;
    //每个队列人数，每个人控制的球数
    int duilieNum, sheBeiNum,time;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.ma_iv_index)
    ImageView ma_iv_index;
    @BindView(R.id.ma_iv_index_1)
    ImageView ma_iv_index_1;
    @BindView(R.id.rv_two_list)
    RecyclerView mRvTwoList;
    @BindView(R.id.tv_jishi)
    TextView mTvJishi;
    @BindView(R.id.tv_title_fu)
    TextView tv_title_fu;
    MyReceiver mMyReceiver;
    List<SchoolStudentBean> mSchoolStudentBeans;
    HomeTwoTwoListAdapter mHomeTwoTwoListAdapter;

    boolean isWanCheng = false;
    @Override
    public int intiLayout() {
        return R.layout.activity_suiji_yundong;
    }

    @Override
    public void initView() {
        mIvBack.setOnClickListener(v -> endYunDong());
        tv_title_fu.setOnClickListener(v -> endYunDong());
        sheBeiNum = getIntent().getIntExtra("sheBeiNum", 0);
        duilieNum = getIntent().getIntExtra("duilieNum", 0);
        time = getIntent().getIntExtra("time", 0);
        sendInitData = getIntent().getByteArrayExtra("sendData");
        getSJData();
        mHomeTwoTwoListAdapter = new HomeTwoTwoListAdapter(mSchoolStudentBeans);
        mRvTwoList.setAdapter(mHomeTwoTwoListAdapter);

        ma_iv_index.getDrawable().setLevel(2);
        ma_iv_index_1.getDrawable().setLevel(0);

        ma_iv_index.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isStart){
                    if(current_time!=0 && time!=0 && current_time>=time*60){
                        ToastUtil.showShortToast(HomeOneChuangJianSj_YdActivity.this,"执行完毕");
                        return;
                    }
                    isStart = true;
                    ma_iv_index.getDrawable().setLevel(1);
                    if(current_time==0){
                        sendDataInit();
                        heartHandler.postDelayed(hearRunable, 1000);
                    }
                }else{
                    ma_iv_index.getDrawable().setLevel(2);
                    isStart = false;
                }
            }
        });

        ma_iv_index_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showDialogHint(HomeOneChuangJianSj_YdActivity.this, "确定重置数据吗?", false, new DialogUtils.ErrorDialogInterface() {
                    @Override
                    public void btnConfirm() {
                        ma_iv_index.getDrawable().setLevel(2);
                        isStart = false;
                        current_time = 0;
                        getSJData();
                        mHomeTwoTwoListAdapter.setNewData(mSchoolStudentBeans);
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        endYunDong();
    }

    private void endYunDong() {
        DialogUtils.showDialogHint(this, "确定终止当前模式吗?", false, new DialogUtils.ErrorDialogInterface() {
            @Override
            public void btnConfirm() {
                DialogUtils.showDialogWanChengSuoYou(HomeOneChuangJianSj_YdActivity.this,
                        "请断开设备连接\n链接到可用网络","断开连接", new DialogUtils.ErrorDialogInterfaceA() {
                    @Override
                    public void btnConfirm(int index) {
                        ClientTcpUtils.mClientTcpUtils.sendData_B3_add00();
                        if(index==0){
                            ClientTcpUtils.mClientTcpUtils.sendData_B1();
                            ClientTcpUtils.mClientTcpUtils.sendData_B0();
                        }
                        isWanCheng = true;
                        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(isWanCheng){
            ClientTcpUtils.mClientTcpUtils.onDestroy();
            finish();
        }
    }

    private void getSJData() {
        mSchoolStudentBeans = new ArrayList<>();
        List<Byte> bytes = new ArrayList<>();
        for(int i=0;i<duilieNum;i++){
            List<byte[]> lists  = new ArrayList<>();
            SchoolStudentBean mSchoolStudentBean = new SchoolStudentBean();
            mSchoolStudentBean.setStudentName(i+"号成员");
            mSchoolStudentBean.setPostWccs(0);
            mSchoolStudentBean.setPostZjzs(0);
            mSchoolStudentBean.setPostZys(0);
            mSchoolStudentBean.setPostPjsd(0);
            for(int j=0;j<sheBeiNum;j++){//5
                byte v;
                do{
                    v = (byte) (Math.random()*duilieNum*sheBeiNum+1);
                }while (bytes.contains(v));
                sendInitData[1] = v;
                bytes.add(v);
                byte[] sendDataNew = new byte[sendInitData.length];
                for(int a = 0;a<sendDataNew.length; a++){
                    sendDataNew[a]=sendInitData[a];
                }
                lists.add(sendDataNew);
            }
            mSchoolStudentBean.setLists(lists);
            mSchoolStudentBeans.add(mSchoolStudentBean);
        }
    }

    /**
     * 初始化首个球
     */
    private void sendDataInit() {
        List<Byte> new_bytes = new ArrayList<>();
        byte msg = 0;
        for(int i=0;i<mSchoolStudentBeans.size();i++){
            List<byte[]> mLists = mSchoolStudentBeans.get(i).getLists();
            byte[] bytes = mLists.get(0);
            if(bytes.length==7){
                msg = bytes[0];
                new_bytes.add(ConstValuesHttps.MESSAGE_ALL_TOTAL_MAP.get(bytes[1]));
                if(bytes[2]==-1){
                    byte num = (byte) (Math.random()*(7+1));
                    if(num==0){
                        num = 2;
                    }
                    new_bytes.add(num);
                }else{
                    new_bytes.add(bytes[2]);
                }
                new_bytes.add(bytes[3]);
                new_bytes.add(bytes[4]);
                new_bytes.add(bytes[5]);
                new_bytes.add(bytes[6]);
            }else{
                ToastUtil.showShortToast(this,"有错误数据-->>"+bytes);
            }
        }
        if(msg!=0 &&new_bytes.size()>0 ){
            byte[] mData = new byte[new_bytes.size()];
            for(int i=0;i<new_bytes.size();i++){
                if(new_bytes.get(i)==null){
                    mData[i] = 0;
                }else{
                    mData[i] = new_bytes.get(i);
                }
            }
            ClientTcpUtils.mClientTcpUtils.sendData_A0_A1_sj(msg,mData);
        }
    }

    @Override
    public void initData() {
        /**
         * 广播动态注册
         */
        mMyReceiver = new MyReceiver();//集成广播的类
        IntentFilter filter = new IntentFilter("com.jxxx.tiyu_app.view.fragment");// 创建IntentFilter对象
        registerReceiver(mMyReceiver, filter);// 注册Broadcast Receive
    }
    private boolean isStart = false;//是否开始
    private long current_time = 0;//执行的时间
    private Handler heartHandler = new Handler();
    private List<Byte> startBroadcastData = new ArrayList<>();//球号
    private List<Byte> sendDatas;//发送的数据
    /**
     * 计时器
     */
    private Runnable hearRunable = new Runnable() {
        @Override
        public void run() {
            if(isStart){
                current_time++;
                heartHandler.postDelayed(hearRunable, 1000);
                if(current_time>=time*60 && time!=0){
                    ma_iv_index.getDrawable().setLevel(2);
                    isStart = false;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvJishi.setText(StringUtil.getValue(current_time));
                    }
                });
            }
        }
    };

    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(!isStart){
                System.out.println("BroadcastReceiver：设备暂停中");
                return;
            }
            System.out.println("BroadcastReceiver：current_time：" + current_time);
            byte[] mData = intent.getByteArrayExtra(WifiMessageReceiver.START_BROADCAST_DATA);
            startBroadcastData.clear();
            for(int i=0;i<mData.length;i++){
                startBroadcastData.add(mData[i]);
            }
            System.out.println("BroadcastReceiver：球号：" +startBroadcastData);
            sendDatas = new ArrayList<>();
            byte msg = 0;
            for(int i=0;i<mSchoolStudentBeans.size();i++){
                List<byte[]> mLists = mSchoolStudentBeans.get(i).getLists();
                for(int j=0;j<mLists.size();j++){
                    byte[] bytes = mLists.get(j);
                    if(startBroadcastData.contains(ConstValuesHttps.MESSAGE_ALL_TOTAL_MAP.get(bytes[1]))){
                        if(j==mLists.size()-1){
                            byte[] bytel = mLists.get(0);
                            if(bytel.length==7){
                                msg = bytel[0];
                                sendDatas.add(ConstValuesHttps.MESSAGE_ALL_TOTAL_MAP.get(bytel[1]));
                                if(bytel[2]==-1){
                                    byte num = (byte) (Math.random()*(7+1));
                                    if(num==0){
                                        num = 2;
                                    }
                                    sendDatas.add(num);
                                }else{
                                    sendDatas.add(bytel[2]);
                                }
                                sendDatas.add(bytel[3]);
                                sendDatas.add(bytel[4]);
                                sendDatas.add(bytel[5]);
                                sendDatas.add(bytel[6]);
                                mSchoolStudentBeans.get(i).setPostZjzs(mSchoolStudentBeans.get(i).getPostZjzs()+1);
                                mSchoolStudentBeans.get(i).setPostWccs(mSchoolStudentBeans.get(i).getPostWccs()+1);
                                mHomeTwoTwoListAdapter.notifyDataSetChanged();
                            }else{
                                ToastUtil.showShortToast(HomeOneChuangJianSj_YdActivity.this,"有错误数据-->>"+bytes);
                            }
                        }else{
                            byte[] bytel = mLists.get(j+1);
                            if(bytel.length==7){
                                msg = bytel[0];
                                sendDatas.add(ConstValuesHttps.MESSAGE_ALL_TOTAL_MAP.get(bytel[1]));
                                if(bytel[2]==-1){
                                    byte num = (byte) (Math.random()*(7+1));
                                    if(num==0){
                                        num = 2;
                                    }
                                    sendDatas.add(num);
                                }else{
                                    sendDatas.add(bytel[2]);
                                }
                                sendDatas.add(bytel[3]);
                                sendDatas.add(bytel[4]);
                                sendDatas.add(bytel[5]);
                                sendDatas.add(bytel[6]);
                                mSchoolStudentBeans.get(i).setPostZjzs(mSchoolStudentBeans.get(i).getPostZjzs()+1);
                                mHomeTwoTwoListAdapter.notifyDataSetChanged();
                            }else{
                                ToastUtil.showShortToast(HomeOneChuangJianSj_YdActivity.this,"有错误数据-->>"+bytes);
                            }
                        }
                    }
                }
            }
            if(sendDatas!=null  && sendDatas.size()>0){
                byte[] mSendData_new = new byte[sendDatas.size()];
                for(int i=0;i<sendDatas.size();i++){
                    if(sendDatas.get(i)==null){
                        mSendData_new[i] = 0;
                    }else{
                        mSendData_new[i] = sendDatas.get(i);
                    }
                }
                ClientTcpUtils.mClientTcpUtils.sendData_A0_A1_sj(msg,mSendData_new);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mMyReceiver!=null){
            unregisterReceiver(mMyReceiver);
            mMyReceiver = null;
        }
    }
}
