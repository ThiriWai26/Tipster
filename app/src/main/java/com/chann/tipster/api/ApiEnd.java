package com.chann.tipster.api;

import com.chann.tipster.data.BetResponse;
import com.chann.tipster.data.CurrentCoin;
import com.chann.tipster.data.EditProfile;
import com.chann.tipster.data.Login;
import com.chann.tipster.data.MatchListData;
import com.chann.tipster.data.MatchListResponse;
import com.chann.tipster.data.OddsData;
import com.chann.tipster.data.Profile;
import com.chann.tipster.data.Register;
import com.chann.tipster.data.RoomListOfLeague;


import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiEnd {

    @FormUrlEncoded
    @POST("/api/login")
    Observable<Login> userLogin(@Field("phone_number") String phoneNumber, @Field("password") String password);

    @FormUrlEncoded
    @POST("/api/register")
    Observable<Register> userRegister(@Field("phone_number") String phoneNumber, @Field("password") String password);

    @Multipart
    @POST("/api/profile_edit")
    Call<EditProfile> profileEdit(@Part("token") RequestBody token , @Part("name") RequestBody name , @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("/api/profile")
    Observable<Profile> getProfile(@Field("token") String token);

    @FormUrlEncoded
    @POST("/api/matchList")
    Call<MatchListResponse> getMatchList(@Field("token") String token);

    @FormUrlEncoded
    @POST("/api/odds")
    Call<OddsData> getOddsData(@Field("token") String token , @Field("match_id") int matchId , @Field("type") int type , @Field("room_id") int roomId);

    @FormUrlEncoded
    @POST("/api/bet")
    Call<BetResponse> bet(@Field("token") String token , @Field("type") int type , @Field("room_id") int roomId , @Field("match_id") int matchId,
                          @Field("fixture_id") int fixtureId, @Field("bet_amount") int betAmount, @Field("bet_handicap") int betHandiCap , @Field("bet_value") int betValue,
                          @Field("label") int label, @Field("bet_type") int betType);

    @FormUrlEncoded
    @POST("/api/league_list")
    Call<RoomListOfLeague> getRoomListOfLeague(@Field("token") String token , @Field("type") int typeId);

    @FormUrlEncoded
    @POST("/api/current_coin")
    Call<CurrentCoin> getCurrentCoin(@Field("token") String token , @Field("type") int typeId, @Field("room_id") int roomId);


}
