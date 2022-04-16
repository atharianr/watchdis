package com.atharianr.moviecatalogueapi.di

import com.atharianr.moviecatalogueapi.data.source.CatalogueRepository
import com.atharianr.moviecatalogueapi.data.source.local.LocalDataSource
import com.atharianr.moviecatalogueapi.data.source.local.room.CatalogueDao
import com.atharianr.moviecatalogueapi.data.source.remote.RemoteDataSource
import com.atharianr.moviecatalogueapi.utils.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource() = RemoteDataSource()

    @Singleton
    @Provides
    fun provideLocalDataSource(catalogueDao: CatalogueDao) = LocalDataSource(catalogueDao)

    @Provides
    fun provideCatalogueRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        appExecutors: AppExecutors
    ) = CatalogueRepository(remoteDataSource, localDataSource, appExecutors)
}