package bot.commands;

import bot.MessageProviders.PortfolioMP;
import db.AnalyticsApi;
import db.DbPortfolioApi;
import dto.Ticker;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;

public class ShowPortfolioCommand implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return "showportfolio";
    }

    @Override
    public String getDescription() {
        return "showportfolio";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {

        AnalyticsApi.createEvent(message.getFrom(), message.getChatId().toString(), "showportfolio", "", "");

        List<Ticker> portfolio = DbPortfolioApi.getPortfolio(message.getFrom(), message.getChatId().toString());

        SendMessage sm = new SendMessage();
        sm.setChatId(message.getChatId());
        sm.setText(PortfolioMP.getShowPortfolioText(portfolio));
        MessageProcessor.sendMsg(absSender, sm);
    }
}
