package com.swjtu.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author baomengyuan
 * @create 2021-12-14 18:47
 */
@Configuration
@ImportResource(locations={"classpath:/application-bean.xml"})
public class MailConfig {}
