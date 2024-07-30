package com.example.new_;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;

public class RetrofitClient {
  private static Retrofit retrofit = null;
  private static final String BASE_URL = "https://forecast9.p.rapidapi.com/rapidapi/forecast/";

  public static Retrofit getClient() {
    if (retrofit == null) {
      OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(new ApiInterceptor())
        .build();

      retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    }
    return retrofit;
  }
}

