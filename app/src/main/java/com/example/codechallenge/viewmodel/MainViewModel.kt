package com.example.codechallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codechallenge.model.AuthRequest
import com.example.codechallenge.model.PictureDTO
import com.example.codechallenge.repository.ApiService
import com.example.codechallenge.util.Constants
import com.example.codechallenge.util.SessionManager
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await

open class MainViewModel : ViewModel() {

    val pictureList: LiveData<List<PictureDTO>>
        get() = _pictureList
    private val _pictureList = MutableLiveData<List<PictureDTO>>()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        // Log Error
    }

    fun load(apiService: ApiService, sessionManager: SessionManager) {
        viewModelScope.launch(Dispatchers.Main + coroutineExceptionHandler) {
            try {
                var token = withContext(IO) {
                    sessionManager.fetchAuthToken()
                }
                if (token.isNullOrEmpty()) {
                    token = apiService.getToken(
                        AuthRequest(Constants.API_KEY)
                    ).await().token
                    withContext(IO) { sessionManager.saveAuthToken(token) }
                }
                val data =
                    apiService.fetchImages().await()
                _pictureList.postValue(data.pictures)
            } catch (e: Exception) {

            }

        }
    }
}
