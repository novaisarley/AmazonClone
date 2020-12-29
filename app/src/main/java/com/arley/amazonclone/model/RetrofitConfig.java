package com.arley.amazonclone.model;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    private RetrofitConfig(){

    }

    private static com.arley.amazonclone.model.RetrofitConfig config = null;

    public static com.arley.amazonclone.model.RetrofitConfig getInstance(){
        if (config==null){
            config = new com.arley.amazonclone.model.RetrofitConfig();
        }
        return config;
    }

    public Retrofit getRetrofitConfig(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .serializeNulls().create()))
                        .build();

        return retrofit;
    }

    public FakeStoreServer getSactServer(){
        Retrofit retrofit = getRetrofitConfig();
        return retrofit.create(FakeStoreServer.class);
    }

}
