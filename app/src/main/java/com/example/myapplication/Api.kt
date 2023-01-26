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
    @POST("api/address/")
    fun getUserProfile(
        @Field("username") username: String,
    ): Call<GetUserProfile>

    @FormUrlEncoded
    @POST("api/registration/")
    fun register(
        @Field("username") username: String,
        @Field("full_name") full_name: String,
        @Field("no_hp") no_hp: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("address") address: String,
    ): Call<MessageResponse>

    @FormUrlEncoded
    @POST("api/edit_user_profile/")
    fun editUserProfile(
        @Field("username") username: String,
        @Field("nama_lengkap") nama_lengkap: String,
        @Field("no_hp") no_hp: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("alamat") alamat: String,
    ): Call<MessageResponse>

    @FormUrlEncoded
    @POST("api/add_order/")
    fun addOrder(
        @Field("product_id") product_id: String,
        @Field("username") username: String,
        @Field("quantity") quantity: String
    ): Call<MessageResponse>

    @FormUrlEncoded
    @POST("api/add_order_from_cart/")
    fun addOrderFromCart(
        @Field("username") username: String
    ): Call<MessageResponse>

    @FormUrlEncoded
    @POST("api/history_order/")
    fun getOrder(
        @Field("username") username: String
    ): Call<ArrayList<GetAllOrders>>

    @FormUrlEncoded
    @POST("api/history_order_detail/")
    fun getOrderDetails(
        @Field("order_id") order_id: String
    ): Call<ArrayList<GetOrderDetails>>

    @FormUrlEncoded
    @POST("api/acc_order/")
    fun accProduct(
        @Field("order_id") order_id: String
    ): Call<MessageResponse>

    @FormUrlEncoded
    @POST("api/get_cart/")
    fun getCart(
        @Field("username") username: String
    ): Call<ArrayList<GetAllCart>>

    @FormUrlEncoded
    @POST("api/add_cart/")
    fun addCart(
        @Field("product_id") product_id: String,
        @Field("username") username: String,
        @Field("stock") stock: String,
    ): Call<MessageResponse>

    @FormUrlEncoded
    @POST("api/delete_cart/")
    fun deleteCart(
        @Field("cart_id") cart_id: String
    ): Call<MessageResponse>

    @FormUrlEncoded
    @POST("api/auth/")
    fun auth(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<MessageResponse>
}
