package com.jxxx.tiyu_app.tcp_tester;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.jxxx.tiyu_app.loginfo.LogcatHelper;
import com.jxxx.tiyu_app.utils.WifiMessageReceiver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientTcpUtils {

    private Context mContext;
    private String TAG = "ClientTcpUtils";
    private final int  MAXSIZE = 100; //设置最大连接数
    private static Socket[] client = null;
    private boolean isConnected = false;
    private ServerSocket serverSocket=null;
    private boolean thread_flag=true;
    private boolean thread_read_flag=true;
    private Integer client_index = 0;
    private InputStream inputStream=null;

    private OutputStream writer_ok = null;

    public static ClientTcpUtils mClientTcpUtils;
    public ClientTcpUtils(Context mContext) {
        this.mContext = mContext;
        client = new Socket[MAXSIZE];
        connect();
    }

    /* 监听按钮处理函数：开始监听端口 */
    public void connect() {
        if(!isConnected){
            try {
                /* 监听端口 */
                serverSocket = new ServerSocket(6090) ;
                Message msg = handler.obtainMessage();
                msg.what = -2;
                handler.sendMessage(msg);
                isConnected = true;
                /* 开启线程，等待连接 */
                thread_flag = true;
                new Thread(new SocketServerThread()).start();
                /* 更新UI */

            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                Log.d(TAG,"listen1:"+e.getMessage());
                isConnected = false;
                thread_flag = false;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.d(TAG,"listen2:"+e.getMessage());
                isConnected = false;
                thread_flag = false;
            }
        }else{
            onDestroy();
        }
    }
    /* 线程SocketServerThread：监听端口 */
    public class SocketServerThread implements Runnable {
        public void run() {
            Log.d(TAG, "SocketServerThread");
            boolean isClosed = true;
            try {
                while (thread_flag) {
                    client_index = client_index % MAXSIZE;
                    Log.d(TAG, "Server opened!");
                    /* accept（）阻塞，一直监听端口，判断是否与连接 */
                    client[client_index] = serverSocket.accept();
                    Log.d(TAG, "Server opened!accept:"+isClosed);
                    if(isClosed){
                        Message msg = handler.obtainMessage();
                        msg.what = 0;
                        handler.sendMessage(msg);
                        isClosed = false;
                    }
                    /* 输出流 */
                    writer_ok = client[client_index].getOutputStream();
                    /* 开启线程，接收数据 */
                    new Thread(new ReadThread(client_index)).start();
                    client_index ++ ;
                }
            }catch (Exception e) {
                Log.d(TAG, "SocketServerThread:"+e.getMessage());
                thread_flag = false;
                thread_read_flag = false;
            }
        }
    }

    /* 线程ReadThread：从客户端读取数据 */
    public class ReadThread implements Runnable {
        int index;
        public ReadThread(int index){
            thread_read_flag = true;
            this.index = index;
        }
        public void run() {
            byte[] data = new byte[1024];
            try {
                while (thread_read_flag) {
                    /* 输入流 */
                    inputStream = client[index].getInputStream();
                    int readBytes = inputStream.read(data);
                    /* 调试输出 */
                    Log.d(TAG, "index:"+index+" readBytes:" + readBytes + " data:"+ Arrays.toString(Arrays.copyOf(data,readBytes)));
                    Log.d(TAG,"from: "+client[index].getRemoteSocketAddress().toString());
                    if (readBytes > 0) {
                        Message msg = handler.obtainMessage();
                        msg.what = 1;
                        msg.obj = Arrays.copyOf(data,readBytes);
                        handler.sendMessage(msg);
                    }
                }
            } catch (Exception e) {
                Log.d(TAG, "ReadThread:while (thread_flag)" + e.getMessage());
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.d(TAG, "ReadThread: " + e.getMessage());
                }
            }
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
                case -2://监听成功
                    WifiMessageReceiver.startBroadcast(WifiMessageReceiver.START_BROADCAST_TYPE_CONNECT_JT,null);
                    break;
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
                    if(!thread_read_flag){
                        return;
                    }
                    System.out.println("接收到的总数据(10):" + Arrays.toString(v));
                    Log.w(LogcatHelper.MESSAGE_LOG ,"接收到的总数据:" + BinaryToHexString(v));
                    int a = 0;
                    for(int i = 0;i<v.length;i++){
                        if(v[i] == -1){
                            a = i;
                            break;
                        }
                    }
                    if(a!=v.length-1){
                        byte[] v1 = Arrays.copyOf(v,a+1);
                        byte[] v2 = Arrays.copyOfRange(v,a+1,v.length);
                        startBroadcast(v1);
                        startBroadcast(v2);
                        return;
                    }
                    startBroadcast(v);
                    break;
            }
            /* 调试输出 */
        }
    };

    private void startBroadcast(byte[] v) {
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
            if(v[1] == ConstValuesHttps.MESSAGE_GET_C6){
                byte[] data_c6 = Arrays.copyOfRange(v, 2, v.length-1);
                WifiMessageReceiver.startBroadcast(ConstValuesHttps.MESSAGE_GET_C6,data_c6);
            }
        }else{
            System.out.println("接收到的总数据:错误数据");
        }
    }

    public static String BinaryToHexString(byte[] bytes) {
        if(bytes==null){
            return "[空]";
        }
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
        // TODO Auto-generated method stub
        isConnected = false;
        thread_flag = false;
        thread_read_flag = false;
        try {
            /* 关闭socket */
            for(int i=0;i<MAXSIZE;i++){
                if(client!=null && client[client_index]!=null && client[client_index].isConnected()) {
                    client[client_index].shutdownInput();
                    client[client_index].shutdownOutput();
                    client[client_index].getInputStream().close();
                    client[client_index].getOutputStream().close();
                    client[client_index].close();
                }
            }
            /* 关闭serversocket*/
            if(serverSocket!=null){
                serverSocket.close();
            }
            Log.d(TAG,"onDestroy:断开连接");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.d(TAG,"onDestroy"+e.getMessage());
        }
        /* 更新UI*/
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
        if(mData[1]==-1){
            byte num = (byte) (Math.random()*(7+1));
            if(num==0){
                num = 2;
            }
            mData[1] = num;
        }
        sendData(msg,mData);
    }

    /**
     * 设置亮度
     * @param dg:灯光 （可设置为室内00以及室外01两种）
     */
    public void sendData_B2(byte dg,SendDataOkInterface mSendDataOkInterface){
//        byte[] data = new byte[ConstValuesHttps.MESSAGE_ALL_TOTAL.size()];
//        for(int i=0;i<ConstValuesHttps.MESSAGE_ALL_TOTAL.size();i++){
//            data[i] = ConstValuesHttps.MESSAGE_ALL_TOTAL.get(i);
//        }
        byte[] data = new byte[1];
        data[0] = ConstValuesHttps.MESSAGE_ALL_TOTAL.get(0);
        sendDataThreadB2(0,data,dg,mSendDataOkInterface);
    }
    private void sendDataThreadB2(int pos, byte[] data,byte dg,SendDataOkInterface mSendDataOkInterface) {
        if(pos > data.length-1){
            mSendDataOkInterface.sendDataOk(ConstValuesHttps.MESSAGE_SEND_B2);
            return;
        }
        byte[] data_new = new byte[]{data[pos], dg,0,0,0,0};
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(ConstValuesHttps.THREAD_SLEEP);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sendData(ConstValuesHttps.MESSAGE_SEND_B2, data_new);
                sendDataThreadB2(pos+1,data,dg,mSendDataOkInterface);
            }
        }).start();
    }


    /**
     * 设置显示的球号
     * @param allDate 设备反馈的数据
     * @param sortNumSet 队列中的球号
     */
    public void sendData_B3(byte[] allDate,String[] sortNumSet,SendDataOkInterface mSendDataOkInterface) {
        int new_ads_pos = ConstValuesHttps.MESSAGE_ALL_TOTAL.size();
        List<Byte> newData = new ArrayList<>();
        for(int i = 0;i<allDate.length;i++){
            if(!ConstValuesHttps.MESSAGE_ALL_TOTAL.contains(allDate[i])){
                if(sortNumSet.length-1>=ConstValuesHttps.MESSAGE_ALL_TOTAL.size()){
                    ConstValuesHttps.MESSAGE_ALL_TOTAL.add(allDate[i]);
                    newData.add(allDate[i]);
                }
            }
        }
        if(newData.size()>0){
            byte[] data = new byte[newData.size()];
            for(int i=0;i<newData.size();i++){
                data[i] = newData.get(i);
            }
            sendDataThreadB3(0,new_ads_pos,data,mSendDataOkInterface);
        }
    }

    private void sendDataThreadB3(int pos,int new_ads_pos, byte[] allDate,SendDataOkInterface mSendDataOkInterface) {
        if(pos > allDate.length-1){
            mSendDataOkInterface.sendDataOk(ConstValuesHttps.MESSAGE_SEND_B3);
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(ConstValuesHttps.THREAD_SLEEP);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                byte new_ads = (byte) (new_ads_pos + pos+1);
                byte[] data_new = new byte[]{allDate[pos], new_ads,0,0,0,0};
                ConstValuesHttps.MESSAGE_ALL_TOTAL_MAP.put(new_ads,allDate[pos]);
                sendData(ConstValuesHttps.MESSAGE_SEND_B3, data_new);
                sendDataThreadB3(pos+1,new_ads_pos,allDate,mSendDataOkInterface);
            }
        }).start();
    }

    /**
     * 发送完毕的回调
     */
    public interface SendDataOkInterface {
        /**
         * 确定
         */
        public void sendDataOk(byte msg);
    }
    public void sendData_B3_add00_all(SendDataOkInterface mSendDataOkInterface) {
        byte[] data = new byte[101];
        for(int i=0;i<101;i++){
            data[i] = (byte) i;
        }
        sendDataThreadB1orB3(0,data,ConstValuesHttps.MESSAGE_SEND_B3,false,mSendDataOkInterface);
    }
    /**
     * 设置显示的球号 00
     */
    public void sendData_B3_add00(boolean isSocketClose,boolean isShutdown,SendDataOkInterface mSendDataOkInterface){
        byte[] data = new byte[ConstValuesHttps.MESSAGE_ALL_TOTAL_ZJ.size()];
        for(int i=0;i<ConstValuesHttps.MESSAGE_ALL_TOTAL_ZJ.size();i++){
            data[i] = ConstValuesHttps.MESSAGE_ALL_TOTAL_ZJ.get(i);
        }
        byte msg = ConstValuesHttps.MESSAGE_SEND_B1;
        if(!isShutdown){//不是关机 ---直接挂机不用制0
            msg = ConstValuesHttps.MESSAGE_SEND_B3;
        }
        sendDataThreadB1orB3(0,data,msg,isSocketClose,mSendDataOkInterface);
    }

    private void sendDataThreadB1orB3(int pos, byte[] data,byte msg,boolean isSocketClose,SendDataOkInterface mSendDataOkInterface) {
        if(pos > data.length-1){
            if(isSocketClose){
                onDestroy();
            }
            mSendDataOkInterface.sendDataOk(msg);
            return;
        }
        byte[] data_new = new byte[]{data[pos], 0,0,0,0,0};
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(ConstValuesHttps.THREAD_SLEEP);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sendData(msg, data_new);
                sendDataThreadB1orB3(pos+1,data,msg, isSocketClose,mSendDataOkInterface);

            }
        }).start();
    }

    public void sendData(byte msg, byte[] data){
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized(this){
                    byte[] mData = ConstValuesHttps.getByteData(msg,data);
                     Log.w(LogcatHelper.MESSAGE_LOG ,"发送的数据-->>"+Integer.toHexString(mData[6] & 0xFF)+":" + BinaryToHexString(mData));

                    for(int i = 0;i<client.length;i++){
                        if(client[i]!=null){
                            try {
                                writer_ok = client[i].getOutputStream();
                                writer_ok.write(mData);
                                writer_ok.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
//                    if(msg!=ConstValuesHttps.MESSAGE_SEND_B0){
//                        sendData_B0(data[0]);
//                    }
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
    public ClientTcpUtils(Context mContext,ErrorDialogInterface mErrorDialogInterface) {
        this.mContext = mContext;
        this.mErrorDialogInterfac = mErrorDialogInterface;
        connect();
    }


    public interface ErrorDialogInterface {
        /**
         * 确定
         */
        public void btnConfirm(String type,byte[] v);
    }
}
