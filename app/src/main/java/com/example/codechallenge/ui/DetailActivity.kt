package com.example.codechallenge.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.codechallenge.repository.ApiClient
import com.example.codechallenge.R
import com.example.codechallenge.SessionManager
import com.example.codechallenge.model.DetailDTO
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class DetailActivity : AppCompatActivity() {

    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        getInconmingIntent()
    }

    private fun getInconmingIntent() {
        val imageUrl = intent.extras?.getString("IMAGE_URL", "0")
        imageUrl?.let { fetchImageDetail(it) }
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

    private fun fetchImageDetail(imageId: String) {
        apiClient.getApiService(this)
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
                        setContent(response)
                    }
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