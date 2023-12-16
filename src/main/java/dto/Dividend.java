package dto;

import java.util.Date;

public class Dividend {

    private final Date recordDate;

    private final Date paymentDate;

    private final String ticker;

    private final Double dividend;

    public Dividend(String ticker,
                    Double dividend,
                    Date recordDate,
                    Date paymentDate) {
        this.ticker = ticker;
        this.dividend = dividend;
        this.recordDate = recordDate;
        this.paymentDate = paymentDate;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public String getTicker() {
        return ticker;
    }

    public Double getDividend() {
        return dividend;
    }
}
