//package com.example.codechallenge
//
//import androidx.paging.PagingSource
//import com.example.codechallenge.model.Model
//import com.example.codechallenge.model.ModelResponse
//import com.example.codechallenge.repository.ApiService
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class AgilePagingSource (
//    private val apiService: ApiService
//) : PagingSource<Int, Model>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Model> {
//        val position = params.key ?: STARTING_INDEX
//        val response = apiService.loadImages(position).enqueue(object : Callback<ModelResponse> {
//            override fun onFailure(call: Call<ModelResponse>, t: Throwable) {}
//            override fun onResponse(
//                call: Call<ModelResponse>,
//                response: Response<ModelResponse>
//            ) {
//
//                val response = response.body()
////                response?.pictures?
//            }
//        })
//        return LoadRes
//    }
//
//    companion object {
//        private const val STARTING_INDEX = 1
//    }
//
//}