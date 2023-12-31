package bot.commands;

import bot.ReplyConstants;
import bot.SysConstants;
import bot.keyboards.PortfolioKeyboard;
import db.AnalyticsApi;
import db.DbPortfolioApi;
import dto.Ticker;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;

public class DeleteTickerCommand implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return "deleteticker";
    }

    @Override
    public String getDescription() {
        return "deleteticker";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {

        AnalyticsApi.createEvent(message.getFrom(), message.getChatId().toString(), "deleteticker", "", "");

        List<Ticker> portfolio = DbPortfolioApi.getPortfolio(message.getFrom(), message.getChatId().toString());

        SendMessage sm = new SendMessage();
        sm.setChatId(message.getChatId());
        sm.setText(ReplyConstants.DELETE_TICKER_REPLAY);
        sm.setReplyMarkup(PortfolioKeyboard.getPortfolioKeyboard(portfolio, SysConstants.DELETE_TICKER_CALLBACK_TYPE));
        MessageProcessor.sendMsg(absSender, sm);
    }
}
