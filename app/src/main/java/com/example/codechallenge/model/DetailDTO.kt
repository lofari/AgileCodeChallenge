package com.example.codechallenge.model

import com.google.gson.annotations.SerializedName

data class DetailDTO (
    @SerializedName("id") val id : String,
    @SerializedName("author") val author : String,
    @SerializedName("camera") val camera : String,
    @SerializedName("tags") val tags : String,
    @SerializedName("cropped_picture") val cropped_picture : String,
    @SerializedName("full_picture") val full_picture : String
)
