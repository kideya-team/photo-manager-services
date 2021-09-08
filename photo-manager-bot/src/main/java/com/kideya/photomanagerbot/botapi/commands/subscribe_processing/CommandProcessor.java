package com.kideya.photomanagerbot.botapi.commands.subscribe_processing;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandProcessor {
	BotApiMethod<?> process(Update update);
}
