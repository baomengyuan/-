package com.swjtu.Service;

import com.swjtu.Controller.HelloController;
import com.swjtu.Util.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author baomengyuan
 * @create 2021-12-14 18:56
 */
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    public static void sendMail(String title, String html, String to){
        logger.info("******开始发送邮件******");
        MailUtil.sendMail(title,html,to);
        logger.info("******邮件发送完成******");
    }
}
