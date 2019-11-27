package com.chann.tipster.api;

import com.chann.tipster.data.BetHistoryResponse;
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
import com.chann.tipster.data.StandingResponse;


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
    Observable<EditProfile> profileEdit(@Part("token") RequestBody token , @Part("name") RequestBody name , @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("/api/profile")
    Observable<Profile> getProfile(@Field("token") String token);

    @FormUrlEncoded
    @POST("/api/matchList")
    Observable<MatchListResponse> getMatchList(@Field("token") String token , @Field("day") String date);

    @FormUrlEncoded
    @POST("/api/odds")
    Observable<OddsData> getOddsData(@Field("token") String token , @Field("match_id") int matchId , @Field("type") int type , @Field("room_id") int roomId);

    @FormUrlEncoded
    @POST("/api/bet")
    Observable<BetResponse> bet(@Field("token") String token , @Field("type") int type , @Field("room_id") int roomId , @Field("match_id") int matchId,
                          @Field("fixture_id") int fixtureId, @Field("bet_amount") int betAmount,
                          @Field("label") int label, @Field("bet_type") int betType, @Field("odds_id") int oddsId);

    @FormUrlEncoded
    @POST("/api/league_list")
    Observable<RoomListOfLeague> getRoomListOfLeague(@Field("token") String token , @Field("type") int typeId);

    @FormUrlEncoded
    @POST("/api/current_coin")
    Observable<CurrentCoin> getCurrentCoin(@Field("token") String token , @Field("type") int typeId, @Field("room_id") int roomId);


    @FormUrlEncoded
    @POST("/api/ongoing_history")
    Observable<BetHistoryResponse> getOngoing(@Field("token") String token , @Field("type") int type , @Field("page") int page);

    @FormUrlEncoded
    @POST("/api/finished_history")
    Observable<BetHistoryResponse> getOnfinish(@Field("token") String token , @Field("type") int type , @Field("page") int page);

    @FormUrlEncoded
    @POST("/api/league_standing")
    Observable<StandingResponse> getUserStanding(@Field("token") String token , @Field("type") int leagueType , @Field("id") int roomId , @Field("page") int page);

    @FormUrlEncoded
    @POST("/api/facebook_register")
    Observable<Login> facebookLogin(@Field("facebook_token") String fbToken , @Field("name") String name , @Field("fb_profile") String fbProfile);

    @FormUrlEncoded
    @POST("/api/facebook_register")
    Observable<Register> facebookRegister(@Field("facebook_token") String fbToken , @Field("name") String name , @Field("fb_profile") String fbProfile);
}
