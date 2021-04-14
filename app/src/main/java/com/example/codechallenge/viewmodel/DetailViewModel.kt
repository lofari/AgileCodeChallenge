package com.example.codechallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.codechallenge.util.SessionManager
import com.example.codechallenge.model.DetailDTO
import com.example.codechallenge.repository.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    val detail: LiveData<DetailDTO>
        get() = _detail
    private val _detail = MutableLiveData<DetailDTO>()

    fun fetchImageDetail(apiService: ApiService, sessionManager: SessionManager, imageId: String) {
       apiService
            .fetchImageDetail(token = "Bearer ${sessionManager.fetchAuthToken()}", id = imageId)
            .enqueue(object : Callback<DetailDTO> {
                override fun onFailure(call: Call<DetailDTO>, t: Throwable) {
                    // Log Error
                }

                override fun onResponse(
                    call: Call<DetailDTO>,
                    response: Response<DetailDTO>
                ) {
                    val response = response.body()
                    response?.let {
                        _detail.postValue(it)
                    }
                }
            })
    }
}
