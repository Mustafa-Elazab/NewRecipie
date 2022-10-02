package com.example.newrecipie.view.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newrecipie.R
import com.example.newrecipie.databinding.SearchItemLayoutBinding
import com.example.newrecipie.model.Meal
import com.example.newrecipie.model.MealDetails
import com.example.newrecipie.view.fragment.SearchFragmentDirections
import com.example.newrecipie.view.viewmodel.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SearchAdapter(val context: Context, private val viewModel: HomeViewModel) :
    ListAdapter<MealDetails, SearchAdapter.SearchViewHolder>(SearchMealsComparator()) {

    class SearchViewHolder(private val binding: SearchItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(mealDetails: MealDetails, viewModel: HomeViewModel) {
            binding.apply {
                Glide.with(itemView).load(mealDetails.strMealThumb).into(imgSearchItem)
                tvSearchItemTitle.text = mealDetails.strMeal
            }

            binding.imgSearchItemFavourite.setOnClickListener {

                var meal: Meal = Meal(
                    idMeal = mealDetails.idMeal,
                    strMeal = mealDetails.strMeal,
                    strMealThumb = mealDetails.strMealThumb,
                    isLike = true
                )

                viewModel.insertFavouriteRecipe(meal)

                if (meal.isLike) {
                    binding.imgSearchItemFavourite.setImageResource(R.drawable.ic_favorite_fill)
                } else {
                    binding.imgSearchItem.setImageResource(R.drawable.ic_heart)
                }

            }
            binding.root.setOnClickListener {

                var action =
                    SearchFragmentDirections.actionSearchFragmentToMealDetailFragment(mealId = mealDetails.idMeal)
                Navigation.findNavController(itemView).navigate(action)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {

        return SearchViewHolder(
            SearchItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem, viewModel)
        }
    }

    class SearchMealsComparator : DiffUtil.ItemCallback<MealDetails>() {
        override fun areItemsTheSame(oldItem: MealDetails, newItem: MealDetails) =
            oldItem.idMeal == newItem.idMeal

        override fun areContentsTheSame(oldItem: MealDetails, newItem: MealDetails) =
            oldItem == newItem
    }
}