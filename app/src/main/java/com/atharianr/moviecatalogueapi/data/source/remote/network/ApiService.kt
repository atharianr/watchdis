package com.atharianr.moviecatalogueapi.data.source.remote.network

import com.atharianr.moviecatalogueapi.BuildConfig
import com.atharianr.moviecatalogueapi.data.source.remote.response.*
import com.atharianr.moviecatalogueapi.utils.Constant
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getMoviePopular(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API,
        @Query("language") language: String = Constant.LANG,
        @Query("page") page: Int = 1
    ): Call<MovieResponse<Movie>>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API,
        @Query("language") language: String = Constant.LANG,
    ): Call<MovieDetailResponse>

    @GET("tv/popular")
    fun getTvShowPopular(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API,
        @Query("language") language: String = Constant.LANG,
        @Query("page") page: Int = 1
    ): Call<TvShowResponse<TvShow>>

    @GET("tv/{tv_id}")
    fun getTvShowDetail(
        @Path("tv_id") tvShowId: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API,
        @Query("language") language: String = Constant.LANG,
    ): Call<TvShowDetailResponse>
}