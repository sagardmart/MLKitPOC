package com.mlkitpoc.integration.retrofit

import com.mlkitpoc.list.model.Record
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiClient {

    //https://digital.dmart.in/api/v2/search/210000385?page=1&size=30&storeId=10151
    @GET("api/v2/search/{searchTerm}?page=1&size=30&storeId=10151")
    fun getListOfProducts(@Path("searchTerm") searchTerm: String?): Call<Record?>?
}