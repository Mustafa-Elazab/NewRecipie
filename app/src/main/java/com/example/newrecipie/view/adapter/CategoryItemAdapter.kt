package com.example.newrecipie.view.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newrecipie.databinding.PopularItemLayoutBinding
import com.example.newrecipie.model.Meal
import com.example.newrecipie.view.fragment.CategoryItemsFragmentDirections


class CategoryItemAdapter(
    private val categoryItemsList: List<Meal>,
) :
    RecyclerView.Adapter<CategoryItemAdapter.CategoryItemViewHolder>() {


    class CategoryItemViewHolder(private val binding: PopularItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(mealDetails: Meal) {
            binding.apply {
                Glide.with(itemView).load(mealDetails.strMealThumb).into(imgPopularItem)
                tvPopularItemTitle.text = mealDetails.strMeal
            }


            binding.root.setOnClickListener {

                Log.i("Tag", mealDetails.idMeal)


                var action =
                    CategoryItemsFragmentDirections.actionCategoryItemsFragmentToMealDetailFragment(
                        mealId = mealDetails.idMeal
                    )
                Navigation.findNavController(itemView).navigate(action)

            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {

        return CategoryItemViewHolder(
            PopularItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {

        val meal = categoryItemsList[position]
        holder.bind(meal)
    }


    override fun getItemCount(): Int {
        return categoryItemsList.size
    }
}