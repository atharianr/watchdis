package com.atharianr.moviecatalogueapi.di

import android.content.Context
import androidx.room.Room
import com.atharianr.moviecatalogueapi.data.source.local.room.CatalogueDao
import com.atharianr.moviecatalogueapi.data.source.local.room.CatalogueDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CatalogueDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            CatalogueDatabase::class.java,
            "Catalogue.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideDao(database: CatalogueDatabase): CatalogueDao = database.movieDao()
}