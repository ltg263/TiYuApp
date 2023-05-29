package com.jxxx.tiyu_app.tcp_tester;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.jxxx.tiyu_app.utils.WifiMessageReceiver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientTcpUtils_1 {

    private Context mContext;
    private Socket socket = null;
    private OutputStream writer = null;
    private InputStream reader = null;
    private String TAG = "ClientTcpUtils";

    public static ClientTcpUtils_1 mClientTcpUtils;
    public ClientTcpUtils_1(Context mContext) {
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
                        mErrorDialogInterfac.btnConfirm("连接成功",null);
                    }
                    WifiMessageReceiver.startBroadcast(WifiMessageReceiver.START_BROADCAST_TYPE_CONNECT,null);
                    break;
                case 1://数据
                    byte[] v = (byte[]) msg.obj;
                    System.out.println("接收到的总数据长度：" +v.length);
                    System.out.println("接收到的总数据(10):" + Arrays.toString(v));
                    System.out.println("接收到的总数据(16):" + BinaryToHexString(v));
                    System.out.println("原始数据接受(10)-->>"+Arrays.toString(v));
                    System.out.println("原始数据接受(16)-->>"+BinaryToHexString(v));
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

                sendData(msg,mData);
            }
        }).start();
    }

    /**
     * 全段配置指令 单个A0 和 A1呼吸灯
     */
    public void sendData_A0_A1_sj(byte msg, byte[] mData){
        sendData(msg,mData);
    }

    /**
     * 一键关机
     */
    public void sendData_B1(){
        byte[] data = new byte[ConstValuesHttps.MESSAGE_ALL_TOTAL_ZJ.size()];
        for(int i=0;i<ConstValuesHttps.MESSAGE_ALL_TOTAL_ZJ.size();i++){
            data[i] = ConstValuesHttps.MESSAGE_ALL_TOTAL_ZJ.get(i);
        }
        for (int i=0;i<data.length;i++){
            byte[] data_new = new byte[]{data[i], 0,0,0,0,0};
            sendData(ConstValuesHttps.MESSAGE_SEND_B1,data_new);
        }
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

    /**
     * 设置显示的球号 00
     */
    public void sendData_B3_add00(){
        byte[] data = new byte[ConstValuesHttps.MESSAGE_ALL_TOTAL_ZJ.size()];
        for(int i=0;i<ConstValuesHttps.MESSAGE_ALL_TOTAL_ZJ.size();i++){
            data[i] = ConstValuesHttps.MESSAGE_ALL_TOTAL_ZJ.get(i);
        }

        for (int i=0;i<data.length;i++){
            byte[] data_new = new byte[]{data[i], 0,0,0,0,0};
            sendData(ConstValuesHttps.MESSAGE_SEND_B3,data_new);
        }
    }

    /**
     * 设置显示的球号
     * @param ads:地址
     * @param new_ads:新的地址
     */
    public void sendData_B3(byte ads,byte new_ads){
        ConstValuesHttps.MESSAGE_ALL_TOTAL_MAP.put(new_ads,ads);
        byte[] data = new byte[]{ads, new_ads,0,0,0,0};
        sendData(ConstValuesHttps.MESSAGE_SEND_B3,data);

//        byte[] data_di = new byte[]{ads, 0X07,0,0X3D,0x01,0X03};
//        sendData(ConstValuesHttps.MESSAGE_SEND_A0,data_di);
    }

    public void sendData(byte msg, byte[] data){
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized(this){
                    byte[] mData = ConstValuesHttps.getByteData(msg,data);
//                    System.out.println("发送的数据-->>(10)"+Integer.toHexString(mData[6] & 0xFF)+":" + Arrays.toString(mData));
                    System.out.println("发送的数据-->>(16)"+Integer.toHexString(mData[6] & 0xFF)+":" + BinaryToHexString(mData));
                    try {
                        writer.write(mData);
                        writer.flush();
                    } catch (IOException e) {
                        System.out.println("发送的数据-->>e"+e);
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    /**
     * 一键启动
     * @param ads:地址
     *
     */
    public void sendData_B0(byte ads){
        byte[] data = new byte[]{ads, 0,0,0,0,0};
        sendData(ConstValuesHttps.MESSAGE_SEND_B0,data);
    }


    //测试用到的-------------------------------------------------
    ErrorDialogInterface mErrorDialogInterfac;
    public ClientTcpUtils_1(Context mContext, ErrorDialogInterface mErrorDialogInterface) {
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
}
