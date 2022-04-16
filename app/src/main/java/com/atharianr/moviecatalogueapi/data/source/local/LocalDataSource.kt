package com.atharianr.moviecatalogueapi.data.source.local

import androidx.lifecycle.LiveData
import com.atharianr.moviecatalogueapi.data.source.local.entity.DetailEntity
import com.atharianr.moviecatalogueapi.data.source.local.entity.MovieEntity
import com.atharianr.moviecatalogueapi.data.source.local.entity.TvShowEntity
import com.atharianr.moviecatalogueapi.data.source.local.room.CatalogueDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val mCatalogueDao: CatalogueDao) {
    fun insertMovies(movies: List<MovieEntity>) = mCatalogueDao.insertMovies(movies)

    fun getMovies(): LiveData<List<MovieEntity>> = mCatalogueDao.getMovies()

    fun getFavoriteMovies(): LiveData<List<DetailEntity>> = mCatalogueDao.getFavoriteMovies()

    fun insertTvShows(tvShows: List<TvShowEntity>) = mCatalogueDao.insertTvShows(tvShows)

    fun getTvShows(): LiveData<List<TvShowEntity>> = mCatalogueDao.getTvShows()

    fun getFavoriteTvShows(): LiveData<List<DetailEntity>> = mCatalogueDao.getFavoriteTvShows()

    fun insertDetail(detail: DetailEntity) = mCatalogueDao.insertDetail(detail)

    fun getDetail(id: Int): LiveData<DetailEntity> = mCatalogueDao.getDetailById(id)

    fun setFavorite(detail: DetailEntity, newState: Boolean) {
        detail.favorite = newState
        mCatalogueDao.updateDetail(detail)
    }
}