package com.addtext.textonphoto.textart.TART_UnsplashImage;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TART_UnsplashClient {
    private static Retrofit retrofit = null;

    public static Retrofit getUnsplashClient() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new TART_HeaderInterceptor(TART_Config.unsplash_access_key)).build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(TART_Config.BASE_URL_UNSPLASH)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
