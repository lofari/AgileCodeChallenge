package com.example.codechallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codechallenge.model.DetailDTO
import com.example.codechallenge.repository.ApiService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    val detail: LiveData<DetailDTO>
        get() = _detail
    private val _detail = MutableLiveData<DetailDTO>()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        // Log Error
    }

    fun fetchImageDetail(apiService: ApiService, imageId: String) {
        viewModelScope.launch(IO + coroutineExceptionHandler) {
            val response = apiService.fetchImageDetail(imageId)
            if (response.isSuccessful) {
                response.body()?.let {
                    _detail.postValue(it)
                }
            }
        }
    }
}
