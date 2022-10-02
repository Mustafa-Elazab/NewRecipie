package com.example.newrecipie.view.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newrecipie.databinding.CategoryItemLayoutBinding
import com.example.newrecipie.model.Category
import com.example.newrecipie.view.fragment.HomeFragmentDirections


class CategoriesAdapter :
    ListAdapter<Category, CategoriesAdapter.CategoryViewHolder>(CategoryComparator()) {

    class CategoryViewHolder(private val binding: CategoryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(categoryModel: Category) {
            binding.apply {
                tvCategoryItem.text = categoryModel.strCategory
                Glide.with(itemView).load(categoryModel.strCategoryThumb).into(imgCategoryItem)
            }
            binding.root.setOnClickListener {
                Log.i("Tag", categoryModel.strCategory)

                val action =
                    HomeFragmentDirections.actionHomeFragmentToCategoryItemsFragment(categoryName = categoryModel.strCategory)
                Navigation.findNavController(itemView).navigate(action)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {

        return CategoryViewHolder(
            CategoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class CategoryComparator : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category) =
            oldItem.idCategory == newItem.idCategory

        override fun areContentsTheSame(oldItem: Category, newItem: Category) =
            oldItem == newItem
    }
}