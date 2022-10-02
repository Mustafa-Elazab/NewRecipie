package com.example.newrecipie.data.repository

import android.util.Log
import androidx.lifecycle.liveData
import androidx.room.withTransaction
import com.example.newrecipie.data.local.FavouriteMealDao
import com.example.newrecipie.data.local.MealDao
import com.example.newrecipie.data.local.MealDataBase
import com.example.newrecipie.data.remote.ApiServices
import com.example.newrecipie.model.Meal
import com.example.newrecipie.model.MealsDetailsList
import com.example.newrecipie.utils.Resource
import com.example.newrecipie.utils.networkBoundResource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@ExperimentalCoroutinesApi
@ActivityRetainedScoped
class MealRepository @Inject constructor(
    private val apiService: ApiServices,
    private val database: MealDataBase,
    private val favouriteMealDao: FavouriteMealDao
) {

    private val mealDao = database.getMealDao()

    fun getLatestMeals() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getLatestMeals()
            if (response.isSuccessful) {
                emit(Resource.success(data = response.body()?.mealsList))
            }

        } catch (exception: Exception) {
            Resource.failed<MealsDetailsList>(null, exception.message.toString())
        }
    }

    fun getCategories() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiService.getCategories()
            if (response.isSuccessful) {
                emit(Resource.success(response.body()?.categories))
            }
        } catch (exception: Exception) {
            Resource.failed<MealsDetailsList>(null, exception.message.toString())
        }
    }

    fun getMealOfCategory(categoryName: String) = networkBoundResource(

        query = {
            mealDao.readAllRecipes()
        },
        fetch = {
            delay(1000)
            apiService.getMealsByCategory(categoryName = categoryName)
        },

        saveFetchResult = {
           database.withTransaction {
               mealDao.deleteAllRecipes()
               mealDao.insertRecipe(meals = it.body()!!.meals)
           }
        },

        )

    fun getSearchedMeals(searchedText: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val data = apiService.getSearchedMeals(searchedText).body()?.mealsList
            Log.i("Tag", data.toString())
            emit(Resource.success(data))

        } catch (exception: Exception) {
            Resource.failed(data = null, exception.message.toString())
        }
    }

    fun getMealDetails(mealId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val meal = apiService.getMealDetails(mealId).body()
            emit(Resource.success(meal!!.mealsList))
        } catch (exception: Exception) {
            emit(Resource.failed(null, exception.message.toString()))
        }
    }


    fun readAllFavouriteRecipes(): Flow<List<Meal>> {
        return favouriteMealDao.readAllFavouriteRecipes()
    }

    suspend fun insertFavouriteRecipes(favouritesRecipe: Meal) {
        favouriteMealDao.insertFavouriteRecipe(favouritesRecipe)
    }

    suspend fun deleteFavouriteRecipe(favouritesRecipe: Meal) {
        favouriteMealDao.deleteFavouriteRecipe(favouritesRecipe)
    }

    suspend fun deleteAllFavouriteRecipes() {
        favouriteMealDao.deleteAllFavouriteRecipes()
    }

    fun readAllRecipes(): Flow<List<Meal>> {
        return mealDao.readAllRecipes()
    }

    suspend fun insertRecipes(recipes: List<Meal>) {
        mealDao.insertRecipe(meals = recipes)
    }


    suspend fun deleteAllRecipes() {
        mealDao.deleteAllRecipes()
    }

}