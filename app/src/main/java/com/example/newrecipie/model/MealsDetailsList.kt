package com.example.newrecipie.model

import com.google.gson.annotations.SerializedName

data class MealsDetailsList(
    @SerializedName("meals")
    val mealsList: MutableList<MealDetails>
)
