package com.chann.tipster.api;

import com.chann.tipster.data.EditProfile;
import com.chann.tipster.data.Login;
import com.chann.tipster.data.Profile;
import com.chann.tipster.data.Register;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiEnd {

    @FormUrlEncoded
    @POST("/api/login")
    Call<Login> userLogin(@Field("phone_number") String phoneNumber, @Field("password") String password);

    @FormUrlEncoded
    @POST("/api/register")
    Call<Register> userRegister(@Field("phone_number") String phoneNumber, @Field("password") String password);

    @Multipart
    @POST("/api/profile_edit")
    Call<EditProfile> profileEdit(@Part("token") RequestBody token , @Part("name") RequestBody name , @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("/api/profile")
    Call<Profile> getProfile(@Field("token") String token);
}
