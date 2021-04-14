package com.example.codechallenge.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
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
import com.example.codechallenge.util.Constants.CACHE_KEY
import com.example.codechallenge.util.SessionManager
import com.example.codechallenge.viewmodel.MainViewModel
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
        viewModel.load(apiService, sessionManager)
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
            loading.visibility = View.GONE
            loading.pauseAnimation()
            initList(it)
        })
    }

    private fun initList(imageList: List<PictureDTO>) {
        val adapter =
            PictureAdapter(imageList, this)
        list.adapter = adapter
    }

    override fun onItemClick(item: PictureDTO, position: Int) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(CACHE_KEY, item.id)
        }
        startActivity(intent)
        overridePendingTransition(
            R.anim.slide_from_right,
            R.anim.slide_from_left
        )
    }
}
