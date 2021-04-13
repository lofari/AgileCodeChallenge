package com.example.codechallenge.repository

import com.example.codechallenge.model.AuthRequest
import com.example.codechallenge.Constants
import com.example.codechallenge.model.AuthDTO
import com.example.codechallenge.model.DetailDTO
import com.example.codechallenge.model.PictureListDTO
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST(Constants.AUTH_URL)
    fun getToken(@Body request: AuthRequest): Call<AuthDTO>

    @GET(Constants.IMAGES_URL)
    fun fetchImages(@Header("Authorization") token: String): Call<PictureListDTO>

    @GET("/images/{id}")
    fun fetchImageDetail(@Header("Authorization") token: String,@Path("id") id: String): Call<DetailDTO>

    @GET("/images")
    fun loadImages(@Query("page") page: Int): Call<PictureListDTO>

}