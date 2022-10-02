package com.example.newrecipie.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_db")
data class Meal(
    @PrimaryKey(autoGenerate = false)
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    var isLike: Boolean = false
)