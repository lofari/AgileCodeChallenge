package com.example.codechallenge.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.codechallenge.*
import com.example.codechallenge.adapter.PictureAdapter
import com.example.codechallenge.adapter.OnImageClickListener
import com.example.codechallenge.model.PictureDTO
import com.example.codechallenge.repository.ApiClient
import com.example.codechallenge.repository.ApiService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    OnImageClickListener {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private lateinit var apiService: ApiService
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel =
            ViewModelProvider(
                this
            ).get(MainViewModel::class.java)
        apiClient = ApiClient()
        apiService = apiClient.getApiService(this)
        sessionManager = SessionManager(this)
        setPicturesObserver()
        setLayoutManager()
//        getToken()
        viewModel.getToken(apiService, sessionManager)
//        fetchPosts()
        viewModel.fetchPosts(apiService, sessionManager)
    }

    private fun setLayoutManager() {
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            list.layoutManager = GridLayoutManager(this, 4)
        } else {
            list.layoutManager = GridLayoutManager(this, 2)
        }
    }

    private fun setPicturesObserver() {
        viewModel.pictureList.observe(this, Observer {
            initList(it)
        })
    }

    fun initList(imageList: List<PictureDTO>) {
        val adapter =
            PictureAdapter(imageList, this)
        list.adapter = adapter
    }

    override fun onItemClick(item: PictureDTO, position: Int) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("IMAGE_URL", item.id)
        }
        startActivity(intent)
        overridePendingTransition(
            R.anim.slide_from_right,
            R.anim.slide_from_left
        )
    }


//    private fun getToken() {
//        apiClient.getApiService(this).getToken(
//            AuthRequest(
//                Constants.API_KEY
//            )
//        ).enqueue(
//            object : Callback<AuthDTO> {
//                override fun onFailure(call: Call<AuthDTO>, t: Throwable) {
//                    // Log Error
//                }
//
//                override fun onResponse(
//                    call: Call<AuthDTO>,
//                    response: Response<AuthDTO>
//                ) {
//                    val authResponse = response.body()
//                    if (response.code() == 200 && authResponse?.token != null) {
//                        Log.e("TOKEN: ", authResponse.token)
//                        sessionManager.saveAuthToken(authResponse.token)
//                    } else {
//                        // Log Error
//                    }
//                }
//
//            }
//        )
//    }
//
//    private fun fetchPosts() {
//        apiClient.getApiService(this)
//            .fetchImages(token = "Bearer ${sessionManager.fetchAuthToken()}")
//            .enqueue(object : Callback<PictureListDTO> {
//                override fun onFailure(call: Call<PictureListDTO>, t: Throwable) {
//                    getToken()
//                    Log.e("FAILED FETCHING IMAGES", t.message!!)
//                }
//
//                override fun onResponse(
//                    call: Call<PictureListDTO>,
//                    response: Response<PictureListDTO>
//                ) {
//                    val response = response.body()
//                    response?.pictures?.let { initList(it) }
//                }
//            })
//    }

}
