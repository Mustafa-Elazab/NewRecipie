package com.example.newrecipie.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.newrecipie.databinding.FragmentCategoryItemsBinding
import com.example.newrecipie.model.Meal
import com.example.newrecipie.utils.Status
import com.example.newrecipie.view.adapter.CategoryItemAdapter
import com.example.newrecipie.view.adapter.PopularMealsAdapter
import com.example.newrecipie.view.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CategoryItemsFragment : Fragment() {

    lateinit var binding: FragmentCategoryItemsBinding
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var categoryItemMealsAdapter: CategoryItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentCategoryItemsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryName = arguments?.getString("category_name")

        binding.tvCategoryName.text = categoryName

        getCategoryItems(categoryName = categoryName!!)

        binding.btnBack.setOnClickListener {

            var action = CategoryItemsFragmentDirections.actionCategoryItemsFragmentToHomeFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun getCategoryItems(categoryName: String) {

        viewModel.getMealsOfCategory(categoryName = categoryName).observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {

                    Log.i("TAG", "getLatestMeals: Loading")
                }
                Status.SUCCESS -> {

                    val data = it.data
                    Log.i("TAG", data.toString())
                    initCategoryItemRecycler(data!!)
                }
                Status.FAIL -> {
                    Log.i("TAG", "getLatestMeals: Fail")
                }
            }
        }

    }

    private fun initCategoryItemRecycler(data: List<Meal>) {

        categoryItemMealsAdapter = CategoryItemAdapter(data)
        binding.rvCategoryItems.apply {


            adapter = categoryItemMealsAdapter

        }

    }


}