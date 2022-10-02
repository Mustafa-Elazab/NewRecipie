package com.example.newrecipie.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newrecipie.databinding.FragmentFavouriteBinding
import com.example.newrecipie.model.Meal
import com.example.newrecipie.model.MealDetails
import com.example.newrecipie.utils.hideKeyboard
import com.example.newrecipie.view.adapter.FavouriteAdapter
import com.example.newrecipie.view.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FavouriteFragment : Fragment() {


    lateinit var binding: FragmentFavouriteBinding
    val viewModel: HomeViewModel by viewModels()
    private lateinit var favouriteAdapter: FavouriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideKeyboard()

        readDatabase()


        binding.imgDelete.setOnClickListener {
            viewModel.deleteAllFavouriteRecipes()
            showSnackBar()
            var action = FavouriteFragmentDirections.actionFavouriteFragmentToHomeFragment()
            Navigation.findNavController(it).navigate(action)
        }



    }
    private fun showSnackBar() {
        Snackbar.make(binding.root, "All recipes removed.", Snackbar.LENGTH_SHORT)
            .setAction("Okay") {}
            .show()
    }



    private fun readDatabase() {
        lifecycleScope.launch {
            viewModel.readAllFavouriteRecipes.observe(viewLifecycleOwner) { database ->

                if (database.isEmpty()) {
                    Log.i("TAG", "readDatabase: Data is Empty")
                } else {
                    Log.i("TAG", "readDatabase: $database")

                    initRecyclerView(database)

                }

            }
        }
    }

    private fun initRecyclerView(database: List<Meal>?) {

        favouriteAdapter = FavouriteAdapter(viewModel,database!!)

        binding.rvFavourite.apply {
            adapter = favouriteAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


}