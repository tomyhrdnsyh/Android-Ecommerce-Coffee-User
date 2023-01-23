package com.example.myapplication

data class GetOrderDetails(
    val order_id: String?,
    val orderdetails__qty: String?,
    val product__name: String?,
    val product__product_type__name: String?,
    val product__price: String?,
)
