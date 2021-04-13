package com.example.codechallenge

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.codechallenge.model.AuthDTO
import com.example.codechallenge.model.AuthRequest
import com.example.codechallenge.repository.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

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


}