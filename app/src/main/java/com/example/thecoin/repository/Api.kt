package com.example.thecoin.repository

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class Api {




    val retrofit = Retrofit.Builder()
        .baseUrl("https://economia.awesomeapi.com.br")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiPath::class.java)
}