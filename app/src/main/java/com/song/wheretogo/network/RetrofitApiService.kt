package com.song.wheretogo.network

import com.song.wheretogo.model.KakaoSearchPlaceResponse
import com.song.wheretogo.model.NidUserInfoResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitApiService {
    //네아로 사용자정보 API
    @GET("/v1/nid/me")
    fun getNidUserInfo(@Header("Authorization") authorization:String):retrofit2.Call<NidUserInfoResponse>

    //z카카오 키워드 장소검색
    @Headers("Authorization: KakaoAK 703156a795add60f9ffe2266d613988d")
    @GET("/v2/local/search/keyboard.json")
    fun searchPlaces(@Query("query") query: String,@Query("x") longitude:String, @Query("y") latitude:String):retrofit2.Call<KakaoSearchPlaceResponse>
}