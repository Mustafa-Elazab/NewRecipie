package com.example.newrecipie.view.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newrecipie.R
import com.example.newrecipie.databinding.PopularItemLayoutBinding
import com.example.newrecipie.model.Meal
import com.example.newrecipie.view.fragment.HomeFragmentDirections
import com.example.newrecipie.view.viewmodel.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
class PopularMealsAdapter(
    private val popularMealList: List<Meal>, var isHome: Boolean,
    private val viewModel: HomeViewModel
) :
    RecyclerView.Adapter<PopularMealsAdapter.LatestMealsViewHolder>() {


    class LatestMealsViewHolder(private val binding: PopularItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(mealDetails: Meal, viewModel: HomeViewModel) {
            binding.apply {
                Glide.with(itemView).load(mealDetails.strMealThumb).into(imgPopularItem)
                tvPopularItemTitle.text = mealDetails.strMeal
            }

            binding.imgPopularItemFavourite.setOnClickListener {

                binding.imgPopularItemFavourite.setImageResource(R.drawable.ic_favorite_fill)
            }


            binding.imgPopularItemFavourite.setOnClickListener {
                mealDetails.isLike = true
                viewModel.insertFavouriteRecipe(mealDetails)

                if(mealDetails.isLike){
                    binding.imgPopularItemFavourite.setImageResource(R.drawable.ic_favorite_fill)
                }else{
                    binding.imgPopularItemFavourite.setImageResource(R.drawable.ic_heart)
                }


            }

            binding.root.setOnClickListener {

                Log.i("Tag", mealDetails.idMeal)


                var action1 =
                    HomeFragmentDirections.actionHomeFragmentToMealDetailFragment(mealId = mealDetails.idMeal)
                Navigation.findNavController(itemView).navigate(action1)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestMealsViewHolder {

        return LatestMealsViewHolder(
            PopularItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: LatestMealsViewHolder, position: Int) {

        val meal = popularMealList[position]
        holder.bind(meal, viewModel = viewModel)
    }


    override fun getItemCount(): Int {
        if (isHome) {
            val size: Int = popularMealList.size
            return if (size > 8) 8 else size
        } else {
            return popularMealList.size
        }
    }
}