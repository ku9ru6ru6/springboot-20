package me.cai.demo.valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * me.cai.demo.valid
 *
 * @author caiguangzheng
 * date: 2020/5/7
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Configuration
public class SpringValidConfiguration {

    /**
     * i18n config
     */
    @Bean
    @ConditionalOnMissingBean(MessageSource.class)
    public MessageSource messageSource(@Value("${me.cai.messages:messages}") String extMessagesClasspath) {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.addBasenames("classpath:" + extMessagesClasspath);
        messageSource.setCacheSeconds(3600);
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
