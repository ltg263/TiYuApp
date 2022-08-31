package com.jxxx.tiyu_app.loginfo;




import android.util.Log;

import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.app.MainApplication;
import com.jxxx.tiyu_app.utils.StringUtil;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * for:
 * create by songqingjun  2021/10/13
 **/
public class MailService {
    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session     和服务器交互的会话
     * @param sendMail    发件人邮箱
     * @param receiveMail 收件人邮箱
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail
            , String receiveMail, String msgStr, File file) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, MainApplication.getContext().getString(R.string.app_name), "UTF-8"));
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "亲爱的开发者"
                , "UTF-8"));
        // 4. Subject: 邮件主题
        message.setSubject("程序奔溃出错--" + StringUtil.getTimeToYMD(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss"), "UTF-8");
        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent("这是一条测试邮件", "text/html;charset=UTF-8");
        // 6. 设置发件时间
        message.setSentDate(new Date());
        // 创建容器描述数据关系
        MimeMultipart mp = new MimeMultipart("mixed");
        message.setContent(mp);


        // 8. 创建邮件正文，为了避免邮件正文中文乱码问题，需要使用CharSet=UTF-8指明字符编码
        MimeBodyPart text = new MimeBodyPart();
        text.setContent(msgStr, "text/html;charset=UTF-8");
        mp.addBodyPart(text);

        // 创建邮件附件
        MimeBodyPart attach = new MimeBodyPart();
        FileDataSource ds = new FileDataSource(file);
        DataHandler dh = new DataHandler(ds);
        attach.setDataHandler(dh);
        attach.setFileName(MimeUtility.encodeText(dh.getName(), "UTF-8", "B"));
        mp.addBodyPart(attach);

        message.saveChanges();

        // 7. 保存设置
        message.saveChanges();
        return message;
    }


}
