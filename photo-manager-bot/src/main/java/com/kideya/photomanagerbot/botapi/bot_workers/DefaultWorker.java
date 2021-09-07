package com.kideya.photomanagerbot.botapi.bot_workers;

import com.kideya.photomanagerbot.botapi.commands.BotCommand;
import com.kideya.photomanagerbot.services.TextService;
import com.kideya.photomanagerbot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

@Component
public class DefaultWorker implements Worker {

    @Autowired
    private List<BotCommand> commands;

    @Autowired
    private TextService textService;

    @Override
    public BotApiMethod<?> doWork(Update update) {

        if (update.hasCallbackQuery()) {
            return  textService.getMessage(Utils.getChatId(update), "reply.startMessage");
        }

        String text = update.getMessage().getText();

        Optional<BotCommand> currentCommand = commands.stream()
                .filter(botCommand -> botCommand.getCommandName().equals(text))
                .findFirst();

        if (currentCommand.isPresent()) {
            return currentCommand.get().runCommand(update);
        }

        return textService.getMessage(Utils.getChatId(update), "reply.startMessage");
    }

    @Override
    public boolean isStillWorking(Integer userId) {
        return true;
    }
}
