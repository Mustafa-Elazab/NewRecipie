package com.example.newrecipie.data.local

import androidx.room.*
import com.example.newrecipie.model.Meal
import kotlinx.coroutines.flow.Flow


@Dao
interface FavouriteMealDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteRecipe(meal: Meal)

    @Query("SELECT * FROM favourite_db")
    fun readAllFavouriteRecipes(): Flow<List<Meal>>


    @Delete
    suspend fun deleteFavouriteRecipe(meal: Meal)

    @Query("DELETE FROM favourite_db")
    suspend fun deleteAllFavouriteRecipes()
}