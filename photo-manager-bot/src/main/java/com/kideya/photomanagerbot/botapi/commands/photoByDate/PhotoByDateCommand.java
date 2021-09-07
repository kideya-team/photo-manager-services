package com.kideya.photomanagerbot.botapi.commands.photoByDate;

import com.kideya.photomanagerbot.botapi.TelegramFacade;
import com.kideya.photomanagerbot.botapi.bot_workers.Worker;
import com.kideya.photomanagerbot.botapi.commands.BotCommand;
import com.kideya.photomanagerbot.services.LocaleTextService;
import com.kideya.photomanagerbot.services.TelegramApiSendingService;
import com.kideya.photomanagerbot.services.TextService;
import com.kideya.photomanagerbot.utils.DateUtils;
import com.kideya.photomanagerbot.utils.Utils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class PhotoByDateCommand implements BotCommand, Worker {

    private final String commandName = "/photoByDate";

    private final Map<Integer, State> userState = new HashMap<>();
    private final Map<Integer, String> requestString = new HashMap<>();

    @Autowired
    private TextService textService;

    @Autowired
    private TelegramApiSendingService telegramApiSendingService;

    @Autowired
    private LocaleTextService localeService;

    @Autowired
    private TelegramFacade facade;

    @Override
    public BotApiMethod<?> doWork(Update update) {
        if (update.hasMessage()) {
            Integer userId = Utils.getUserId(update);

            State state = userState.get(userId);


            if (state.equals(State.WAITING_FROM_DATE) || state.equals(State.WAITING_TO_DATE)) {
                return parseDate(userId, update.getMessage().getText(), update);
            }

            if (state.equals(State.WAITING_PHOTO)) {
                Long chatId = Utils.getChatId(update);
                telegramApiSendingService.sendTextMessage(chatId,
                        localeService.getTranslatedText("photo_by_date.date_request.request_was_sent_to_server"));
                freeCache(userId);
                return new AnswerCallbackQuery();
            }

        }

        return new AnswerCallbackQuery();
    }

    @Override
    public boolean isStillWorking(Integer userId) {
        return true;
    }

    @Override
    public BotApiMethod<?> runCommand(Update update) {

        Integer userId = Utils.getUserId(update);

        userState.put(userId, State.WAITING_FROM_DATE);
        requestString.put(userId, "/api/photo_catcher/blabla");

        facade.changeBehaviour(userId, this);
        return textService.getMessage(Utils.getChatId(update), "photo_by_date.date_request.from");
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    private BotApiMethod<?> parseDate(Integer userId, String date, Update update) {
        State state = userState.get(userId);

        try {
            DateUtils.convertStringToDate(date);
            String newRequest;
            userState.replace(userId, state.next());

            if (state.equals(State.WAITING_FROM_DATE)) {
                newRequest = requestString.get(userId) + "?to_date=" + date;
                requestString.replace(userId, newRequest);
                return textService.getMessage(userId, "photo_by_date.date_request.to");

            } else {
                newRequest = requestString.get(userId) + "?from_date=" + date;
                requestString.replace(userId, newRequest);
                return doWork(update);
            }

        } catch (RuntimeException exception) {
            return textService.getMessage(userId, "photo_by_date.date_request.bad_request");
        }
    }

    //TODO in another thread
    private void loadPhoto() {

    }

    private void freeCache(Integer userId) {
        userState.remove(userId);
        requestString.remove(userId);
    }
}
