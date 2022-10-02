package com.example.newrecipie.di

import android.content.Context
import androidx.room.Room
import com.example.newrecipie.data.local.FavouriteMealDataBase
import com.example.newrecipie.data.local.MealDataBase
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
    fun provideFavouriteMealDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        FavouriteMealDataBase::class.java,
        "favourite_db"
    ).build()

    @Singleton
    @Provides
    fun provideMealDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        MealDataBase::class.java,
        "meal_db"
    ).build()


    @Singleton
    @Provides
    fun provideFavouriteDao(database: FavouriteMealDataBase) = database.getFavouriteMealDao()


    @Singleton
    @Provides
    fun provideDao(database: MealDataBase) = database.getMealDao()


}