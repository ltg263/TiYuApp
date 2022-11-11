package com.jxxx.tiyu_app.tcp_tester;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

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
            serverSocket.close();
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
    public void sendData_B3_add00(boolean isSocketClose,boolean isShutdown){
        if(isShutdown){//是关机 ---直接挂机不用制0
            ClientTcpUtils.mClientTcpUtils.sendData_B1();
        }else{
            byte[] data = new byte[ConstValuesHttps.MESSAGE_ALL_TOTAL_ZJ.size()];
            for(int i=0;i<ConstValuesHttps.MESSAGE_ALL_TOTAL_ZJ.size();i++){
                data[i] = ConstValuesHttps.MESSAGE_ALL_TOTAL_ZJ.get(i);
            }
            for (int i=0;i<data.length;i++){
                byte[] data_new = new byte[]{data[i], 0,0,0,0,0};
                sendData(ConstValuesHttps.MESSAGE_SEND_B3,data_new);
            }
        }

        if(isSocketClose){
            onDestroy();
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
