package com.swjtu.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

/**
 * @author baomengyuan
 * @create 2021-12-14 18:48
 */
public class MailUtil {
    private static final Logger logger = LoggerFactory.getLogger(MailUtil.class);
    private static JavaMailSenderImpl mailSender;
    public static void sendMail(String title, String html, String to) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setFrom(mailSender.getUsername());
            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(html, true);
            mailSender.send(msg);
        } catch (Exception e) {
            logger.error("sendMail error", e);
        }
    }
    public void setMailSender(JavaMailSenderImpl mailSender) {
        MailUtil.mailSender = mailSender;
    }
    public JavaMailSenderImpl getMailSender() {
        return mailSender;
    }
}
