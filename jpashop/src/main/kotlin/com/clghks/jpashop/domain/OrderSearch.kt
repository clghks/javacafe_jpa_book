package com.clghks.jpashop.domain

data class OrderSearch(
    val memberName: String? = null,
    val orderStatus: OrderStatus? = null
)