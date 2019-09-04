package com.chann.tipster.retrofit;

import com.chann.tipster.api.ApiEnd;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitService {
    //lazy initialization
    public static final String BASE_URL="http://134.209.102.209";

    private static ApiEnd apiEnd;
    private static RetrofitService retrofitService;

    private RetrofitService(){ }

    public static RetrofitService getInstance(){
        if(retrofitService == null){
            retrofitService = new RetrofitService();
        }


        return retrofitService;
    }

    public static ApiEnd getApiEnd(){


        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit service = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        if(apiEnd == null) {

            apiEnd = service.create(ApiEnd.class);
        }

        return apiEnd;
    }



}
