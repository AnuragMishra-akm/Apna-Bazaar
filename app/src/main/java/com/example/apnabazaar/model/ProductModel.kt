package com.example.apnabazaar.model

data class ProductModel(
    val id : String,
    val title : String,
    val description : String,
    val price : String,
    val actualPrice : String,
    val images : List<String> = emptyList(),
    val category : String
)
