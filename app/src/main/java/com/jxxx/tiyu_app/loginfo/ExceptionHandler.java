package com.jxxx.tiyu_app.loginfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.jxxx.tiyu_app.app.MainApplication;

import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
 * 异常类
 */
public class ExceptionHandler implements UncaughtExceptionHandler {
    private static ExceptionHandler instance;
    private Thread.UncaughtExceptionHandler defaultHandler;
    private Context context;

    private ExceptionHandler() {
    }

    public static ExceptionHandler getInstance() {
        if (instance == null) {
            instance = new ExceptionHandler();
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, final Throwable ex) {
        Log.w("ExceptionHandler","ExceptionHandler:");
//        if (ex != null && defaultHandler != null) {
//            new Thread() {
//                @Override
//                public void run() {
//                    final Writer writer = new StringWriter();
//                    PrintWriter pw = new PrintWriter(writer);
//                    ex.printStackTrace(pw);
//                    pw.close();
//
////                    LogHelper.outputErrorLog(context.getClass(), writer.toString());//这里是其他崩溃日志保存到本地的方法
//
//                    //崩溃邮件发送
//                    File crashMailFile = new File(context.getFilesDir(), "crashMail.log");
//                    Log.w("ExceptionHandler","crashMailFile:"+crashMailFile.exists());
//                    CrashLogUtil.writeLog(crashMailFile, "CrashHandler", ex.getMessage(), ex);
//                    try {
//                        sendMail(crashMailFile);
//                    } catch (Exception e) {
//                        Log.w("ExceptionHandler","e:"+e);
//                        e.printStackTrace();
//                    }
//
////                    Looper.prepare();
////                    ((Activity)context).runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
////                            new AlertDialog.Builder(context).setTitle(context.getString(R.string.app_name))
////                                    .setCancelable(false)
////                                    .setMessage(context.getString(R.string.loading_text))
////                                    .setPositiveButton(context.getString(R.string.home_main_1), new DialogInterface.OnClickListener() {
////                                        @Override
////                                        public void onClick(DialogInterface dialog, int which) {
//////                                             ApplicationExt.getInstance().exit();
////                                        }
////                                    }).create().show();
////                        }
////                    });
////                    Looper.loop();
//                }
//            }.start();
//        }
    }

/***************************************************************************/
    private void sendMail(File crashMailFile) throws Exception {

        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();          // 参数配置
        props.setProperty("mail.smtp.host", "smtp.126.com");   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.transport.protocol", "smtp");  // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.auth", "true");      // 需要请求认证
        //如果遇到ssl类错误，请打开一下代码
//        final String smtpPort = "465";
//        props.setProperty("mail.smtp.port", smtpPort);
//        props.setProperty("mail.smtp.socketFactory.class", "javax.NET.ssl.SSLSocketFactory");
//        props.setProperty("mail.smtp.socketFactory.fallback", "false");
//        props.setProperty("mail.smtp.socketFactory.port", smtpPort);

        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getDefaultInstance(props);
        // 设置为debug模式, 可以查看详细的发送 log
        session.setDebug(true);
        // 3. 创建一封邮件
        MimeMessage message = MailService.createMimeMessage(session, "ltg263@126.com"
                , "564355785@qq.com", buildBody(context)
                , crashMailFile);//我这里是以163邮箱为发信邮箱测试通过
        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();
        transport.connect("ltg263@126.com", "FZDUSJRLHXFTSDCQ");
        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());
        // 7. 关闭连接
        transport.close();
    }

    public String buildBody(Context context) {
        StringBuilder sb = new StringBuilder();

        sb.append("APPLICATION INFORMATION").append("\r\n");
        PackageManager pm = context.getPackageManager();
        ApplicationInfo ai = context.getApplicationInfo();
        sb.append("Application : ").append(pm.getApplicationLabel(ai)).append("\r\n");

        try {
            PackageInfo pi = pm.getPackageInfo(ai.packageName, 0);
            sb.append("Version Code: ").append(pi.versionCode).append("\r\n");
            sb.append("Version Name: ").append(pi.versionName).append("\r\n");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        sb.append("\r\n").append("DEVICE INFORMATION").append("\r\n");
        sb.append("Board: ").append(Build.BOARD).append("\r\n");
        sb.append("BOOTLOADER: ").append(Build.BOOTLOADER).append("\r\n");
        sb.append("BRAND: ").append(Build.BRAND).append("\r\n");
        sb.append("CPU_ABI: ").append(Build.CPU_ABI).append("\r\n");
        sb.append("CPU_ABI2: ").append(Build.CPU_ABI2).append("\r\n");
        sb.append("DEVICE: ").append(Build.DEVICE).append("\r\n");
        sb.append("DISPLAY: ").append(Build.DISPLAY).append("\r\n");
        sb.append("FINGERPRINT: ").append(Build.FINGERPRINT).append("\r\n");
        sb.append("HARDWARE: ").append(Build.HARDWARE).append("\r\n");
        sb.append("HOST: ").append(Build.HOST).append("\r\n");
        sb.append("ID: ").append(Build.ID).append("\r\n");
        sb.append("MANUFACTURER: ").append(Build.MANUFACTURER).append("\r\n");
        sb.append("PRODUCT: ").append(Build.PRODUCT).append("\r\n");
        sb.append("TAGS: ").append(Build.TAGS).append("\r\n");
        sb.append("TYPE: ").append(Build.TYPE).append("\r\n");
        sb.append("USER: ").append(Build.USER).append("\r\n");

        return sb.toString();
    }

    public void addAttachment(String filePath, String fileName) throws Exception {

        MimeMultipart multipart = new MimeMultipart();
        BodyPart messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(filePath);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(fileName);
        multipart.addBodyPart(messageBodyPart);
    }
}
