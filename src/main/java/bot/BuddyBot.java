package bot;

import Util.PropertiesProvider;
import bot.MessageProviders.PortfolioMP;
import bot.commands.*;
import bot.keyboards.TicketOptionsKeyboard;
import dto.Status;
import db.DbPortfolioApi;
import dto.Ticker;
import http.TickerApi;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuddyBot extends TelegramLongPollingCommandBot {

    public static Map<Long, Status> state = new HashMap<>();
    public static Map<Long, List<Ticker>> tickerOptions = new HashMap<>();

    public BuddyBot() {
        super();
        register(new StartCommand());
        register(new CancelCommand());
        register(new AddStockCommand());
        register(new DividendCalendarCommand());
        register(new ShowPortfolioCommand());
        register(new DeleteTickerCommand());
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

        if (update.hasCallbackQuery()) {
            processCallbackQuery(update);
        }

        if (update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();
            if (state.containsKey(chatId)) {
                if (state.get(chatId) == Status.ADD_STOCK) {
                    List<Ticker> options = TickerApi.getTickers(update.getMessage().getText());
                    if (options.size() > 0) {
                        sendMessage(chatId, ReplyConstants.CHOOSE_TICKER, false, TicketOptionsKeyboard.getTicketOptionsKeyboard(options, SysConstants.CHOOSE_TICKER_CALLBACK_TYPE));
                        state.remove(chatId);
                        tickerOptions.put(chatId, options);
                    } else
                        sendMessage(chatId, ReplyConstants.NO_TICKERS_FOUNDED, false, null);
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

    private void processCallbackQuery(Update update) {
        Long userId = update.getCallbackQuery().getFrom().getId();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();

        String[] parsedCallback = update.getCallbackQuery().getData().split(SysConstants.DELIMITER);

        switch (parsedCallback[0]) {
            case SysConstants.DELETE_TICKER_CALLBACK_TYPE:
                processDeleteTickerCallbackQuery(parsedCallback, userId, chatId, messageId, update.getCallbackQuery().getFrom());
                break;
            case SysConstants.CHOOSE_TICKER_CALLBACK_TYPE:
                processChooseTickerCallbackQuery(parsedCallback, userId, chatId, messageId, update.getCallbackQuery().getFrom());
                break;
        }
    }

    private void processDeleteTickerCallbackQuery(String[] parsedCallback, Long userId, Long chatId, int messageId, User user) {

        String ticker = parsedCallback[1];
        DbPortfolioApi.deleteTicker(user, chatId.toString(), ticker);
        List<Ticker> portfolio = DbPortfolioApi.getPortfolio(user, chatId.toString());

        String header = "Ticker: " + ticker + " deleted.\n\n";
        String text = header + PortfolioMP.getShowPortfolioText(portfolio);

        editMessage(chatId, messageId, text, false, null);
    }

    private void processChooseTickerCallbackQuery(String[] parsedCallback, Long userId, Long chatId, int messageId, User user) {

        String chosenTicker = parsedCallback[1];
        if (tickerOptions.containsKey(chatId)) {
            List<Ticker> options = tickerOptions.get(chatId);

            Ticker ticker = null;

            for (Ticker t : options) {
                if (t.getTicker().equals(chosenTicker))
                    ticker = t;
            }

            DbPortfolioApi.addStock(user, chatId.toString(), ticker);

            List<Ticker> portfolio = DbPortfolioApi.getPortfolio(user, chatId.toString());
            String text = ReplyConstants.STOCK_ADDED + "\n" + PortfolioMP.getShowPortfolioText(portfolio);
            editMessage(chatId, messageId, text, false, null);
        } else
            editMessage(chatId, messageId, ReplyConstants.ERROR, false, null);
    }


    private void sendMessage(long chatId, String text, boolean htmlParseMode, InlineKeyboardMarkup keyboard) {
        SendMessage sm = new SendMessage();
        sm.setChatId(Long.toString(chatId));
        sm.setText(text);
        if (keyboard != null)
            sm.setReplyMarkup(keyboard);
        if (htmlParseMode)
            sm.setParseMode(ParseMode.HTML);
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void editMessage(long chatId, int messageId, String text, boolean htmlParseMode, InlineKeyboardMarkup keyboard) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setText(text);
        if (keyboard != null)
            editMessageText.setReplyMarkup(keyboard);
        if (htmlParseMode)
            editMessageText.setParseMode(ParseMode.HTML);
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}


