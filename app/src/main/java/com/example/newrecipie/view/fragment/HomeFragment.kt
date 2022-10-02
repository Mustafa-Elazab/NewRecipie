package com.example.newrecipie.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.newrecipie.databinding.FragmentHomeBinding
import com.example.newrecipie.model.Category
import com.example.newrecipie.model.Meal
import com.example.newrecipie.model.MealDetails
import com.example.newrecipie.utils.Status
import com.example.newrecipie.utils.hideKeyboard
import com.example.newrecipie.view.adapter.CategoriesAdapter
import com.example.newrecipie.view.adapter.LatestMealsAdapter
import com.example.newrecipie.view.adapter.PopularMealsAdapter
import com.example.newrecipie.view.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding


    val homeViewModel: HomeViewModel by viewModels()

    private lateinit var categoriesList: MutableList<Category>
    private lateinit var categoriesAdapter: CategoriesAdapter

    private lateinit var latestMealsList: MutableList<MealDetails>
    private lateinit var latestMealsAdapter: LatestMealsAdapter

    private lateinit var popularMealsList: MutableList<Meal>
    private lateinit var popularMealsAdapter: PopularMealsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideKeyboard()
        initData()
        getCategories()
        getLatestMeals()
        getPopularMeals()

    }

    private fun getLatestMeals() {
        homeViewModel.getMealsOfCategory(categoryName = "Seafood").observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {

                    Log.i("TAG", "getLatestMeals: Loading")
                }
                Status.SUCCESS -> {

                    latestMealsAdapter.submitList(it.data)
                }
                Status.FAIL -> {
                    Log.i("TAG", "getLatestMeals: Fail")
                }
            }
        }
        initLatestRecycler()
    }

    private fun getPopularMeals() {
        homeViewModel.getMealsOfCategory(categoryName = "Chicken").observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {

                    Log.i("TAG", "getLatestMeals: Loading")
                }
                Status.SUCCESS -> {
                    val data = it.data
                    Log.i("TAG", data.toString())
                    initPopularRecycler(data!!)
                }
                Status.FAIL -> {
                    Log.i("TAG", "getLatestMeals: Fail")
                }
            }
        }

    }

    private fun initPopularRecycler(popularMealList: List<Meal>) {

        popularMealsAdapter =
            PopularMealsAdapter(popularMealList, isHome = true, viewModel = homeViewModel)
        binding.rvPopular.apply {
            adapter = popularMealsAdapter
        }
    }

    private fun initLatestRecycler() {

        binding.rvRandom.apply {
            adapter = latestMealsAdapter


        }
    }

    private fun getCategories() {
        homeViewModel.getCategories().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
//                    binding.categoiresShimmerEffect.startShimmer()
                }
                Status.SUCCESS -> {
                    categoriesAdapter.submitList(it.data)
//                    binding.categoiresShimmerEffect.visibility=View.INVISIBLE
                }
                Status.FAIL -> {

                }
            }
        }
        initCategoriesRecycler()
    }

    private fun initCategoriesRecycler() {
        binding.rvCategories.apply {
            adapter = categoriesAdapter
        }
    }


    private fun initData() {
        categoriesList = mutableListOf()
        categoriesAdapter = CategoriesAdapter()

        latestMealsList = mutableListOf()
        latestMealsAdapter = LatestMealsAdapter(viewModel = homeViewModel)

    }


}