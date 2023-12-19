package dto;

public class Ticker {

    private String ticker;
    private String prettyTicker;
    private String name;
    private String currency;
    private String exchange;
    private String exchangeFullName;

    public Ticker(String ticker,
                  String prettyTicker,
                  String name,
                  String currency,
                  String exchange,
                  String exchangeFullName) {
        this.ticker = ticker;
        this.prettyTicker = prettyTicker;
        this.name = name;
        this.currency = currency;
        this.exchange = exchange;
        this.exchangeFullName = exchangeFullName;
    }

    public String getTicker() {
        return ticker;
    }

    public String getPrettyTicker() {
        return prettyTicker;
    }

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }

    public String getExchange() {
        return exchange;
    }

    public String getExchangeFullName() {
        return exchangeFullName;
    }
}
