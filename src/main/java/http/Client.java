package http;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;

public class Client {

    public static void getDividends() {
//        UrlBuilder urlBuilder = new UrlBuilder();

        OkHttpClient client = new OkHttpClient();

        HttpUrl httpUrl = HttpUrl.parse("https://financialmodelingprep.com/api/v3/stock_dividend_calendar")
                .newBuilder()
                .addQueryParameter("apikey", "6eba9ae7fdff78e7038c55bfb79fa247")
                .addQueryParameter("from", "2023-10-10")
                .addQueryParameter("to", "2023-11-30")
                .build();
        Request r = new Request.Builder().url(httpUrl).build();

        String response = null;
        try {
            response = client.newCall(r).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}