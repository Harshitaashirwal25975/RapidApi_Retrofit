package com.example.new_;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class ApiInterceptor implements Interceptor {
  @Override
  public Response intercept(Chain chain) throws IOException {
    Request original = chain.request();
    Request request = original.newBuilder()
      .header("x-rapidapi-host", "forecast9.p.rapidapi.com")
      .header("x-rapidapi-key", "0743f370f4msh529cbd62a81a579p15028ajsn81fcb8f9ea29")
      .header("x-application-id", "6276747")
      .method(original.method(), original.body())
      .build();

    return chain.proceed(request);
  }
}
