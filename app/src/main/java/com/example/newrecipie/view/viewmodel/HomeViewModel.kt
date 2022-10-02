package com.example.newrecipie.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.newrecipie.data.repository.MealRepository
import com.example.newrecipie.model.Meal
import com.example.newrecipie.model.MealDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {


    fun getLatest() = repository.getLatestMeals()

    fun getCategories() = repository.getCategories()


    fun getMealsOfCategory(categoryName: String) = repository.getMealOfCategory(categoryName)

    fun getSearchedMeals(searchedTxt: String) = repository.getSearchedMeals(searchedTxt)

    fun getMealDetails(mealId: String) = repository.getMealDetails(mealId)

    val readAllFavouriteRecipes: LiveData<List<Meal>> =
        repository.readAllFavouriteRecipes().asLiveData()

    fun insertFavouriteRecipe(favouritesRecipe: Meal) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavouriteRecipes(favouritesRecipe)
        }
    fun deleteFavouriteRecipe(favouritesRecipe: Meal) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavouriteRecipe(favouritesRecipe)
        }

    fun deleteAllFavouriteRecipes() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllFavouriteRecipes()
        }
}