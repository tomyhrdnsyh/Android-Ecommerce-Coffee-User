package com.example.myapplication

data class GetAllOrders (
    val order_id: String?,
    val qty: String?,
    val product__name: String?,
    val order__gross_amount: String?,
    val order__status: String?,
    val datetime: String?,
)