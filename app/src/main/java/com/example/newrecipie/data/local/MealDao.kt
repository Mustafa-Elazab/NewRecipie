package com.example.newrecipie.data.local

import androidx.room.*
import com.example.newrecipie.model.Meal
import kotlinx.coroutines.flow.Flow


@Dao
interface MealDao {




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(meals: List<Meal>)

    @Query("SELECT * FROM favourite_db")
    fun readAllRecipes(): Flow<List<Meal>>


    @Query("DELETE FROM favourite_db")
    suspend fun deleteAllRecipes()
}