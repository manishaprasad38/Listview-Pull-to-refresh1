package com.demo.app.assignment.viewmodel.callback;

import com.demo.app.assignment.model.News;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class ApiServices {
    public static String BASE_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/";
    private static volatile ApiServices apiServices = new ApiServices();
    //private constructor
    private ApiServices() {
    }

    public static ApiServices getInstance() {
        return apiServices;
    }

    public Retrofit getClient(String baseURL) {
        Retrofit retroClint = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
                        .connectTimeout(60, TimeUnit.SECONDS).build())
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder().setLenient().serializeNulls().create()))
                .build();
        return retroClint;
    }

    public Retrofit getAuthorizedClint(String token) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().serializeNulls().create()))
                .client(new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .addInterceptor(new AuthenticationInterceptor(token)).build())
                .build();
    }


    public interface ApiInterfaces {

        @GET("facts.json")
        Call<News> news();
      //  Call <List<News>> news();

    }
}
