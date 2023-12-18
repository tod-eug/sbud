package bot;

import Util.PropertiesProvider;
import bot.commands.AddStockCommand;
import bot.commands.DividendCalendarCommand;
import bot.commands.ShowPortfolioCommand;
import bot.commands.StartCommand;
import dto.Status;
import db.DbPortfolioApi;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuddyBot extends TelegramLongPollingCommandBot {

    public static Map<Long, Status> state = new HashMap<>();

    public BuddyBot() {
        super();
        register(new StartCommand());
        register(new AddStockCommand());
        register(new DividendCalendarCommand());
        register(new ShowPortfolioCommand());
    }

    @Override
    public String getBotUsername() {
        return PropertiesProvider.configurationProperties.get("BotName");
    }

    @Override
    public String getBotToken() {
        return PropertiesProvider.configurationProperties.get("BotToken");
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public void processNonCommandUpdate(Update update) {

        if (update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();
            if (state.containsKey(chatId)) {
                if (state.get(chatId) == Status.ADD_STOCK) {
                    DbPortfolioApi.addStock(update.getMessage().getFrom(), chatId.toString(), update.getMessage().getText());
                    sendMessage(chatId, ReplyConstants.STOCK_ADDED);
                    state.remove(chatId);
                }
            }
        }
    }

    @Override
    public void processInvalidCommandUpdate(Update update) {
        super.processInvalidCommandUpdate(update);
    }

    @Override
    public boolean filter(Message message) {
        return super.filter(message);
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }


    private void sendMessage(long chatId, String text) {
        SendMessage sm = new SendMessage();
        sm.setChatId(Long.toString(chatId));
        sm.setText(text);
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}


