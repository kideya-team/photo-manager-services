package com.kideya.photomanagerbot;

import com.kideya.photomanagerbot.botapi.commands.subscribe_processing.CommandHandler;
import com.kideya.photomanagerbot.config.BotConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties(BotConfig.class)
public class PhotoManagerBotApplication {

    @Bean
    @Qualifier("handlers")
    public Map<String, CommandHandler> handlerMap(List<CommandHandler> commandHandlers){
        return commandHandlers.stream().collect(toMap(CommandHandler::getMyCommand, identity()));
    }

    public static void main(String[] args) {
        SpringApplication.run(PhotoManagerBotApplication.class, args);
    }

}
