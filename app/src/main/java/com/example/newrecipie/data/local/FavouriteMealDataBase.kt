package com.example.newrecipie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newrecipie.model.Meal

@Database(entities = [Meal::class], version = 1, exportSchema = false)
abstract class FavouriteMealDataBase : RoomDatabase() {

    abstract fun getFavouriteMealDao(): FavouriteMealDao


}