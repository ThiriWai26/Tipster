package com.chann.tipster.api;

import com.chann.tipster.data.Login;
import com.chann.tipster.data.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiEnd {

    @FormUrlEncoded
    @POST("/api/login")
    Call<Login> userLogin(@Field("phone_number") String phoneNumber, @Field("password") String password);

    @FormUrlEncoded
    @POST("/api/register")
    Call<Register> userRegister(@Field("phone_number") String phoneNumber, @Field("password") String password);



}
