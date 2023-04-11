package com.jxxx.tiyu_app.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.app.ConstValues;
import com.jxxx.tiyu_app.app.MainApplication;
import com.jxxx.tiyu_app.tcp_tester.ClientTcpUtils;
import com.jxxx.tiyu_app.tcp_tester.ConstValuesHttps;
import com.jxxx.tiyu_app.utils.view.LoadingDialog;
import com.jxxx.tiyu_app.utils.view.StepArcView_n;
import com.jxxx.tiyu_app.view.activity.HomeTwoXueShengActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ViewCollections;

public class WifiMessageReceiver extends BroadcastReceiver {

    int sbNum,dengGuang;//设备数量、灯光模式（室内|室外）
    Context mContext;
    boolean isShowCurrentActivity,isSuiJi=false;
    WifiMessageReceiverInter mWifiMessageReceiverInter;
    public interface WifiMessageReceiverInter {
        /**
         * 确定
         */
        public void messageReceiverInter();
    }

    public void setSuiJi(boolean suiJi) {
        isSuiJi = suiJi;
    }

    public WifiMessageReceiver(){
        sortNumSet = null;
    }
    public WifiMessageReceiver(int sbNum,int dengGuang) {
        this.sbNum = sbNum;
        this.dengGuang = dengGuang;
        isShowCurrentActivity = true;
        sortNumSet = null;
    }

    public void setSbNum(int sbNum) {
        this.sbNum = sbNum;
    }

    public void setDengGuang(int dengGuang) {
        this.dengGuang = dengGuang;
    }

    public void setWifiMessageReceiverInter(WifiMessageReceiverInter wifiMessageReceiverInter) {
        mWifiMessageReceiverInter = wifiMessageReceiverInter;
        sortNumSet = null;
    }

    public void onWifiMessageReceiverInter (Context mContext, int sbNum, int dengGuang, WifiMessageReceiverInter mWifiMessageReceiverInter){
        isShowCurrentActivity = true;
        this.mWifiMessageReceiverInter = mWifiMessageReceiverInter;
        this.sbNum = sbNum;
        this.dengGuang = dengGuang;
        sortNumSet = null;
        showDialogXunQiu(mContext,sbNum,false,false);
    }

