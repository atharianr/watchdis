package com.atharianr.moviecatalogueapi.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atharianr.moviecatalogueapi.data.source.remote.network.ApiConfig
import com.atharianr.moviecatalogueapi.data.source.remote.response.*
import com.atharianr.moviecatalogueapi.data.source.remote.response.vo.ApiResponse
import com.atharianr.moviecatalogueapi.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor() {

    fun getMoviePopular(): LiveData<ApiResponse<List<Movie>>> {
        EspressoIdlingResource.increment()

        val resultResponse = MutableLiveData<ApiResponse<List<Movie>>>()

        ApiConfig.getApiService().getMoviePopular()
            .enqueue(object : Callback<MovieResponse<Movie>> {
                override fun onResponse(
                    call: Call<MovieResponse<Movie>>,
                    response: Response<MovieResponse<Movie>>
                ) {
                    if (response.isSuccessful) {
                        resultResponse.postValue(ApiResponse.success(response.body()?.results as List<Movie>))
                        EspressoIdlingResource.decrement()
                    } else {
                        resultResponse.postValue(
                            ApiResponse.error(
                                response.message().toString(),
                                mutableListOf()
                            )
                        )
                        Log.e("RemoteDataSource", "onFailure: ${response.message()}")
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<MovieResponse<Movie>>, t: Throwable) {
                    resultResponse.postValue(
                        ApiResponse.error(
                            t.message.toString(),
                            mutableListOf()
                        )
                    )
                    Log.e("RemoteDataSource", "onFailure: ${t.message.toString()}")
                    EspressoIdlingResource.decrement()
                }
            })

        return resultResponse
    }

    fun getMovieDetail(movieId: String): LiveData<ApiResponse<MovieDetailResponse>> {
        EspressoIdlingResource.increment()

        val resultResponse = MutableLiveData<ApiResponse<MovieDetailResponse>>()

        ApiConfig.getApiService().getMovieDetail(movieId)
            .enqueue(object : Callback<MovieDetailResponse> {
                override fun onResponse(
                    call: Call<MovieDetailResponse>,
                    response: Response<MovieDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        resultResponse.postValue(ApiResponse.success(response.body() as MovieDetailResponse))
                        EspressoIdlingResource.decrement()
                    } else {
                        Log.e("RemoteDataSource", "onFailure: ${response.message()}")
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                    Log.e("RemoteDataSource", "onFailure: ${t.message.toString()}")
                    EspressoIdlingResource.decrement()
                }
            })

        return resultResponse
    }

    fun getTvShowPopular(): LiveData<ApiResponse<List<TvShow>>> {
        EspressoIdlingResource.increment()

        val resultResponse = MutableLiveData<ApiResponse<List<TvShow>>>()

        ApiConfig.getApiService().getTvShowPopular()
            .enqueue(object : Callback<TvShowResponse<TvShow>> {
                override fun onResponse(
                    call: Call<TvShowResponse<TvShow>>,
                    response: Response<TvShowResponse<TvShow>>
                ) {
                    if (response.isSuccessful) {
                        resultResponse.postValue(ApiResponse.success(response.body()?.results as List<TvShow>))
                        EspressoIdlingResource.decrement()
                    } else {
                        resultResponse.postValue(
                            ApiResponse.error(
                                response.message().toString(),
                                mutableListOf()
                            )
                        )
                        Log.e("RemoteDataSource", "onFailure: ${response.message()}")
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<TvShowResponse<TvShow>>, t: Throwable) {
                    resultResponse.postValue(
                        ApiResponse.error(
                            t.message.toString(),
                            mutableListOf()
                        )
                    )
                    Log.e("RemoteDataSource", "onFailure: ${t.message.toString()}")
                    EspressoIdlingResource.decrement()
                }
            })

        return resultResponse
    }

    fun getTvShowDetail(tvShowId: String): LiveData<ApiResponse<TvShowDetailResponse>> {
        EspressoIdlingResource.increment()

        val resultResponse = MutableLiveData<ApiResponse<TvShowDetailResponse>>()

        ApiConfig.getApiService().getTvShowDetail(tvShowId)
            .enqueue(object : Callback<TvShowDetailResponse> {
                override fun onResponse(
                    call: Call<TvShowDetailResponse>,
                    response: Response<TvShowDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        resultResponse.postValue(ApiResponse.success(response.body() as TvShowDetailResponse))
                        EspressoIdlingResource.decrement()
                    } else {
                        Log.e("RemoteDataSource", "onFailure: ${response.message()}")
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<TvShowDetailResponse>, t: Throwable) {
                    Log.e("RemoteDataSource", "onFailure: ${t.message.toString()}")
                    EspressoIdlingResource.decrement()
                }
            })

        return resultResponse
    }
}