package com.atharianr.moviecatalogueapi.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.atharianr.moviecatalogueapi.data.source.CatalogueRepository
import com.atharianr.moviecatalogueapi.data.source.local.entity.DetailEntity
import com.atharianr.moviecatalogueapi.utils.Constant
import com.atharianr.moviecatalogueapi.vo.Resource

class DetailViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {

    private lateinit var detailData: LiveData<Resource<DetailEntity>>

    fun setType(id: String, type: Int) {
        when (type) {
            Constant.TYPE_MOVIE -> {
                detailData = catalogueRepository.getMovieDetail(id)
            }

            Constant.TYPE_TV_SHOW -> {
                detailData = catalogueRepository.getTvShowDetail(id)
            }
        }
    }

    fun getMovieDetail(): LiveData<Resource<DetailEntity>> = detailData

    fun setFavorite(detailEntity: DetailEntity) {
        val newState = !detailEntity.favorite
        catalogueRepository.setFavorite(detailEntity, newState)
    }
}