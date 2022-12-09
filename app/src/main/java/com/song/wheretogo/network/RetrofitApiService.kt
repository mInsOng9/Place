package com.song.wheretogo.network

import android.telecom.Call
import com.song.wheretogo.model.NidUserInfoResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface RetrofitApiService {
    //네아로 사용자정보 API
    @GET("/v1/nid/me")
    fun getNidUserInfo(@Header("Authorization") authorization:String):Call<NidUserInfoResponse>
}