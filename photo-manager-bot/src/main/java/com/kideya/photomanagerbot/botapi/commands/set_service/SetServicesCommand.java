package com.kideya.photomanagerbot.botapi.commands.set_service;

import com.kideya.photomanagerbot.botapi.TelegramFacade;
import com.kideya.photomanagerbot.botapi.bot_workers.Worker;
import com.kideya.photomanagerbot.botapi.bot_workers.simple_flow.handlers.Handler;
import com.kideya.photomanagerbot.botapi.commands.BotCommand;
import com.kideya.photomanagerbot.botapi.commands.set_service.buttons_headlers.ButtonPressHandler;
import com.kideya.photomanagerbot.model.settings_service.Settings;
import com.kideya.photomanagerbot.services.SendingMessageService;
import com.kideya.photomanagerbot.utils.KeyboardBuilder;
import com.kideya.photomanagerbot.model.settings_service.ServiceSettings;
import com.kideya.photomanagerbot.services.LocaleTextService;
import com.kideya.photomanagerbot.services.TextService;
import com.kideya.photomanagerbot.utils.MicroservicesNames;
import com.kideya.photomanagerbot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.*;

@Component
public class SetServicesCommand implements BotCommand, Worker {

    private final String name = "/setServices";

    @Autowired
    private TextService textService;

    @Autowired
    private LocaleTextService localeTextService;

    @Autowired
    private List<ButtonPressHandler> handlers;

    @Autowired
    private TelegramFacade facade;

    @Autowired
    private SendingMessageService sendingMessageService;

    private String btnName;

    private final Map<Integer, List<ServiceSettings>> cache = new HashMap<>();
    private final Map<Integer, ButtonPressHandler> currentHandler = new HashMap<>();

    @Override
    public BotApiMethod<?> runCommand(Update update) {

        Integer userId = update.getMessage().getFrom().getId();
        facade.changeBehaviour(userId, this);
        return sendStartMessage(update);
    }

    @Override
    public String getCommandName() {
        return name;
    }

    private InlineKeyboardMarkup buildKeyboard(List<ServiceSettings> settings) {

        KeyboardBuilder keyboardBuilder = new KeyboardBuilder();

        for (SupportedServices service : SupportedServices.values()) {

            String buttonName = service.getName();

            ServiceSettings userService = getSettingByName(buttonName, settings);

            if (userService != null) {
                if (userService.isActive()) {
                    buttonName += " - " + localeTextService.getTranslatedText("button.status.enabled");
                } else {
                    buttonName += " - " + localeTextService.getTranslatedText("button.status.disabled");
                }
                keyboardBuilder.addButton(buttonName, service.getName());
            } else {
                buttonName += " - " + localeTextService.getTranslatedText("button.status.unsupported");
                keyboardBuilder.addButton(buttonName, "default " + buttonName);
            }
        }


        return keyboardBuilder.addButton(localeTextService.getTranslatedText("button.back"), "back").build();
    }

    private ServiceSettings getSettingByName(String name, List<ServiceSettings> settings) {
        return settings.stream()
                .filter(setting -> setting.getServiceName().equals(name))
                .findFirst().orElse(null);
    }

    @Override
    public BotApiMethod<?> doWork(Update update) {
        Integer userId = Utils.getUserId(update);
        List<ServiceSettings> settings = cache.get(userId);

        if (currentHandler.containsKey(userId)) {
            ButtonPressHandler handler = currentHandler.get(userId);

            if (handler.isFinished(userId)) {
                currentHandler.remove(userId);
                return sendStartMessage(update);
            } else {
                return handler.pressHandle(update, getSettings(settings, handler.getName()));
            }
        }

        if (update.hasCallbackQuery()) {
            String handlerName = update.getCallbackQuery().getData();

            for (ButtonPressHandler handler : handlers) {
                if (handler.getName().equals(handlerName)) {

                    currentHandler.put(userId, handler);
                    return handler.pressHandle(update, getSettings(settings, handlerName));
                }
            }

            if (handlerName.startsWith("default")) {
                btnName = handlerName.split(" ")[1];
                ButtonPressHandler handler = findHandlerByName("default");
                currentHandler.put(userId, handler);
                return handler.pressHandle(update, prepareNewSettings(btnName));
            }

            throw new IllegalArgumentException();
        }

        return new AnswerCallbackQuery();
    }

    private ButtonPressHandler findHandlerByName(String name) {
        return handlers.stream().filter(n -> n.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public boolean isStillWorking(Integer userId) {
        return cache.containsKey(userId);
    }

    @Component
    protected class BackButtonHandler implements ButtonPressHandler {

        @Override
        public String getName() {
            return "back";
        }

        @Override
        public BotApiMethod<?> pressHandle(Update update, ServiceSettings settings) {
            Integer userId = update.getCallbackQuery().getFrom().getId();
            cache.remove(userId);
            return new AnswerCallbackQuery();
        }

        @Override
        public boolean isFinished(Integer userId) {
            return true;
        }
    }

    private ServiceSettings getSettings(List<ServiceSettings> settings, String serviceName) {
        return settings.stream().filter(setting -> setting.getServiceName().equals(serviceName)).findFirst().orElse(null);
    }

    private ServiceSettings prepareNewSettings(String btnName) {
        ServiceSettings settings = new ServiceSettings();

        settings.setActive(false);
        settings.setServiceName(btnName);
        settings.setParams("");

        return settings;
    }

    private SendMessage sendStartMessage(Update update) {

        Integer userId = Utils.getUserId(update);

        Settings response = sendingMessageService
                .sendGet(MicroservicesNames.SETTINGS_SERVICE_NAME, "/api/settings/user/" + userId, Settings.class).getBody();

        List<ServiceSettings> settings = new ArrayList<>();

        if (response != null) {
            settings = response.getServiceSettings();
        }

        cache.put(userId, settings);

        SendMessage message =
                textService.getMessage(Utils.getChatId(update), "reply.setGroupHeader");

        message.setReplyMarkup(buildKeyboard(settings));
        return message;
    }
}
