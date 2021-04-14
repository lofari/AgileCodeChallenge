package com.example.codechallenge.repository

import com.example.codechallenge.model.AuthRequest
import com.example.codechallenge.util.Constants
import com.example.codechallenge.model.AuthDTO
import com.example.codechallenge.model.DetailDTO
import com.example.codechallenge.model.PictureListDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST(Constants.AUTH_URL)
    fun getToken(@Body apiKey: AuthRequest): Call<AuthDTO>

    @GET(Constants.IMAGES_URL)
    fun fetchImages(): Call<PictureListDTO>

    @GET("/images/{id}")
    suspend fun fetchImageDetail(
        @Path("id") id: String
    ): Response<DetailDTO>

    @GET("/images")
    suspend fun loadImages(@Query("page") page: Int): Response<PictureListDTO>
}
