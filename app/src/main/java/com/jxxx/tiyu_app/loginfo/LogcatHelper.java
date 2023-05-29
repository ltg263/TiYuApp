package com.jxxx.tiyu_app.loginfo;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.jxxx.tiyu_app.app.ConstValues;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogcatHelper {
    public static final String MESSAGE_LOG = "AndroidRuntime";
    public static String LOGCAT_FILE_NAME = "";
    private static LogcatHelper INSTANCE = null;
    private LogDumper mLogDumper = null;
    private int mPId;
    /**
     * 初始化目录
     */
    public void init(Context context) {
        File file = new File(ConstValues.PATH_LOGCAT);
        if(file.exists()){
            deleteFile(file);
        }
        if (!file.exists()) {
            file.mkdir();
        }
        Log.i("codedzh","存储位置："+ConstValues.PATH_LOGCAT);
        Log.i("codedzh","文件是否存在："+new File(ConstValues.PATH_LOGCAT).exists());
    }
    /**
     * 删除文件夹所有内容
     *
     */
    public boolean deleteFile(File file) {
        System.out.println("file is==>" + file);
        boolean isSuccess = false;
        if (file.exists()) { //判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                System.out.println("is file");
                if(!file.toString().contains(getFileNameCr())){
                    System.out.println("is delete1"+file);
                    file.delete(); // delete()方法 你应该知道 是删除的意思;
                }
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                System.out.println("is dic");
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                if(files!=null){
                    for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                        this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                    }
                }
            }
            isSuccess = true;
        }
        return isSuccess;
    }
    public static LogcatHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LogcatHelper(context);
        }
        return INSTANCE;
    }
    private LogcatHelper(Context context) {
        init(context);
        mPId = android.os.Process.myPid();
    }
    public void start() {
        if (mLogDumper == null)
            mLogDumper = new LogDumper(String.valueOf(mPId), ConstValues.PATH_LOGCAT);
        mLogDumper.start();
    }

    public void stop() {
        if (mLogDumper != null) {
            mLogDumper.stopLogs();
            mLogDumper = null;
        }
    }

    private class LogDumper extends Thread {
        private Process logcatProc;
        private BufferedReader mReader = null;
        private boolean mRunning = true;
        String cmds = null;
        private String mPID;
        private FileOutputStream out = null;
        public LogDumper(String pid, String dir) {
            mPID = pid;
            try {
                File mFile = new File(dir, "log-"+ getFileName() + ".log");
                out = new FileOutputStream(mFile);
                Log.w(LogcatHelper.MESSAGE_LOG,"生成LOG文件："+mFile.getPath());
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            /**
             *
             * 日志等级：*:v , *:d , *:w , *:e , *:f , *:s
             *
             * 显示当前mPID程序的 E和W等级的日志.
             *
             * */
//             cmds = "logcat *:e *:w | grep \"(" + mPID + ")\"";
//             cmds = "logcat  | grep \"(" + mPID + ")\"";//打印所有日志信息
            cmds = "logcat -s "+MESSAGE_LOG;//打印标签过滤信息
//             cmds = "logcat *:e *:v | grep \"(" + mPID + ")\"";

        }
        public void stopLogs() {
            mRunning = false;
        }
        @Override
        public void run() {
            try {
                logcatProc = Runtime.getRuntime().exec(cmds);
                mReader = new BufferedReader(new InputStreamReader(
                        logcatProc.getInputStream()), 1024);
                String line = null;
                while (mRunning && (line = mReader.readLine()) != null) {
                    if (!mRunning) {
                        break;
                    }
                    if (line.length() == 0) {
                        continue;
                    }
                    if (out != null && line.contains(mPID)) {
                        out.write((line + "\n").getBytes());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (logcatProc != null) {
                    logcatProc.destroy();
                    logcatProc = null;
                }
                if (mReader != null) {
                    try {
                        mReader.close();
                        mReader = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out = null;
                }
            }
        }
    }
    public String getFileName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = format.format(new Date(System.currentTimeMillis()));
        LogcatHelper.LOGCAT_FILE_NAME = date;
        return date;
    }
    public String getFileNameCr() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date(System.currentTimeMillis()));
        return date;
    }
}
