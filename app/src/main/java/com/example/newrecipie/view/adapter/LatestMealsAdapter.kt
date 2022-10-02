package com.example.newrecipie.view.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newrecipie.R
import com.example.newrecipie.databinding.RandomItemLayoutBinding
import com.example.newrecipie.model.Meal
import com.example.newrecipie.view.fragment.HomeFragmentDirections
import com.example.newrecipie.view.viewmodel.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class LatestMealsAdapter (private val viewModel: HomeViewModel) :
    ListAdapter<Meal, LatestMealsAdapter.LatestMealsViewHolder>(LatestMealsComparator()) {

    class LatestMealsViewHolder(private val binding: RandomItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(mealDetails: Meal,viewModel: HomeViewModel) {
            binding.apply {
                Glide.with(itemView).load(mealDetails.strMealThumb).into(imgRandomItem)
                tvRandomItemTitle.text = mealDetails.strMeal
            }

            binding.imgRandomItemFavourite.setOnClickListener {

                mealDetails.isLike = true
                viewModel.insertFavouriteRecipe(mealDetails)
                if(mealDetails.isLike){
                    binding.imgRandomItemFavourite.setImageResource(R.drawable.ic_favorite_fill)
                }else{
                    binding.imgRandomItemFavourite.setImageResource(R.drawable.ic_heart)
                }
            }

            binding.root.setOnClickListener {

                var action =
                    HomeFragmentDirections.actionHomeFragmentToMealDetailFragment(mealId = mealDetails.idMeal)
                Navigation.findNavController(itemView).navigate(action)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestMealsViewHolder {

        return LatestMealsViewHolder(
            RandomItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: LatestMealsViewHolder, position: Int) {

        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem,viewModel)
        }
    }

    class LatestMealsComparator : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal) =
            oldItem.idMeal == newItem.idMeal

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal) =
            oldItem == newItem
    }
}