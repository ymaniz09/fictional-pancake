package com.github.ymaniz09.fictionalpancake.model.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CountriesService {

    private val BASE_URL = "https://raw.githubusercontent.com"

    fun getCountriesService(): CountriesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountriesApi::class.java)
    }
}