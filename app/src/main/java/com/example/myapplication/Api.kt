package com.example.myapplication

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @GET("api/all_product/")
    fun getAllProduct(): Call<ArrayList<GetAllProduct>>

    @FormUrlEncoded
    @POST("api/add_order/")
    fun addOrder(
        @Field("product_id") product_id: String,
        @Field("username") username: String,
        @Field("quantity") quantity: String
    ): Call<PostOrders>

    @FormUrlEncoded
    @POST("api/history_order/")
    fun getOrder(
        @Field("username") username: String
    ): Call<ArrayList<GetAllOrders>>
}
