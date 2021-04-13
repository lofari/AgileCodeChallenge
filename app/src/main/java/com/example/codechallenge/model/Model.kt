package com.example.codechallenge.model

import com.google.gson.annotations.SerializedName

data class Model(
    val id: String,
    @SerializedName("cropped_picture")
    val image: String
)