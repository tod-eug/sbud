package bot.commands;

import bot.BuddyBot;
import bot.ReplyConstants;
import db.AnalyticsApi;
import dto.Status;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class AddStockCommand implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return "addstock";
    }

    @Override
    public String getDescription() {
        return "addstock";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {

        AnalyticsApi.createEvent(message.getFrom(), message.getChatId().toString(), "addstock", "", "");

        BuddyBot.state.put(message.getChatId(), Status.ADD_STOCK);

        SendMessage sm = new SendMessage();
        sm.setChatId(message.getChatId());
        sm.setText(ReplyConstants.ADD_STOCK_REPLY);
        MessageProcessor.sendMsg(absSender, sm);
    }
}
