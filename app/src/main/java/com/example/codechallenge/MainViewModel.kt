package com.example.codechallenge

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.codechallenge.model.AuthDTO
import com.example.codechallenge.model.AuthRequest
import com.example.codechallenge.model.PictureDTO
import com.example.codechallenge.model.PictureListDTO
import com.example.codechallenge.repository.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val pictureList: LiveData<List<PictureDTO>>
        get() = _pictureList
    private val _pictureList = MutableLiveData<List<PictureDTO>>()


    fun getToken(apiService: ApiService, sessionManager: SessionManager) {
        apiService.getToken(
            AuthRequest(
                Constants.API_KEY
            )
        ).enqueue(
            object : Callback<AuthDTO> {
                override fun onFailure(call: Call<AuthDTO>, t: Throwable) {
                    // Log Error
                }

                override fun onResponse(
                    call: Call<AuthDTO>,
                    response: Response<AuthDTO>
                ) {
                    val authResponse = response.body()
                    if (response.code() == 200 && authResponse?.token != null) {
                        Log.e("TOKEN: ", authResponse.token)
                        sessionManager.saveAuthToken(authResponse.token)
                    } else {
                        // Log Error
                    }
                }

            }
        )
    }

    fun fetchPosts(apiService: ApiService, sessionManager: SessionManager) {
        apiService
            .fetchImages(token = "Bearer ${sessionManager.fetchAuthToken()}")
            .enqueue(object : Callback<PictureListDTO> {
                override fun onFailure(call: Call<PictureListDTO>, t: Throwable) {
                    getToken(apiService, sessionManager)
                    Log.e("FAILED FETCHING IMAGES", t.message!!)
                }

                override fun onResponse(
                    call: Call<PictureListDTO>,
                    response: Response<PictureListDTO>
                ) {
                    val response = response.body()
                    response?.pictures?.let {
//                        initList(it)
                        _pictureList.postValue(it)
                    }
                }
            })
    }


}