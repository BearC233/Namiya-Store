package com.namiya.store.project.service.impl;

import com.namiya.store.project.service.NotificationService;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class NotificationServiceImpl implements NotificationService {
    @Override
    public boolean sendNotification(String email,String message1) {
        try {
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

            //配置邮箱信息
            Properties props = System.getProperties();
            //邮件服务器
            props.setProperty("mail.smtp.host", "smtp.qq.com");
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            //邮件服务器端口
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            //鉴权信息
            props.setProperty("mail.smtp.auth", "true");
            //建立邮件会话
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                //身份认证
                protected PasswordAuthentication getPasswordAuthentication() {
                    //1.账户 授权码
                    return new PasswordAuthentication("2891514995@qq.com", "cjurnyxdeguvdece");
                }
            });
            //建立邮件对象
            MimeMessage message = new MimeMessage(session);
            //设置邮件的发件人
            message.setFrom(new InternetAddress("2891514995@qq.com"));
            //2.设置邮件的收件人
            message.setRecipients(Message.RecipientType.TO, email);
            //设置邮件的主题
            message.setSubject("解忧杂货店提醒您,时间胶囊已到期");
            //文本部分
            message.setContent(message1, "text/html;charset=UTF-8");
            message.saveChanges();
            //发送邮件
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
