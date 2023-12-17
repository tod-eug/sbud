package bot.MessageProviders;

import dto.Dividend;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class DividendCalendarMP {

    public static String datePattern = "dd.MM.yyyy";

    public static String getDividendCalendar(List<Dividend> divs) {
        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        Map<String, Dividend> map = convertToMap(divs);
        List<Dividend> divFiltered = filterOnlyUpcomingDivs(map);
        StringBuilder sb = new StringBuilder();
        sb.append("Upcoming dividends: \n");
        for (Dividend d : divFiltered) {
            sb.append(formatter.format(d.getPaymentDate())).append(": \n");
            sb.append(d.getTicker()).append(" - ").append(d.getDividend()).append("\n");
            sb.append("\n");
        }
        return sb.toString();
    }

    private static Map<String, Dividend> convertToMap(List<Dividend> divs) {
        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        Map<String, Dividend> map = new HashMap<>();
        for (Dividend d : divs) {
            map.put(formatter.format(d.getPaymentDate()) + d.getTicker(), d);
        }
        return map;
    }

    private static List<Dividend> filterOnlyUpcomingDivs(Map<String, Dividend> map) {
        Set<String> set = map.keySet();
        List<Dividend> result = new ArrayList<>();

        for (String s : set) {
            String stringDate = s.substring(0,10);
            LocalDate d = LocalDate.parse(convertDates(stringDate));
            if (d.isAfter(LocalDate.now()))
                result.add(map.get(s));
        }
        return result;
    }

    /**
     * Convert string from 31.12.2023 to 2023-12-31
     * @param s
     * @return
     */
    private static String convertDates(String s) {
        String day = s.substring(0,2);
        String month = s.substring(3,5);
        String year = s.substring(6,10);
        return year + "-" + month + "-" + day;
    }
}
