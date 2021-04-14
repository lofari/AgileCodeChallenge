package com.example.codechallenge.model

data class DetailDTO(
    val id: String,
    val author: String,
    val camera: String,
    val tags: String,
    val cropped_picture: String,
    val full_picture: String
)
