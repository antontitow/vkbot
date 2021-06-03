package ru.kkb.bot.vk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Microservice bot for vk bot api
 * @author Marchenko_DS in 31/05/2021 - 17:51
 */
@SpringBootApplication
@ConfigurationPropertiesScan("ru.kkb.bot.vk.config")
@EnableScheduling
public class MessagingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessagingApplication.class, args);
	}

}
