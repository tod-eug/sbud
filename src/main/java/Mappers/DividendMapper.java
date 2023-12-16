package Mappers;

import dto.Dividend;
import http.Client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DividendMapper {

    public static Dividend mapDividend(Object ticker, Object dividend, Object recordDate, Object paymentDate) {

        SimpleDateFormat datePattern = new SimpleDateFormat(Client.apiDatePattern);

        String tickerR;
        Double dividendR;
        Date recordDateR;
        Date paymentDateR;

        if (ticker == null)
            tickerR = "Unknown ticker";
        else
            tickerR = ticker.toString();

        if (dividend == null)
            dividendR = 0.0;
        else
            dividendR = Double.parseDouble(dividend.toString().replace(",", "."));

        try {
            if (recordDate == null)
                recordDateR = datePattern.parse("2100-01-01");
            else
                recordDateR = datePattern.parse(recordDate.toString());

            if (paymentDate == null)
                paymentDateR = datePattern.parse("2100-01-01");
            else
                paymentDateR = datePattern.parse(paymentDate.toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return new Dividend(tickerR, dividendR, recordDateR, paymentDateR);
    }
}
