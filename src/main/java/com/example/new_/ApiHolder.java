package com.example.new_;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiHolder {
  @GET("forecast/{city}/summary")
  Call<WeatherResponse> getByCity(@Path("city") String city);
}
