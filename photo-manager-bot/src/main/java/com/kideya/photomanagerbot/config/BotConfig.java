package com.kideya.photomanagerbot.config;


import com.kideya.photomanagerbot.PhotoTelegramBot;
import com.kideya.photomanagerbot.botapi.bot_workers.DefaultWorker;
import com.kideya.photomanagerbot.botapi.bot_workers.Worker;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {
    private String webHookPath;
    private String botUserName;
    private String botToken;

    @Bean
    @SneakyThrows
    public PhotoTelegramBot myWizardTelegramBot() {
        DefaultBotOptions options = ApiContext.getInstance(DefaultBotOptions.class);

        PhotoTelegramBot photoTelegramBot = new PhotoTelegramBot(options);
        photoTelegramBot.setBotUsername(botUserName);
        photoTelegramBot.setBotToken(botToken);
        photoTelegramBot.setBotPath(webHookPath);

        System.out.println("BOT CREATED");
        return photoTelegramBot;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
