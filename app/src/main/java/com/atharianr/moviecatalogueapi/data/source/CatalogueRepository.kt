package com.atharianr.moviecatalogueapi.data.source

import androidx.lifecycle.LiveData
import com.atharianr.moviecatalogueapi.data.source.local.LocalDataSource
import com.atharianr.moviecatalogueapi.data.source.local.entity.DetailEntity
import com.atharianr.moviecatalogueapi.data.source.local.entity.MovieEntity
import com.atharianr.moviecatalogueapi.data.source.local.entity.TvShowEntity
import com.atharianr.moviecatalogueapi.data.source.remote.RemoteDataSource
import com.atharianr.moviecatalogueapi.data.source.remote.response.Movie
import com.atharianr.moviecatalogueapi.data.source.remote.response.MovieDetailResponse
import com.atharianr.moviecatalogueapi.data.source.remote.response.TvShow
import com.atharianr.moviecatalogueapi.data.source.remote.response.TvShowDetailResponse
import com.atharianr.moviecatalogueapi.data.source.remote.response.vo.ApiResponse
import com.atharianr.moviecatalogueapi.utils.AppExecutors
import com.atharianr.moviecatalogueapi.utils.Constant
import com.atharianr.moviecatalogueapi.vo.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatalogueRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    CatalogueDataSource {

    override fun getMoviePopular(): LiveData<Resource<List<MovieEntity>>> {

        return object : NetworkBoundResource<List<MovieEntity>, List<Movie>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<MovieEntity>> = localDataSource.getMovies()

            override fun shouldFetch(data: List<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<Movie>>> =
                remoteDataSource.getMoviePopular()

            override fun saveCallResult(data: List<Movie>) {
                val movies = ArrayList<MovieEntity>()

                for (response in data) {
                    val movie = MovieEntity(
                        response.id,
                        response.posterPath,
                        response.title,
                        response.releaseDate,
                        response.voteAverage,
                    )
                    movies.add(movie)
                }

                localDataSource.insertMovies(movies)
            }
        }.asLiveData()
    }

    override fun getTvShowPopular(): LiveData<Resource<List<TvShowEntity>>> {

        return object : NetworkBoundResource<List<TvShowEntity>, List<TvShow>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<TvShowEntity>> = localDataSource.getTvShows()

            override fun shouldFetch(data: List<TvShowEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvShow>>> =
                remoteDataSource.getTvShowPopular()

            override fun saveCallResult(data: List<TvShow>) {
                val tvShows = ArrayList<TvShowEntity>()

                for (response in data) {
                    val tvShow = TvShowEntity(
                        response.id,
                        response.posterPath,
                        response.name,
                        response.firstAirDate,
                        response.voteAverage,
                    )
                    tvShows.add(tvShow)
                }

                localDataSource.insertTvShows(tvShows)
            }
        }.asLiveData()
    }

    override fun getMovieDetail(movieId: String): LiveData<Resource<DetailEntity>> {
        return object : NetworkBoundResource<DetailEntity, MovieDetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<DetailEntity> =
                localDataSource.getDetail(movieId.toInt())

            override fun shouldFetch(data: DetailEntity?): Boolean = data == null

            override fun createCall(): LiveData<ApiResponse<MovieDetailResponse>> =
                remoteDataSource.getMovieDetail(movieId)

            override fun saveCallResult(data: MovieDetailResponse) {
                val listGenres = ArrayList<String>()

                for (genre in data.genres) {
                    listGenres.add(genre.name)
                }

                val detail = DetailEntity(
                    data.id,
                    data.title,
                    data.overview,
                    listGenres.toString(),
                    data.voteAverage,
                    data.releaseDate,
                    data.runtime,
                    data.status,
                    data.posterPath,
                    false,
                    Constant.TYPE_MOVIE
                )

                localDataSource.insertDetail(detail)
            }
        }.asLiveData()
    }

    override fun getTvShowDetail(tvShowId: String): LiveData<Resource<DetailEntity>> {
        return object : NetworkBoundResource<DetailEntity, TvShowDetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<DetailEntity> =
                localDataSource.getDetail(tvShowId.toInt())

            override fun shouldFetch(data: DetailEntity?): Boolean = data == null

            override fun createCall(): LiveData<ApiResponse<TvShowDetailResponse>> =
                remoteDataSource.getTvShowDetail(tvShowId)

            override fun saveCallResult(data: TvShowDetailResponse) {
                val listGenres = ArrayList<String>()

                for (genre in data.genres) {
                    listGenres.add(genre.name)
                }

                val episodeRunTime = if (data.episodeRunTime.isNotEmpty()) {
                    data.episodeRunTime[0]
                } else {
                    0
                }

                val genre = if (listGenres.isNotEmpty()) {
                    listGenres.toString()
                } else {
                    "[Unknown]"
                }

                val detail = DetailEntity(
                    data.id,
                    data.name,
                    data.overview,
                    genre,
                    data.voteAverage,
                    data.firstAirDate,
                    episodeRunTime,
                    data.status,
                    data.posterPath,
                    false,
                    Constant.TYPE_TV_SHOW
                )

                localDataSource.insertDetail(detail)
            }
        }.asLiveData()
    }

    override fun setFavorite(detailEntity: DetailEntity, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavorite(detailEntity, state) }
    }

    override fun getFavoriteMovies(): LiveData<List<DetailEntity>> =
        localDataSource.getFavoriteMovies()

    override fun getFavoriteTvShows(): LiveData<List<DetailEntity>> =
        localDataSource.getFavoriteTvShows()
}