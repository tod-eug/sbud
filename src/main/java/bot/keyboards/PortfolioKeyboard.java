package bot.keyboards;

import bot.SysConstants;
import org.apache.commons.collections4.ListUtils;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class PortfolioKeyboard {

    public static final int buttonsInLineDefault = 3;

    public static InlineKeyboardMarkup getPortfolioKeyboard(List<String> portfolio, String callbackType) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        if (portfolio != null) {
            if (!portfolio.isEmpty()) {
                int buttonsInLine = buttonsInLineDefault;

                List<List<String>> dividedList = ListUtils.partition(portfolio, buttonsInLine);

                for (List<String> l : dividedList) {
                    List<InlineKeyboardButton> rowInline = new ArrayList<>();
                    for (String s : l) {
                        InlineKeyboardButton button = new InlineKeyboardButton();
                        button.setText(s);
                        button.setCallbackData(callbackType + SysConstants.DELIMITER + s);
                        rowInline.add(button);
                    }
                    rowsInline.add(rowInline);
                }
            }
        }

        keyboardMarkup.setKeyboard(rowsInline);
        return keyboardMarkup;
    }
}
