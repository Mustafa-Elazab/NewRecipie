package com.example.newrecipie.view.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newrecipie.databinding.FavouriteItemLayoutBinding
import com.example.newrecipie.model.Meal
import com.example.newrecipie.view.fragment.FavouriteFragmentDirections
import com.example.newrecipie.view.viewmodel.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class FavouriteAdapter(
    private var viewModel: HomeViewModel,
    private val favouriteMealList: List<Meal>
) :
    RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {


    class FavouriteViewHolder(private val binding: FavouriteItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            mealDetails: Meal,
            viewModel: HomeViewModel,
            favouriteMealList: List<Meal>
        ) {

            binding.apply {
                tvFavouriteItemTitle.text = mealDetails.strMeal
                Glide.with(itemView).load(mealDetails.strMealThumb).into(imgFavouriteItem)
            }
            binding.imgFavouriteItemFavourite.setOnClickListener {
                viewModel.deleteFavouriteRecipe(mealDetails)
            }

            if (favouriteMealList.size == 0) {
                var action = FavouriteFragmentDirections.actionFavouriteFragmentToHomeFragment()
                Navigation.findNavController(itemView).navigate(action)
            }

            binding.root.setOnClickListener {
                var action =
                    FavouriteFragmentDirections.actionFavouriteFragmentToMealDetailFragment(mealId = mealDetails.idMeal)

                Navigation.findNavController(it).navigate(action)
            }


        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {

        return FavouriteViewHolder(
            FavouriteItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {


        val meal = favouriteMealList[position]

        holder.bind(meal, viewModel, favouriteMealList)


    }

    override fun getItemCount(): Int {
        return favouriteMealList.size
    }


}