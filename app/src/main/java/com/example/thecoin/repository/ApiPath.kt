package com.example.thecoin.repository

import com.example.thecoin.model.Coin
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiPath{
    @GET("/json/last/{coinOne}-{coinTwo}")
    fun coinConverter(@Path("coinOne") coinOne : String,@Path("coinTwo") coinTwo : String) : Call<Map<String ,Coin>>
}