    @Override
    public void onReceive(Context mContext, Intent intent) {
        this.mContext = mContext;
        byte mStartBroadcastType = intent.getByteExtra(WifiMessageReceiver.START_BROADCAST_TYPE, (byte) 0X00);
        byte[] startBroadcastData = intent.getByteArrayExtra(WifiMessageReceiver.START_BROADCAST_DATA);
        Log.i("BroadcastReceiver", "onReceive: " + Integer.toHexString(mStartBroadcastType & 0xFF));
        if(mStartBroadcastType == WifiMessageReceiver.START_BROADCAST_TYPE_CLOSE){
            System.out.println("连接已断开");
            if(!isShowCurrentActivity){
                return;
            }
            if(dialog!=null && dialog.isShowing()){
                try {
                    dialog.dismiss();
                }catch (IllegalArgumentException e){

                }
            }
            Toast.makeText(mContext,"断开连接",Toast.LENGTH_SHORT).show();
        }
        if(mStartBroadcastType == WifiMessageReceiver.START_BROADCAST_TYPE_CONNECT_JT){
            System.out.println("开始监听");
            if(!isShowCurrentActivity){
                return;
            }
            showDialogXunQiu(mContext,sbNum,true,true);
        }
        if(mStartBroadcastType == WifiMessageReceiver.START_BROADCAST_TYPE_CONNECT){
            System.out.println("连接成功");
            if(!isShowCurrentActivity){
                return;
            }
//            showDialogXunQiu(mContext,sbNum,true);
            ClientTcpUtils.mClientTcpUtils.sendData_B3_add00_all(new ClientTcpUtils.SendDataOkInterface() {
                @Override
                public void sendDataOk(byte msg) {
                    ((Activity)mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(dialog.isShowing()){
                                btn_xunqiu.setText("开始寻球");
                                tv_title.setText("恭喜您\n设备已连接成功！");
                            }
                        }
                    });
                }
            });
        }
        if(mStartBroadcastType == ConstValuesHttps.MESSAGE_GET_C0){
            if(startBroadcastData==null){
                return;
            }
            if(!isShowCurrentActivity){
                return;
            }
            if(sortNumSet==null){
                ConstValuesHttps.MESSAGE_ALL_TOTAL.clear();
                ConstValuesHttps.MESSAGE_ALL_TOTAL_ZJ.clear();
                ConstValuesHttps.MESSAGE_ALL_TOTAL_MAP.clear();
                if(isSuiJi){
                    ConstValues.mSchoolCourseInfoBean = null;
                    ConstValues.mSchoolCourseInfoBeanSmall = null;
                    sortNumSet = new String[sbNum];
                    for(int s=0;s<sortNumSet.length;s++){
                        sortNumSet[s] = (s+1)+"";
                    }
                }
                if(ConstValues.mSchoolCourseInfoBean!=null
                        && ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList()!=null
                        && ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().size()>HomeTwoXueShengActivity.current_course_section
                        && StringUtil.isNotBlank(ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().get(HomeTwoXueShengActivity.current_course_section).getSmallCourseVo().getSortNumSet())){
                    sortNumSet = ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().get(HomeTwoXueShengActivity.current_course_section).getSmallCourseVo().getSortNumSet().split(",");
                }else if(ConstValues.mSchoolCourseInfoBeanSmall != null
                        && StringUtil.isNotBlank(ConstValues.mSchoolCourseInfoBeanSmall.getSortNumSet())){
                    sortNumSet = ConstValues.mSchoolCourseInfoBeanSmall.getSortNumSet().split(",");
                }
            }
            String str = Integer.toHexString(startBroadcastData[0] & 0xFF)+ Integer.toHexString(startBroadcastData[1] & 0xFF);

            int lightness = Integer.parseInt(str, 16);
            Log.w("lightness","lightness"+lightness);
            SharedUtils.singleton().put("lightness",lightness);
            startBroadcastData = Arrays.copyOfRange(startBroadcastData, 2, startBroadcastData.length);
            if(startBroadcastData.length==0){
                return;
            }
            ConstValuesHttps.MESSAGE_ALL_TOTAL_ZJ.clear();
            for(int i = 0;i<startBroadcastData.length;i++){
                if(!ConstValuesHttps.MESSAGE_ALL_TOTAL_ZJ.contains(startBroadcastData[i])) {
                    ConstValuesHttps.MESSAGE_ALL_TOTAL_ZJ.add(startBroadcastData[i]);
                    if(tv_sbNum_zj!=null && isShowCurrentActivity){
                        tv_sbNum_zj.setText("(主机已连接"+ConstValuesHttps.MESSAGE_ALL_TOTAL_ZJ.size()+"个球)");
                    }
                }
            }
            if(dialog!=null && dialog.isShowing() && btn_xunqiu.getText().toString().equals("正在寻球")){
                if(sortNumSet!=null){
                    ClientTcpUtils.mClientTcpUtils.sendData_B3(startBroadcastData, sortNumSet,
                            new ClientTcpUtils.SendDataOkInterface() {
                        @Override
                        public void sendDataOk(byte msg) {
                            ((Activity)mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(sbNum <= ConstValuesHttps.MESSAGE_ALL_TOTAL.size() && dialog.isShowing()){
                                        btn_xunqiu.setText("完成寻球");
                                    }
                                    tv_sbNum.setText(ConstValuesHttps.MESSAGE_ALL_TOTAL.size()+"/"+sbNum);
                                    mSvN.setCurrentCount(sbNum,ConstValuesHttps.MESSAGE_ALL_TOTAL.size(),tv_bfb);
                                }
                            });
                        }
                    });
                }
            }
        }
        if(mStartBroadcastType== ConstValuesHttps.MESSAGE_GET_C5
                || mStartBroadcastType== ConstValuesHttps.MESSAGE_GET_C6){
            if(startBroadcastData==null){
                return;
            }
            int to = startBroadcastData.length;
            for(int i = 0;i<startBroadcastData.length;i++){
                if(startBroadcastData[i] == -1){
                    to = i+1;
                }
            }
            startBroadcastData = Arrays.copyOfRange(startBroadcastData, 0,to);
            if(startBroadcastData.length==0){
                return;
            }
            Intent mIntent = new Intent("com.jxxx.tiyu_app.view.fragment");
            mIntent.putExtra(START_BROADCAST_TYPE, mStartBroadcastType);
            mIntent.putExtra(START_BROADCAST_DATA, startBroadcastData);
            MainApplication.getContext().sendBroadcast(mIntent);
        }
    }


    Dialog dialog;
    StepArcView_n mSvN;
    TextView tv_bfb,tv_sbNum,tv_sbNum_zj,tv_title;
    Button btn_xunqiu;
    String[] sortNumSet = null;
    private void showDialogXunQiu(Context mContext,int sbNum,boolean isShowQuXiao,boolean isDengdai) {
        dialog = new Dialog(mContext, R.style.selectorDialog);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_kaishixunqiu, null);
        btn_xunqiu = view.findViewById(R.id.btn_xunqiu);
        ImageView iv_quxiao =view.findViewById(R.id.iv_quxiao);
        tv_title =  view.findViewById(R.id.tv_title);
        tv_bfb =  view.findViewById(R.id.tv_bfb);
        tv_sbNum =  view.findViewById(R.id.tv_sbNum);
        tv_sbNum_zj =  view.findViewById(R.id.tv_sbNum_zj);
        mSvN = view.findViewById(R.id.sv_n);
        btn_xunqiu.setText("开始寻球");
        tv_sbNum.setText("0/"+sbNum);
        tv_sbNum_zj.setText("(主机已连接"+0+"个球)");
        if(isDengdai){
            tv_title.setText("已开启监听\n等待设备连接");
            btn_xunqiu.setText("正在连接");
        }
        mSvN.setCurrentCount(sbNum,0,tv_bfb);
        btn_xunqiu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(btn_xunqiu.getText().toString().equals("正在寻球")){
                    return;
                }
                if(btn_xunqiu.getText().toString().equals("开始寻球")){
                    btn_xunqiu.setText("正在寻球");
                }
                if(btn_xunqiu.getText().toString().equals("完成寻球")){
                    dialog.dismiss();
                    isShowCurrentActivity = false;
                      //不使用灯光模式
                    showLoading(mContext);
                    ClientTcpUtils.mClientTcpUtils.sendData_B2((byte) dengGuang, new ClientTcpUtils.SendDataOkInterface() {
                        @Override
                        public void sendDataOk(byte msg) {
                            ((Activity)mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    hideLoading();
                                    if(mWifiMessageReceiverInter==null){
                                        mContext.startActivity(new Intent(mContext, HomeTwoXueShengActivity.class));
                                    }else{
                                        mWifiMessageReceiverInter.messageReceiverInter();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
        if(!isShowQuXiao){
            iv_quxiao.setVisibility(View.GONE);
        }
        iv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showLoading(mContext);
                ClientTcpUtils.mClientTcpUtils.sendData_B3_add00(true, false,
                        new ClientTcpUtils.SendDataOkInterface() {
                    @Override
                    public void sendDataOk(byte msg) {
                        ((Activity)mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hideLoading();
                            }
                        });
                    }
                });
            }
        });
        dialog.setCancelable(false);
        dialog.setContentView(view);
        try {
            dialog.show();
        }catch (Exception e){

        }
    }

    private LoadingDialog mLoading;
    public void showLoading(Context mContext) {
        if (mLoading != null && !mLoading.isShowing()) {
            mLoading.show();
        } else {
            mLoading = LoadingDialog.show(mContext, R.string.loading_text, false, null);
        }
    }



    public void hideLoading() {
        if (mLoading != null && mLoading.isShowing()) {
            mLoading.dismiss();
        }
    }


    public static String START_BROADCAST_ACTION_START = "start_broadcast_action_start";
    public static String START_BROADCAST_TYPE = "start_broadcast_type";
    public static String START_BROADCAST_DATA = "start_broadcast_data";
    public static byte START_BROADCAST_TYPE_CLOSE = (byte) 0XFF;//断开
    public static byte START_BROADCAST_TYPE_CONNECT =(byte) 0X00;//连接成功
    public static byte START_BROADCAST_TYPE_CONNECT_JT = -2;//开始监听成功
    /**
     * @param type ：0XFF（断开） 0X00（连接成功）
     * @param data : 数据
     */
    public static void startBroadcast(byte type,byte[] data){
        Intent intent = new Intent(START_BROADCAST_ACTION_START);
        //发送广播的数据
        intent.putExtra(START_BROADCAST_TYPE, type);
        if(data!=null){
            intent.putExtra(START_BROADCAST_DATA, data);
        }
        MainApplication.getContext().sendBroadcast(intent);
    }

}
