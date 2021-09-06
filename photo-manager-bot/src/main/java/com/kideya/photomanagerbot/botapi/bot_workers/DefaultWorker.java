package com.kideya.photomanagerbot.botapi.bot_workers;

import com.kideya.photomanagerbot.botapi.commands.BotCommand;
import com.kideya.photomanagerbot.services.TextMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class DefaultWorker implements Worker {

    @Autowired
    private List<BotCommand> commands;

    @Autowired
    private TextMessageService textMessageService;

    @Override
    public SendMessage doWork(Update update) {

        String text = update.getMessage().getText();

        commands.stream()
                .filter(botCommand -> botCommand.getCommandName().equals(text))
                .findFirst()
                .ifPresent(BotCommand::runCommand);


        return textMessageService.getMessage(update.getMessage().getChatId(), "reply.startMessage");
    }

    @Override
    public boolean isStillWorking() {
        return true;
    }
}
