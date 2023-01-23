package com.example.myapplication

data class GetAllCart(
    val cart_id: String?,
    val product__name: String?,
    val product__price: String?,
    val product__stock: String?,
    val product__product_type__name: String?,
    val qty: String?,
    val product__product_category__name: String?,
)
