package com.jxxx.tiyu_app.utils;

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
import com.jxxx.tiyu_app.utils.view.StepArcView_n;
import com.jxxx.tiyu_app.view.activity.HomeTwoXueShengActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    }
    public WifiMessageReceiver(int sbNum,int dengGuang) {
        this.sbNum = sbNum;
        this.dengGuang = dengGuang;
        isShowCurrentActivity = true;
    }

    public void setSbNum(int sbNum) {
        this.sbNum = sbNum;
    }

    public void setDengGuang(int dengGuang) {
        this.dengGuang = dengGuang;
    }

    public void setWifiMessageReceiverInter(WifiMessageReceiverInter wifiMessageReceiverInter) {
        mWifiMessageReceiverInter = wifiMessageReceiverInter;
    }

    public void onWifiMessageReceiverInter (Context mContext, int sbNum, int dengGuang, WifiMessageReceiverInter mWifiMessageReceiverInter){
        isShowCurrentActivity = true;
        this.mWifiMessageReceiverInter = mWifiMessageReceiverInter;
        this.sbNum = sbNum;
        this.dengGuang = dengGuang;
        showDialogXunQiu(mContext,sbNum,false);
    }

    @Override
    public void onReceive(Context mContext, Intent intent) {
        this.mContext = mContext;
        byte mStartBroadcastType = intent.getByteExtra(WifiMessageReceiver.START_BROADCAST_TYPE, (byte) 0X00);
        byte[] startBroadcastData = intent.getByteArrayExtra(WifiMessageReceiver.START_BROADCAST_DATA);
        System.out.println("接收到的数据：" + Arrays.toString(startBroadcastData));
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
            Toast.makeText(mContext,"连接已断开",Toast.LENGTH_SHORT).show();
        }
        if(mStartBroadcastType == WifiMessageReceiver.START_BROADCAST_TYPE_CONNECT){
            System.out.println("连接成功");
            if(!isShowCurrentActivity){
                return;
            }
            showDialogXunQiu(mContext,sbNum,true);
            Toast.makeText(mContext,"连接成功",Toast.LENGTH_SHORT).show();
        }
        if(mStartBroadcastType== ConstValuesHttps.MESSAGE_GET_C0){
            if(!isShowCurrentActivity || startBroadcastData==null){
                return;
            }
            startBroadcastData = Arrays.copyOfRange(startBroadcastData, 2, startBroadcastData.length);
            if(dialog!=null && dialog.isShowing() && btn_xunqiu.getText().toString().equals("正在寻球")){
                for(int i = 0;i<startBroadcastData.length;i++){
                    Log.i("BroadcastReceiver", "MESSAGE_ALL_TOTAL: " + ConstValuesHttps.MESSAGE_ALL_TOTAL);
                    Log.i("BroadcastReceiver", "startBroadcastData: " + startBroadcastData[i]);
                    if(!ConstValuesHttps.MESSAGE_ALL_TOTAL.contains(startBroadcastData[i])){
                        ConstValuesHttps.MESSAGE_ALL_TOTAL.add(startBroadcastData[i]);
                        String[] sortNumSet = null;
                        if(ConstValues.mSchoolCourseInfoBean!=null
                                && ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList()!=null
                                && ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().size()>HomeTwoXueShengActivity.current_course_section
                                && StringUtil.isNotBlank(ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().get(HomeTwoXueShengActivity.current_course_section).getSmallCourseVo().getSortNumSet())){
                            sortNumSet = ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().get(HomeTwoXueShengActivity.current_course_section).getSmallCourseVo().getSortNumSet().split(",");
                        }else if(ConstValues.mSchoolCourseInfoBeanSmall != null
                                && StringUtil.isNotBlank(ConstValues.mSchoolCourseInfoBeanSmall.getSortNumSet())){
                            sortNumSet = ConstValues.mSchoolCourseInfoBeanSmall.getSortNumSet().split(",");
                        }
                        if(isSuiJi){
                            sortNumSet = new String[sbNum];
                            for(int s=0;s<sortNumSet.length;s++){
                                sortNumSet[s] = (s+1)+"";
                            }
                        }
                        if(sortNumSet != null && sortNumSet.length>=ConstValuesHttps.MESSAGE_ALL_TOTAL.size()){
                            Log.i("BroadcastReceiver", "sendData_B3: " + startBroadcastData[i]);
                            ClientTcpUtils.mClientTcpUtils.sendData_B3(startBroadcastData[i],
                                    Byte.parseByte(sortNumSet[ConstValuesHttps.MESSAGE_ALL_TOTAL.size()-1]));
                        }
                    }
                }
                if(sbNum <= ConstValuesHttps.MESSAGE_ALL_TOTAL.size() && dialog.isShowing()){
                    btn_xunqiu.setText("完成寻球");
                }
                mSvN.setCurrentCount(sbNum,ConstValuesHttps.MESSAGE_ALL_TOTAL.size(),tv_bfb);
            }
        }
        if(mStartBroadcastType== ConstValuesHttps.MESSAGE_GET_C5){
            if(startBroadcastData==null){
                return;
            }
            Intent mIntent = new Intent("com.jxxx.tiyu_app.view.fragment");
            mIntent.putExtra(START_BROADCAST_DATA, startBroadcastData);
            MainApplication.getContext().sendBroadcast(mIntent);
        }
    }


    Dialog dialog;
    StepArcView_n mSvN;
    TextView tv_bfb;
    Button btn_xunqiu;
    private void showDialogXunQiu(Context mContext,int sbNum,boolean isShowQuXiao) {
        dialog = new Dialog(mContext, R.style.selectorDialog);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_kaishixunqiu, null);
        btn_xunqiu = view.findViewById(R.id.btn_xunqiu);
        ImageView iv_quxiao =view.findViewById(R.id.iv_quxiao);
        TextView tv_title =  view.findViewById(R.id.tv_title);
        tv_bfb =  view.findViewById(R.id.tv_bfb);
        mSvN = view.findViewById(R.id.sv_n);
        btn_xunqiu.setText("开始寻球");
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
                    for(int i = 0;i<ConstValuesHttps.MESSAGE_ALL_TOTAL.size();i++){
                        ClientTcpUtils.mClientTcpUtils.sendData_B2(ConstValuesHttps.MESSAGE_ALL_TOTAL.get(i),(byte) dengGuang);
                    }
                    isShowCurrentActivity = false;
                    dialog.dismiss();
                    if(mWifiMessageReceiverInter==null){
                        mContext.startActivity(new Intent(mContext, HomeTwoXueShengActivity.class));
                    }else{
                        mWifiMessageReceiverInter.messageReceiverInter();
                    }
                }
            }
        });
        if(!isShowQuXiao){
            iv_quxiao.setVisibility(View.GONE);
        }
        iv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientTcpUtils.mClientTcpUtils.sendData_B3_add00();
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.setContentView(view);
        try {
            dialog.show();
        }catch (Exception e){

        }
    }


    public static String START_BROADCAST_ACTION_START = "start_broadcast_action_start";
    public static String START_BROADCAST_TYPE = "start_broadcast_type";
    public static String START_BROADCAST_DATA = "start_broadcast_data";
    public static byte START_BROADCAST_TYPE_CLOSE = (byte) 0XFF;//断开
    public static byte START_BROADCAST_TYPE_CONNECT =(byte) 0X00;//链接成功
    /**
     * @param type ：0XFF（断开） 0X00（链接成功）
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
