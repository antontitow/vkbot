package ru.kkb.bot.vk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.kkb.bot.vk.audit.AccessLoggerAspect;

/**
 * App config
 * @author Marchenko_DS in 31/05/2021 - 17:51
 */
@Configuration
@ComponentScan({"ru.kkb.bot.vk.component","ru.kkb.bot.vk.service"})
public class AppConfig {

    /**
     * Log all Rest Api request
     * @return
     */
    @Bean
    public AccessLoggerAspect geAccessLoggerAspect() {
        return new AccessLoggerAspect();
    }


}
