package com.example.newrecipie.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.newrecipie.databinding.FragmentSearchBinding
import com.example.newrecipie.model.Meal
import com.example.newrecipie.utils.Status
import com.example.newrecipie.utils.hide
import com.example.newrecipie.utils.show
import com.example.newrecipie.utils.showKeyboard
import com.example.newrecipie.view.adapter.SearchAdapter
import com.example.newrecipie.view.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
@ExperimentalCoroutinesApi
class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    val viewModel: HomeViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initVars()
        searchAction()
        initRecyclerView()


    }

    private fun initVars() {
        showKeyboard(requireContext(), binding.edtSearch)
        searchAdapter = SearchAdapter(requireContext(),viewModel)
    }

    private fun searchAction() {
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                viewModel.getSearchedMeals(p0.toString()).observe(viewLifecycleOwner) {
                    when (it.status) {
                        Status.LOADING -> {
                            binding.progressBar.show()
                            binding.view.show()
                        }
                        Status.SUCCESS -> {
                            val mealsList = it.data

                            searchAdapter.submitList(mealsList)
                            binding.progressBar.hide()
                            binding.view.hide()
                        }
                        Status.FAIL -> {
                        }
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvSearchItem.apply {
            setHasFixedSize(true)
            adapter = searchAdapter
        }
    }
}