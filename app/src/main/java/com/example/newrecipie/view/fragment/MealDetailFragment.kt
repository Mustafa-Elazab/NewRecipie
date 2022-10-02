package com.example.newrecipie.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.newrecipie.R
import com.example.newrecipie.databinding.FragmentMealDetailBinding
import com.example.newrecipie.model.Meal
import com.example.newrecipie.model.MealDetails
import com.example.newrecipie.utils.Status
import com.example.newrecipie.utils.hideKeyboard
import com.example.newrecipie.view.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MealDetailFragment : Fragment() {


    lateinit var binding: FragmentMealDetailBinding
    val viewModel: HomeViewModel by viewModels()

    lateinit var meal: MealDetails
    lateinit var mealData: Meal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMealDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideKeyboard()

        var mealId = arguments?.getString("meal_id")
        binding.btnBack.setOnClickListener {

            var action =
                MealDetailFragmentDirections.actionMealDetailFragmentToHomeFragment()
            Navigation.findNavController(it).navigate(action)
        }

        getMealDetail(mealId!!)



        binding.btnFavourite.setOnClickListener {


            if (mealData.isLike) {
                binding.btnFavourite.setImageResource(R.drawable.ic_favorite_fill)
            } else {
                binding.btnFavourite.setImageResource(R.drawable.ic_heart)
            }


            viewModel.insertFavouriteRecipe(favouritesRecipe = mealData)
            Log.i("TAG", "setValu:Saved in Room")

        }




    }


    private fun getMealDetail(mealId: String) {

        viewModel.getMealDetails(mealId = mealId).observe(viewLifecycleOwner) {


            when (it.status) {

                Status.FAIL -> {
                    Log.i("TAG", "getMealDetail: Fail")
                }
                Status.LOADING -> {
                    Log.i("TAG", "getMealDetail: Loading")
                }
                Status.SUCCESS -> {
                    Log.i("TAG", "getMealDetail: SUCCESS")
                    meal = it.data!![0]
                    setupVariables(meal)
                    openLink(meal)
                    openYoutube(meal)

                    initValMeal(meal)

                }

            }


        }

    }

    private fun initValMeal(meal: MealDetails) {
        mealData = Meal(
            idMeal = meal.idMeal,
            strMeal = meal.strMeal,
            strMealThumb = meal.strMealThumb,
            isLike = true
        )

    }


    private fun putIngredient(gradients: String) {
        binding.ingredient.text = gradients
    }

    private fun getIngredient(ingredients: MutableList<String>) {
        val ingredientText = StringBuilder()
        for (ingredient: String in ingredients) {
            if (ingredient != " " && ingredient.isNotEmpty()) {
                ingredientText.append("\n \u2022$ingredient")
            }
            putIngredient(ingredientText.toString())
        }
    }

    private fun addIngredientToIngredients(meal: MealDetails) {
        val ingredients = mutableListOf<String>()
        ingredients.add(meal.strIngredient1)
        ingredients.add(meal.strIngredient2)
        ingredients.add(meal.strIngredient3)
        ingredients.add(meal.strIngredient4)
        ingredients.add(meal.strIngredient5)
        ingredients.add(meal.strIngredient6)
        ingredients.add(meal.strIngredient7)
        ingredients.add(meal.strIngredient8)
        ingredients.add(meal.strIngredient9)
        ingredients.add(meal.strIngredient10)
        ingredients.add(meal.strIngredient11)
        ingredients.add(meal.strIngredient12)
        ingredients.add(meal.strIngredient13)
        ingredients.add(meal.strIngredient14)
        ingredients.add(meal.strIngredient15)
        ingredients.add(meal.strIngredient16)
        ingredients.add(meal.strIngredient17)
        ingredients.add(meal.strIngredient18)
        ingredients.add(meal.strIngredient19)
        ingredients.add(meal.strIngredient20)

        getIngredient(ingredients)
    }

    private fun addMeasures(meal: MealDetails) {
        val measures = mutableListOf<String>()
        measures.add(meal.strMeasure1)
        measures.add(meal.strMeasure2)
        measures.add(meal.strMeasure3)
        measures.add(meal.strMeasure4)
        measures.add(meal.strMeasure5)
        measures.add(meal.strMeasure6)
        measures.add(meal.strMeasure7)
        measures.add(meal.strMeasure8)
        measures.add(meal.strMeasure9)
        measures.add(meal.strMeasure10)
        measures.add(meal.strMeasure11)
        measures.add(meal.strMeasure12)
        measures.add(meal.strMeasure13)
        measures.add(meal.strMeasure14)
        measures.add(meal.strMeasure15)
        measures.add(meal.strMeasure16)
        measures.add(meal.strMeasure17)
        measures.add(meal.strMeasure18)
        measures.add(meal.strMeasure19)
        measures.add(meal.strMeasure20)

        getMeasures(measures)
    }

    private fun getMeasures(measuresList: MutableList<String>) {
        val measureText = StringBuilder()
        for (measure: String in measuresList) {
            if (measure != " " && measure.isNotEmpty()) {
                Log.d("TAG", "getMeasures: $measure")
                measureText.append("\n \u2022$measure")
            }
            putMeasuresText(measureText.toString())
        }
    }

    private fun putMeasuresText(measureText: String) {
        binding.measure.text = measureText
    }

    private fun setupVariables(meal: MealDetails) {

        binding.apply {

            tvMealName.text = meal.strMeal
            tvCategoryName.text = meal.strCategory
            tvCountryName.text = meal.strArea
            tvInstructions.text = meal.strInstructions
            Glide.with(requireContext()).load(meal.strMealThumb).into(imgMeal)
        }


        addIngredientToIngredients(meal)
        addMeasures(meal)
    }

    private fun openLink(meal: MealDetails) {
        binding.source.setOnClickListener {
            implicitIntent(meal.strSource)
        }
    }

    private fun openYoutube(meal: MealDetails) {
        binding.youtube.setOnClickListener {
            implicitIntent(meal.strYoutube)
        }
    }

    private fun implicitIntent(uri: String) {
        val implicitIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        activity?.startActivity(implicitIntent)
    }


}