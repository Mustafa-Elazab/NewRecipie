package com.example.newrecipie.data.remote

import com.example.newrecipie.model.Categories
import com.example.newrecipie.model.Meals
import com.example.newrecipie.model.MealsDetailsList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiServices {
    companion object {
        const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
    }



     // latest is paid api so ues this as a last meal   @GET("latest.php")
     @GET("search.php?f=s")
     suspend fun getLatestMeals(): Response<MealsDetailsList>

    @GET("categories.php")
    suspend fun getCategories(): Response<Categories>

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") categoryName: String): Response<Meals>

    @GET("search.php")
    suspend fun getSearchedMeals(@Query("s") searchedText: String): Response<MealsDetailsList>

    @GET("lookup.php")
    suspend fun getMealDetails(@Query("i") mealId:String) : Response<MealsDetailsList>

}