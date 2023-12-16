package bot.MessageProviders;

import dto.Dividend;

import java.util.List;

public class DividendCalendarMP {

    public static String getDividendCalendar(List<Dividend> divs) {
        StringBuilder sb = new StringBuilder();
        sb.append("Upcoming dividends: \n");
        for (Dividend d : divs) {
            sb.append(d.getPaymentDate()).append(": \n");
            sb.append(d.getTicker()).append(" - ").append(d.getDividend()).append("\n");
            sb.append("\n");
        }
        return sb.toString();
    }
}
