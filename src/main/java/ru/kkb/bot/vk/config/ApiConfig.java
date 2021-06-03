package ru.kkb.bot.vk.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.kkb.bot.vk.dao.SendPackage;

/**
 * Bot Api Config
 * @author Marchenko_DS in 31/05/2021 - 18:22
 */
@Configuration
@ConfigurationProperties(prefix = "bot.api")
@Setter
@Getter
public class ApiConfig {

    private Integer key;
    private String token;

    private SendPackage testMessage;
}
