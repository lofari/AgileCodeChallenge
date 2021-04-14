package com.example.codechallenge.model

data class PictureListDTO(
    val pictures: List<PictureDTO>,
    val page: Int,
    val pageCount: Int,
    val hasMore: Boolean
)
