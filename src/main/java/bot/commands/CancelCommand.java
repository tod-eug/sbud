package bot.commands;

import bot.BuddyBot;
import bot.ReplyConstants;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class CancelCommand implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return "cancel";
    }

    @Override
    public String getDescription() {
        return "cancel";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {

        SendMessage sm = new SendMessage();
        sm.setChatId(message.getChatId());
        sm.setText(ReplyConstants.CANCEL_REPLY);
        MessageProcessor.sendMsg(absSender, sm);
        BuddyBot.state.remove(message.getChatId());
        BuddyBot.tickerOptions.remove(message.getChatId());
    }
}
