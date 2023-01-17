package com.example.myapplication

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @GET("api/all_product/")
    fun getAllProduct(): Call<ArrayList<GetJenisProduk>>
}