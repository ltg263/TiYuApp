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
            connect(false);
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
     * 全段配置指令 单个
     * 第一字节   表示从设备地址 表示从设备地址，从01~1A共26个可设置地址，可取其一也可以取多个甚至全部。
     * 第二字节	颜色设置  表示颜色的设置，从0X00~0X07，分别代表7种颜色，其中0X00表示灭灯状态，0X01表示红色，0X02表示蓝色，0X03表示绿色，0X04表示紫色，0X05表示黄色，0X06表示青色，0X07表示白色。
     * 第三字节	频闪设置  表示闪烁的频率，设置值为0X00~0X32，对应十进制0~50，表示0~5秒的频闪间隔。
     * 第四字节	时长设置    设置灯亮的时长，设置值为0X00~0X3C，对应十进制0~60，表示0~60秒的时长，其中若设置值为0X3D，即对应十进制61，则为常亮模式。倒计时结束后发送状态返回指令（新增）。
     * 第五字节	触发方式   表示触发的方式，其中0X00代表不触发，0X01代表按压触发，0X02代表振动触发，0x03代表按压触发并且返回状态指令，0x04代表振动触发并且返回状态指令（新增）。
     * 第六字节	触发后的执行动作 表示触发后的状态，0X01表示触发后切换当前亮与灭状态，0X02表示触发后亮灯，随后等待2秒灯灭，0x03表示触发后保持亮灯状态，0x04表示触发后灭灯（新增）。
     */
    public void sendData_A0(byte data_1,byte data_2,byte data_3,byte data_4,byte data_5,byte data_6){
        byte[] a0_data = {data_1,data_2,data_3,data_4,data_5,data_6};
        sendData(ConstValuesHttps.MESSAGE_SEND_A0,a0_data);
    }

    public void sendData_A0_dg(List<Byte> sendDatas){
        byte[] a0_data = new byte[sendDatas.size()];
        for(int i=0;i<sendDatas.size();i++){
            a0_data[i] = sendDatas.get(i);
        }
        sendData(ConstValuesHttps.MESSAGE_SEND_A0,a0_data);
    }

    /**
     * 全段配置指令
     * 第一字节	表示从设备地址 表示从设备地址，从01~1A共26个可设置地址，可取其一也可以取多个甚至全部。
     * 第二字节	颜色设置    表示颜色的设置，从0X00~0X07，分别代表7种颜色，其中0X00表示灭灯状态，0X01表示红色，0X02表示蓝色，0X03表示绿色，0X04表示紫色，0X05表示黄色，0X06表示青色，0X07表示白色。
     * 第三字节	周期设置    表示从亮到灭的这个过程的时长，设置值为0x01~0x03，代表1秒到3秒。
     * 第四字节	间隔设置    从完全灭掉至下一次开始亮起的时间间隔，设置值为0x00~0x05，代表0~5秒。
     * 第五字节	循环次数    整个周期+间隔的循环次数。其中循环结束后发送返回状态指令。
     * 第六字节	NC      未定义。
     *
     */
    public void sendData_A1(byte data_1,byte data_2,byte data_3,byte data_4,byte data_5){
        byte[] a1_data = {data_1,data_2,data_3,data_4,data_5,0};
        sendData(ConstValuesHttps.MESSAGE_SEND_A1,a1_data);
    }

    /**
     * 一键启动
     */
    public void sendData_B0(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                byte[] data = new byte[ConstValuesHttps.MESSAGE_NUM_TOTAL];
                for(int i=0;i<data.length;i++){
                    data[i] = (byte) (i+1);
                }
                sendData(ConstValuesHttps.MESSAGE_SEND_B0,ConstValuesHttps.getByteDataB0OrB1(data));
            }
        }).start();
    }

    /**
     * 一键关机
     */
    public void sendData_B1(){
        byte[] data = new byte[ConstValuesHttps.MESSAGE_NUM_TOTAL];
        for(int i=0;i<data.length;i++){
            data[i] = (byte) (i+1);
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
