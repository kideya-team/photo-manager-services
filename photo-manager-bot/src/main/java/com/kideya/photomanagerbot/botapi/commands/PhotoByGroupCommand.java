package com.kideya.photomanagerbot.botapi.commands;

import com.kideya.photomanagerbot.botapi.bot_workers.simple_flow.Params;
import com.kideya.photomanagerbot.botapi.bot_workers.simple_flow.SimpleFlowWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class PhotoByGroupCommand implements BotCommand {

    private static final String NAME = "/photoByGroup";

    @Autowired
    private SimpleFlowWorker simpleFlowWorker;

    @Override
    public BotApiMethod<?> runCommand(Update update) {

        Params params = Params.builder()
                .restPoint(NAME)
                .resourceTag("photo_by_group")
                .argName("group").build();

        return simpleFlowWorker.registerCommand(update, params);
    }

    @Override
    public String getCommandName() {
        return NAME;
    }
}
