package com.jxxx.tiyu_app.tcp_tester;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jxxx.tiyu_app.R;

/**
 * Create By HauyuChen
 * Email：Hauyu.Chen@gmail.com
 */

/* 服务端界面代码 */
public class ServerActivity extends Activity {
    private final String TAG="ServerActivity";
    private boolean isConnected = false;
    private ServerSocket serverSocket=null;
    private static Socket[] client = null;
    private final int  MAXSIZE = 100; //设置最大连接数
    private Integer client_index = 0;
    private InputStream inputStream=null;
    private boolean thread_flag=true;
    private boolean thread_read_flag=true;
    OutputStream writer = null;

    private EditText edit_listenport = null;
    private Button btn_connect = null;
    private EditText edit_recv = null;
    private EditText edit_send = null;
    private Button btn_send = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        /* 初始化 */
        initControls();
        /*发送按钮*/
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 发送按钮处理函数 */
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0;i<client.length;i++){
                            try {
                                writer = client[i].getOutputStream();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String str = edit_send.getText().toString();
                            send(str);
                        }
                    }
                }).start();
            }
        });
        //监听按钮
        btn_connect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /* 监听按钮处理函数 */
                listen();
            }
        });
    }
/*------------------------------------------------------------------------------------*/
    /* 初始化 */
    private void initControls(){
        edit_listenport = (EditText)findViewById(R.id.edit_listenport);
        btn_connect = (Button)findViewById(R.id.btn_listen);
        btn_send = (Button)findViewById(R.id.btn_send);
        edit_send = (EditText)findViewById(R.id.edit_msgsend);
        edit_recv = (EditText)findViewById(R.id.edit_recv);

        edit_recv.setMovementMethod(ScrollingMovementMethod.getInstance());
        client = new Socket[MAXSIZE];
        client_index = new Integer(0);
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
                        runOnUiThread(new Runnable() {
                            public void run() {
                                /* 更新UI */
                                edit_recv.setText(Arrays.toString(Arrays.copyOf(data,readBytes)));
                            }
                        });
                    }
                }
            } catch (Exception e) {
                Log.d(TAG, "ReadThread:while (thread_flag)" + e.getMessage());
                Log.d(TAG,"from: "+client[index].getRemoteSocketAddress().toString());
                thread_read_flag = false;

            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.d(TAG, "ReadThread: " + e.getMessage());
                }
            }
        }
    }

   /* 线程SocketServerThread：监听端口 */
    public class SocketServerThread implements Runnable {
        public void run() {
            Log.d(TAG, "SocketServerThread");
            try {
                while (thread_flag) {
                    client_index = client_index % MAXSIZE;
                    Log.d(TAG, "Server opened! on" + edit_listenport.getText().toString());
                    /* accept（）阻塞，一直监听端口，判断是否与连接 */
                    client[client_index] = serverSocket.accept();
                    /* 输出流 */
                    writer = client[client_index].getOutputStream();
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

   /* 监听按钮处理函数：开始监听端口 */
    public void listen() {
        if(false == isConnected){
            try {
                /* 监听端口 */
                serverSocket = new ServerSocket(Integer.parseInt(edit_listenport.getText().toString())) ;
                Toast.makeText(ServerActivity.this,"监听成功：）",Toast.LENGTH_SHORT).show();
                isConnected = true;
                /* 开启线程，等待连接 */
                thread_flag = true;
                new Thread(new SocketServerThread()).start();
                /* 更新UI */
                btn_connect.setText("停止监听");
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

    /* 发送按钮处理函数：向输出流写数据 */
    public void send(String str) {
        try {
            /*  向输出流写数据 */
            if(writer!=null){
                writer.write(str.getBytes());
                writer.flush();
            }
            /* 更新UI */
//            edit_send.setText("");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
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
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.d(TAG,"onDestroy"+e.getMessage());
        }
        /* 更新UI*/
        btn_connect.setText("开始监听");
        edit_recv.setText("");
        super.onDestroy();
    }
}

