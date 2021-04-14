package com.example.codechallenge.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.codechallenge.R
import com.example.codechallenge.model.DetailDTO
import com.example.codechallenge.repository.ApiClient
import com.example.codechallenge.repository.ApiService
import com.example.codechallenge.util.Constants.CACHE_KEY
import com.example.codechallenge.util.SessionManager
import com.example.codechallenge.viewmodel.DetailViewModel
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private lateinit var apiService: ApiService
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        viewModel =
            ViewModelProvider(
                this
            ).get(DetailViewModel::class.java)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        apiClient = ApiClient()
        apiService = apiClient.getApiService(this)
        sessionManager = SessionManager(this)
        setDetailObserver()
        getInconmingIntent()
    }

    private fun showHint() {
        Toast.makeText(this, getString(R.string.detail_hint), Toast.LENGTH_SHORT).show()
    }

    private fun getInconmingIntent() {
        val imageUrl = intent.extras?.getString(CACHE_KEY, "0")
        imageUrl?.let {
            viewModel.fetchImageDetail(apiService, it)
        }
    }

    private fun setDetailObserver() {
        viewModel.detail.observe(this, Observer {
            setContent(it)
        })
    }

    private fun setImageFromUrl(image: String) {
        val defaultOptions =
            DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build()
        val config =
            ImageLoaderConfiguration.Builder(this).defaultDisplayImageOptions(defaultOptions)
                .build()
        val imageLoader = ImageLoader.getInstance()
        imageLoader.init(config)
        imageLoader.loadImage(image, object : SimpleImageLoadingListener() {
            override fun onLoadingComplete(
                imageUri: String,
                view: View?,
                loadedImage: Bitmap
            ) {
                showHint()
                loading.pauseAnimation()
                loading.visibility = View.GONE
                imageView.setImageBitmap(loadedImage)
                imageView.setZoomable(true)
                fadeIn(imageView)
            }
        })
    }

    private fun fadeIn(view: View) {
        view.animate().apply {
            alpha(1f)
            duration = 500
        }.start()
    }

    private fun setContent(response: DetailDTO) {
        with(response) {
            setImageFromUrl(full_picture)
            setItemClickListener()
            name.text = author
            model.text = camera
        }
    }

    private fun DetailDTO.setItemClickListener() {
        share_button.setOnClickListener {
            val clipboard: ClipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("image", full_picture)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this@DetailActivity, "Link Copied!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            R.anim.slide_from_right,
            R.anim.slide_from_left
        )
    }
}
