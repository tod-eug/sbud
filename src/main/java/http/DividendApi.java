package http;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;

public class DividendApi {

    public static String getDividends(String from, String to) {

        OkHttpClient client = new OkHttpClient();

        HttpUrl httpUrl = ApiKeyProvider.getUrl("https://financialmodelingprep.com/api/v3/stock_dividend_calendar")
                .addQueryParameter("from", from)
                .addQueryParameter("to", to)
                .build();
        Request r = new Request.Builder().url(httpUrl).build();

        String response = null;
        try {
            response = client.newCall(r).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


}
