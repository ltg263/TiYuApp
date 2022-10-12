package com.jxxx.tiyu_app.tcp_tester;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.jxxx.tiyu_app.app.MainApplication;
import com.jxxx.tiyu_app.utils.SharedUtils;
import com.jxxx.tiyu_app.utils.WifiMessageReceiver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.http.Body;

public class ClientTcpUtils {

    private Context mContext;
    private Socket socket = null;
    private OutputStream writer = null;
    private InputStream reader = null;
    private String TAG = "ClientTcpUtils";

    public static ClientTcpUtils mClientTcpUtils;
    public ClientTcpUtils(Context mContext) {
        this.mContext = mContext;
        connect(true);
    }
    /* 连接按钮处理函数：建立Socket连接 */
    @SuppressLint("HandlerLeak")
    public void connect(boolean biaoji) {
        Log.w("-----","000"+socket);
        if(socket==null || !socket.isConnected()){
            new Thread() {
                public void run(){
                    try {
                        if(!biaoji){
                            Thread.sleep(10000);
                        }
                        /* 建立socket */
                        socket = new Socket(ConstValuesHttps.IPAdr, ConstValuesHttps.PORT);
                        /* 调试输出 */
                        Log.i(TAG, "输入输出流获取成功");
                        reader = socket.getInputStream();
                        writer = socket.getOutputStream();
                        if(socket.isConnected()){
                            Message msg_0 = handler.obtainMessage();
                            msg_0.what = 0;
                            handler.sendMessage(msg_0);
                            Log.i(TAG, "连接成功");
                        }else {
                            Log.i(TAG, "连接失败");
                        }
                        /* 读数据并更新UI */
                        byte[] buffer = new byte[1024];
                        while (socket!=null && socket.isConnected()) {
                            /* 输入流 */
                            int len = reader.read(buffer);
                            if (len > 0) {
                                Message msg_1 = handler.obtainMessage();
                                msg_1.what = 1;
                                msg_1.obj = Arrays.copyOf(buffer,len);
                                handler.sendMessage(msg_1);
                            }
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        Message msg_1_ = handler.obtainMessage();
                        msg_1_.what = -1;
                        handler.sendMessage(msg_1_);
                    }
                }
            }.start();
        }else{
            /* 关闭socket */
            onDestroy();
        }
    }

    /* 定义Handler对象 */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        /* 当有消息发送出来的时候就执行Handler的这个方法 */
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            /* 更新UI */
            switch (msg.what){
                case -1://断开
                    onDestroy();
                    break;
                case 0://链接成功
                    if(mErrorDialogInterfac!=null){
                        mErrorDialogInterfac.btnConfirm("链接成功",null);
                    }
                    WifiMessageReceiver.startBroadcast(WifiMessageReceiver.START_BROADCAST_TYPE_CONNECT,null);
                    break;
                case 1://数据
                    byte[] v = (byte[]) msg.obj;
                    System.out.println("接收到的总数据长度：" +v.length);
                    System.out.println("接收到的总数据(10):" + Arrays.toString(v));
                    System.out.println("接收到的总数据(16):" + BinaryToHexString(v));
                    if(mErrorDialogInterfac!=null){
                        mErrorDialogInterfac.btnConfirm("接收数据",v);
                    }
                    if(v[0]==ConstValuesHttps.MESSAGE_START){
                        if(v[1] == ConstValuesHttps.MESSAGE_GET_C0){
                            byte[] data_c0 = Arrays.copyOfRange(v, 2, v.length-1);
                            WifiMessageReceiver.startBroadcast(ConstValuesHttps.MESSAGE_GET_C0,data_c0);
                        }
                        if(v[1] == ConstValuesHttps.MESSAGE_GET_C5){
                            byte[] data_c5 = Arrays.copyOfRange(v, 2, v.length-1);
                            WifiMessageReceiver.startBroadcast(ConstValuesHttps.MESSAGE_GET_C5,data_c5);
                        }
                    }else{
                        System.out.println("接收到的总数据:错误数据");
                    }
                    break;
            }
            /* 调试输出 */
        }
    };

    public static String BinaryToHexString(byte[] bytes) {

        String hexStr = "0123456789ABCDEF";

        String result = "";

        String hex = "";

        for (byte b : bytes) {

            hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));

            hex += String.valueOf(hexStr.charAt(b & 0x0F));

            result += hex + " ";

        }

        return result;

    }
    public void onDestroy() {
        try {
            /* 关闭socket */
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (socket != null) {
                socket.close();
                socket = null;
            }
//            connect(false);
        } catch (IOException e) {
            Log.d(TAG,e.getMessage());
        }
        /* 更新UI */
        WifiMessageReceiver.startBroadcast(WifiMessageReceiver.START_BROADCAST_TYPE_CLOSE,null);
        if(mErrorDialogInterfac!=null){
            mErrorDialogInterfac.btnConfirm("断开连接",null);
        }
    }
    /**
     * 全段配置指令 多个A0 和 A1呼吸灯
     */
    public void sendData_A0_A1_dg(byte msg,List<Byte> sendDatas){
        byte[] a0_data = new byte[sendDatas.size()];
        for(int i=0;i<sendDatas.size();i++){
            if(sendDatas.get(i)==null){
                a0_data[i] = 0;
            }else{
                a0_data[i] = sendDatas.get(i);
            }
        }
        sendData(msg,a0_data);
        sendData_B0();
    }
    /**
     * 全段配置指令 单个A0 和 A1呼吸灯
     */
    public void sendData_A0_A1(byte msg,List<Byte> sendys){
        List<Byte> sendDatas = new ArrayList<>(sendys);
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendDatas.remove(0);
                byte[] mData = new byte[sendDatas.size()];
                for(int i=0;i<sendDatas.size();i++){
                    if(sendDatas.get(i)==null){
                        mData[i] = 0;
                    }else{
                        mData[i] = sendDatas.get(i);
                    }
                }
                sendData_A0_A1_syn(msg,mData);
            }
        }).start();
    }

    /**
     * 全段配置指令 单个A0 和 A1呼吸灯
     */
    public void sendData_A0_A1_sj(byte msg, byte[] mData){
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendData_A0_A1_syn(msg,mData);
            }
        }).start();
    }

    private synchronized void sendData_A0_A1_syn(byte msg, byte[] mData) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendData(msg,mData);
        sendData_B0();
    }


    /**
     * 一键启动
     */
    public void sendData_B0(){
        new Thread(new Runnable() {
            @Override
            public void run() {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                byte[] data = new byte[ConstValuesHttps.MESSAGE_ALL_TOTAL.size()];
                for(int i=0;i<ConstValuesHttps.MESSAGE_ALL_TOTAL.size();i++){
                    data[i] = ConstValuesHttps.MESSAGE_ALL_TOTAL.get(i);
                }
                sendData(ConstValuesHttps.MESSAGE_SEND_B0,ConstValuesHttps.getByteDataB0OrB1(data));
            }
        }).start();
    }

    /**
     * 一键关机
     */
    public void sendData_B1(){
//        byte[] data = new byte[ConstValuesHttps.MESSAGE_NUM_TOTAL];
//        for(int i=0;i<data.length;i++){
//            data[i] = (byte) (i+1);
//        }
        byte[] data = new byte[ConstValuesHttps.MESSAGE_ALL_TOTAL.size()];
        for(int i=0;i<ConstValuesHttps.MESSAGE_ALL_TOTAL.size();i++){
            data[i] = ConstValuesHttps.MESSAGE_ALL_TOTAL.get(i);
        }
        sendData(ConstValuesHttps.MESSAGE_SEND_B1,ConstValuesHttps.getByteDataB0OrB1(data));
    }
    /**
     * 网关数据上报
     * @param data
     */
    public void sendData_C0(byte[] data){
        sendData(ConstValuesHttps.MESSAGE_GET_C0,data);
    }

    /**
     * 设置亮度
     * @param ads:地址
     * @param dg:灯光 （可设置为室内00以及室外01两种）
     */
    public void sendData_B2(byte ads,byte dg){
        byte[] data = new byte[]{ads, dg,0,0,0,0};
        sendData(ConstValuesHttps.MESSAGE_SEND_B2,data);
    }

    public void sendData_B2_dg(List<Byte> sendDatas){
        byte[] b2_data = new byte[sendDatas.size()];
        for(int i=0;i<sendDatas.size();i++){
            b2_data[i] = sendDatas.get(i);
        }
        sendData(ConstValuesHttps.MESSAGE_SEND_B2,b2_data);
    }

    /**
     * 设置显示的球号
     * @param ads:地址
     * @param new_ads:新的地址
     */
    public void sendData_B3(byte ads,byte new_ads){
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendData_B3Syn(ads,new_ads);
            }
        }).start();
    }

    private synchronized void sendData_B3Syn(byte ads,byte new_ads) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ConstValuesHttps.MESSAGE_ALL_TOTAL_MAP.put(new_ads,ads);
        byte[] data = new byte[]{ads, new_ads,0,0,0,0};
        sendData(ConstValuesHttps.MESSAGE_SEND_B3,data);
        sendData_B0();
    }

    public void sendData(byte msg, byte[] data){
        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] mData = ConstValuesHttps.getByteData(msg,data);

                System.out.println("发送的数据-->>"+Integer.toHexString(mData[1] & 0xFF)+":" + Arrays.toString(mData));
                try {
                    writer.write(mData);
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    //测试用到的-------------------------------------------------
    ErrorDialogInterface mErrorDialogInterfac;
    public ClientTcpUtils(Context mContext,ErrorDialogInterface mErrorDialogInterface) {
        this.mContext = mContext;
        this.mErrorDialogInterfac = mErrorDialogInterface;
        connect(true);
    }


    public interface ErrorDialogInterface {
        /**
         * 确定
         */
        public void btnConfirm(String type,byte[] v);
    }


    public void sendData_cs(byte[] mData){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    writer.write(mData);
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
