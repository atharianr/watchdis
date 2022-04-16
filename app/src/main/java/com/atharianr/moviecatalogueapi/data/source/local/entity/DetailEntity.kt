package com.atharianr.moviecatalogueapi.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "detail_entities")
data class DetailEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String? = "",

    @ColumnInfo(name = "overview")
    val overview: String? = "",

    @ColumnInfo(name = "genre")
    val genre: String? = "",

    @ColumnInfo(name = "score")
    val score: Double? = null,

    @ColumnInfo(name = "release_date")
    val releaseDate: String? = "",

    @ColumnInfo(name = "runtime")
    val runtime: Int? = null,

    @ColumnInfo(name = "status")
    val status: String? = "",

    @ColumnInfo(name = "poster_path")
    val poster: String? = "",

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false,

    @ColumnInfo(name = "type")
    val type: Int? = null
) : Parcelable
