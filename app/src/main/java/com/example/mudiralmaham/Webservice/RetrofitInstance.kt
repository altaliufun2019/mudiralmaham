package com.example.mudiralmaham.Webservice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val BASE_URL = "http://localhost:8000"

    var retrofit: Retrofit? = null
    get() {
        if (field == null){
            retrofit = retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return field
    }
}