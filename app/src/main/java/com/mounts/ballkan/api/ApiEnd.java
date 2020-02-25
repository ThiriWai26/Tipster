package com.mounts.ballkan.api;

import com.mounts.ballkan.data.AdsResponse;
import com.mounts.ballkan.data.BetHistoryResponse;
import com.mounts.ballkan.data.BetResponse;
import com.mounts.ballkan.data.CurrentCoin;
import com.mounts.ballkan.data.EditProfile;
import com.mounts.ballkan.data.IsSuccess;
import com.mounts.ballkan.data.Login;
import com.mounts.ballkan.data.MatchListResponse;
import com.mounts.ballkan.data.OddsData;
import com.mounts.ballkan.data.Profile;
import com.mounts.ballkan.data.Register;
import com.mounts.ballkan.data.RoomListOfLeague;
import com.mounts.ballkan.data.StandingResponse;


import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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

    @FormUrlEncoded
    @POST("/api/ads")
    Observable<AdsResponse> getAds(@Field("token") String token);

    @FormUrlEncoded
    @POST("/api/change_name")
    Observable<IsSuccess> changeName(@Field("token") String token, @Field("name") String name);


    @Multipart
    @POST("/api/change_photo")
    Observable<IsSuccess> changePhoto(@Part("token") RequestBody token , @Part MultipartBody.Part image);
}
