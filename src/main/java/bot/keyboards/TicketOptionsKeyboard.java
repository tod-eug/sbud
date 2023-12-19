package bot.keyboards;

import bot.SysConstants;
import dto.Ticker;
import org.apache.commons.collections4.ListUtils;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class TicketOptionsKeyboard {

    public static final int buttonsInLineDefault = 2;

    public static InlineKeyboardMarkup getTicketOptionsKeyboard(List<Ticker> ticketOptions, String callbackType) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        boolean analyse = false;
        for (Ticker t : ticketOptions) {
            if (t.getTicker().split("\\.").length > 0) {
                analyse = true;
                break;
            }
        }

        if (ticketOptions != null) {
            if (!ticketOptions.isEmpty()) {
                int buttonsInLine = buttonsInLineDefault;

                List<List<Ticker>> dividedList = ListUtils.partition(ticketOptions, buttonsInLine);

                for (List<Ticker> l : dividedList) {
                    List<InlineKeyboardButton> rowInline = new ArrayList<>();
                    for (Ticker t : l) {
                        InlineKeyboardButton button = new InlineKeyboardButton();
                        if (analyse)
                            button.setText(getTickerWithExchangeText(t));
                        else
                            button.setText(t.getPrettyTicker());
                        button.setCallbackData(callbackType + SysConstants.DELIMITER + t.getTicker());
                        rowInline.add(button);
                    }
                    rowsInline.add(rowInline);
                }
            }
        }

        keyboardMarkup.setKeyboard(rowsInline);
        return keyboardMarkup;
    }

    private static String getTickerWithExchangeText(Ticker ticker) {
        return ticker.getPrettyTicker() + " (" + ticker.getExchange() + ")";
    }
}
