package com.example.codechallenge.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.codechallenge.DetailViewModel
import com.example.codechallenge.repository.ApiClient
import com.example.codechallenge.R
import com.example.codechallenge.SessionManager
import com.example.codechallenge.model.DetailDTO
import com.example.codechallenge.repository.ApiService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


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

    private fun getInconmingIntent() {
        val imageUrl = intent.extras?.getString("IMAGE_URL", "0")
        imageUrl?.let {
            viewModel.fetchImageDetail(apiService, sessionManager, it)
        }
    }

    private fun setDetailObserver() {
        viewModel.detail.observe(this, Observer {
            setContent(it)
        })
    }

    private fun setImageFromUrl(image: String) {
        Picasso.get().load(image).into(imageView, object : com.squareup.picasso.Callback {
            override fun onSuccess() {
                loading.pauseAnimation()
                loading.visibility = View.GONE
            }

            override fun onError(e: Exception?) {
                TODO("Not yet implemented")
            }

        })
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