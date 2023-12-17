package http;

import okhttp3.HttpUrl;

public class ApiKeyProvider {

    public static final String apiDatePattern = "yyyy-MM-dd";

    public static HttpUrl.Builder getUrl(String url) {
        return HttpUrl.parse(url)
                .newBuilder()
                .addQueryParameter("apikey", "6eba9ae7fdff78e7038c55bfb79fa247");
    }
}
