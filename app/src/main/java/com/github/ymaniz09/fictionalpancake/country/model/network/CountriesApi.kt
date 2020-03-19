package com.github.ymaniz09.fictionalpancake.country.model.network

import com.github.ymaniz09.fictionalpancake.country.model.Country
import retrofit2.Response
import retrofit2.http.GET

interface CountriesApi {
    @GET("DevTides/countries/master/countriesV2.json")
    suspend fun getCountries(): Response<List<Country>>
}