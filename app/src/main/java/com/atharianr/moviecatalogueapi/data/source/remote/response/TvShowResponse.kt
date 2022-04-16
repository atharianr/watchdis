package com.atharianr.moviecatalogueapi.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowResponse<T>(
    @field:SerializedName("results")
    val results: List<T>
)

data class TvShow(
    @field:SerializedName("first_air_date")
    val firstAirDate: String,

    @field:SerializedName("poster_path")
    val posterPath: String,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
)
