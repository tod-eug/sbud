package bot.commands;

import bot.ReplyConstants;
import controllers.DividendController;
import db.AnalyticsApi;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class DividendCalendarCommand implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return "dividendcalendar";
    }

    @Override
    public String getDescription() {
        return "dividendcalendar";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {

        AnalyticsApi.createEvent(message.getFrom(), message.getChatId().toString(), "dividendcalendar", "", "");

        SendMessage sm = new SendMessage();
        sm.setChatId(message.getChatId());
        sm.setText(DividendController.getDividendCalendar(message.getFrom(), message.getChatId().toString()));
        MessageProcessor.sendMsg(absSender, sm);
    }
}
