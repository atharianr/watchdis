package com.atharianr.moviecatalogueapi.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GenresItem(
    @field:SerializedName("name")
    val name: String
)