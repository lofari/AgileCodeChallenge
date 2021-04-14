package com.example.codechallenge.model

import com.google.gson.annotations.SerializedName

data class PictureListDTO (
    @SerializedName("pictures") val pictures : List<PictureDTO>,
    @SerializedName("page") val page : Int,
    @SerializedName("pageCount") val pageCount : Int,
    @SerializedName("hasMore") val hasMore : Boolean
)
