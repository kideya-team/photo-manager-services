package com.kideya.photomanagerbot.botapi.commands.set_service.buttons_headlers;

import com.kideya.photomanagerbot.botapi.commands.set_service.SupportedServices;
import com.kideya.photomanagerbot.model.settings_service.ServiceSettings;
import com.kideya.photomanagerbot.services.SendingMessageService;
import com.kideya.photomanagerbot.services.TextService;
import com.kideya.photomanagerbot.utils.MicroservicesNames;
import com.kideya.photomanagerbot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class DefaultHandler implements ButtonPressHandler{

    @Autowired
    private SendingMessageService sendingMessageService;

    @Autowired
    private TextService textService;

    @Override
    public String getName() {
        return "default";
    }

    @Override
    public BotApiMethod<?> pressHandle(Update update, ServiceSettings settings) {

        Integer userId = Utils.getUserId(update);
        Long chatId = Utils.getChatId(update);

        ResponseEntity<ServiceSettings> responseEntity =
                sendingMessageService.sendPost(
                        MicroservicesNames.SETTINGS_SERVICE_NAME,
                        "/api/settings/user/" + userId + "/services",
                        settings,
                        ServiceSettings.class);


        return textService.getMessage(chatId, "button.def_handler.ok");
    }

    @Override
    public boolean isFinished(Integer userId) {
        return true;
    }
}
