package com.atharianr.moviecatalogueapi.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.atharianr.moviecatalogueapi.data.source.CatalogueRepository
import com.atharianr.moviecatalogueapi.data.source.local.entity.DetailEntity
import com.atharianr.moviecatalogueapi.utils.Constant

class FavoriteViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {

    private lateinit var favoriteData: LiveData<List<DetailEntity>>

    fun setType(type: Int) {
        when (type) {
            Constant.TYPE_MOVIE -> {
                favoriteData = catalogueRepository.getFavoriteMovies()
            }

            Constant.TYPE_TV_SHOW -> {
                favoriteData = catalogueRepository.getFavoriteTvShows()
            }
        }
    }

    fun getFavorite(): LiveData<List<DetailEntity>> = favoriteData
}