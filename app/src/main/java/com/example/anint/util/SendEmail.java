package com.example.anint.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * 发送验证邮件
 */

public class SendEmail{

    // 邮件发送协议
    private String PROTOCOL = "smtp";
    // SMTP邮件服务器
    private String HOST = "smtp.163.com";
    // SMTP邮件服务器默认端口
    private String PORT = "25";
    // 是否要求身份认证
    private String IS_AUTH = "true";
    // 是否启用调试模式（启用调试模式可打印客户端与服务器交互过程时一问一答的响应消息）
    private String IS_ENABLED_DEBUG_MOD = "true";
    // 发件人
    private String from = "int2020uestc@163.com";
    // 收件人
    private String to = "";
    // 初始化连接邮件服务器的会话信息
    private Properties props = null;

    public SendEmail(String toEmail ){
        to = toEmail;
        props = new Properties();
        props.setProperty("mail.transport.protocol", PROTOCOL);
        props.setProperty("mail.smtp.host", HOST);
        props.setProperty("mail.smtp.port", PORT);
        props.setProperty("mail.smtp.auth", IS_AUTH);
        props.setProperty("mail.debug", IS_ENABLED_DEBUG_MOD);
    }

    /**
     * 发送简单的html验证码邮件，Num为验证码
     * @param Num
     * @throws Exception
     */
    public void sendHtmlEmail(long Num) throws Exception {
        // 创建Session实例对象
        Session session = Session.getInstance(props, new MyAuthenticator());

        // 创建MimeMessage实例对象
        MimeMessage message = new MimeMessage(session);
        // 设置邮件主题
        message.setSubject("int注册验证码");
        // 设置发送人
        message.setFrom(new InternetAddress(from));
        // 设置发送时间
        message.setSentDate(new Date());
        // 设置收件人
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        // 设置html内容为邮件正文，指定MIME类型为text/html类型，并指定字符编码为gbk
        message.setContent("<span style='color:blue;'>您好，欢迎使用int！本次的验证码为：</span>"+"<span style='color:red;'>"+Num+"</span>",
                "text/html;charset=gbk");
        // 保存并生成最终的邮件内容
        message.saveChanges();
        // 发送邮件
        Transport.send(message);
    }


    /**
     * 向邮件服务器提交认证信息
     */
    static class MyAuthenticator extends Authenticator {

        private String username = "int2020uestc";

        private String password = "int2020";

        public MyAuthenticator() {
            super();
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {

            return new PasswordAuthentication(username, password);
        }
    }

}
