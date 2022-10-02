package com.example.newrecipie.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newrecipie.model.OnBoardModel
import com.example.newrecipie.databinding.OnBoardingItemBinding


class OnBoardAdapter(private val onBoardItems: List<OnBoardModel>) :
    RecyclerView.Adapter<OnBoardAdapter.OnBoardViewHolder>() {

    class OnBoardViewHolder(private val binding: OnBoardingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(onBoardItem: OnBoardModel) {

            binding.apply {
                tvOnBoardTitle.text = onBoardItem.onBoardTitle
                tvOnBoardDescription.text = onBoardItem.onBoardDescription
                imgOnBoard.setImageResource(onBoardItem.onBoardImage)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardViewHolder {

        return OnBoardViewHolder(

            OnBoardingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        )
    }

    override fun onBindViewHolder(holder: OnBoardViewHolder, position: Int) {
        holder.bind(onBoardItems[position])
    }

    override fun getItemCount(): Int {
        return onBoardItems.size
    }
}