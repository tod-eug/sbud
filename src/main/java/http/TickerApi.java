package http;

import dto.Ticker;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TickerApi {

    private static Integer requestTickersByDefault = 10;

    public static List<Ticker> getTickers(String ticker) {

        OkHttpClient client = new OkHttpClient();

        HttpUrl httpUrl = ApiKeyProvider.getUrl("https://financialmodelingprep.com/api/v3/search-ticker")
                .addQueryParameter("limit", requestTickersByDefault.toString())
                .addQueryParameter("query", ticker)
                .build();
        Request r = new Request.Builder().url(httpUrl).build();

        String response = null;
        try {
            response = client.newCall(r).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parseTicker(response);
    }

    private static List<Ticker> parseTicker(String tickerRaw) {
        List<Ticker> tickers = new ArrayList<>();

        if (tickerRaw != null) {
            JSONParser parser = new JSONParser();
            JSONArray json;

            try {
                json = (JSONArray) parser.parse(tickerRaw);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            for (int i = 0; i < json.size(); i++) {
                JSONObject jsonO = (JSONObject) json.get(i);
                String ticker = "";
                String prettyTicker = "";
                String name = "";
                String currency = "";
                String exchange = "";
                String exchangeShortName = "";
                if (jsonO.get("symbol") != null ) {
                    ticker = jsonO.get("symbol").toString();
                    prettyTicker = jsonO.get("symbol").toString();
                    String[] arr = jsonO.get("symbol").toString().split("\\.");
                    if (arr.length > 0)
                        prettyTicker = arr[0];
                }
                if (jsonO.get("name") != null)
                    name = jsonO.get("name").toString();
                if (jsonO.get("currency") != null)
                    currency = jsonO.get("currency").toString();
                if (jsonO.get("exchangeShortName") != null)
                    exchangeShortName = jsonO.get("exchangeShortName").toString();
                if (jsonO.get("stockExchange") != null)
                    exchange = jsonO.get("stockExchange").toString();

                tickers.add(new Ticker(ticker, prettyTicker, name, currency, exchangeShortName, exchange));
            }
        }
        return tickers;
    }
}
