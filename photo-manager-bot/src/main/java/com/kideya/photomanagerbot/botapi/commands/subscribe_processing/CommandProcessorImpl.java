package com.kideya.photomanagerbot.botapi.commands.subscribe_processing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
public class CommandProcessorImpl implements CommandProcessor {

	@Autowired
	@Qualifier("handlers")
	private Map<String,CommandHandler> handlerMap;

	@Override
	public BotApiMethod<?> process(Update update) {
		String text = update.getMessage().getText();
		if (text!=null){
			CommandHandler commandHandler = handlerMap.get(text);
			if (commandHandler!=null) {
				return commandHandler.handle(update);
			}
		}
		return new AnswerCallbackQuery();
	}

}
