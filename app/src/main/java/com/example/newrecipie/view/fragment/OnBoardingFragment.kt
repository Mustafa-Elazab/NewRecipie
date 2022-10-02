package com.example.newrecipie.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.newrecipie.R
import com.example.newrecipie.adapter.OnBoardAdapter
import com.example.newrecipie.model.OnBoardModel
import com.example.newrecipie.databinding.FragmentOnBoardingBinding
import com.example.newrecipie.view.activity.MainActivity


class OnBoardingFragment : Fragment() {

    private lateinit var onBoardingItemAdapter: OnBoardAdapter
    private lateinit var indicatorContainer: LinearLayout
    lateinit var binding: FragmentOnBoardingBinding

    private val sharedPrefs by lazy {
        requireContext().getSharedPreferences("main", Context.MODE_PRIVATE)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        setOnBoardingItem()
        setIndicator()
        setCurrentIndicator(0)
    }

    private fun setOnBoardingItem() {
        onBoardingItemAdapter = OnBoardAdapter(
            listOf(
                OnBoardModel(
                    onBoardImage = R.drawable.bg_splash,
                    onBoardTitle = "Share You \nRecipes",
                    onBoardDescription = "Learn and share your recipes with getting \namazing rewards "
                ),
                OnBoardModel(
                    onBoardImage = R.drawable.bg_splash,
                    onBoardTitle = "Learn to cook",
                    onBoardDescription = "Learn and share your recipes with getting \namazing rewards "
                ),
                OnBoardModel(
                    onBoardImage = R.drawable.bg_splash,
                    onBoardTitle = "Become a \nMaster Chef",
                    onBoardDescription = "Learn and share your recipes with getting \namazing rewards "
                ),
            )
        )
        val onBoardingViewPager = binding.viewPager
        onBoardingViewPager.adapter = onBoardingItemAdapter

        onBoardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        (onBoardingViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER

        binding.imageNext.setOnClickListener {
            if (onBoardingViewPager.currentItem + 1 < onBoardingItemAdapter.itemCount) {
                onBoardingViewPager.currentItem += 1
            } else {


                // navigate To Home
                // AppPreference.setBoolean(key = "isBoarding", true)
                sharedPrefs.edit().putBoolean("isBoarding", true).commit()

                Toast.makeText(
                    requireContext(),
                    sharedPrefs.getBoolean("isBoarding",false).toString(),
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)

            }
        }
    }

    private fun setIndicator() {
        indicatorContainer = binding.lyIndicatorsContainer
        val indicators = arrayOfNulls<ImageView>(onBoardingItemAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 8, 8, 8)
        for (i in indicators.indices) {
            indicators[i] = ImageView(requireContext())
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.inactive_dot
                    )
                )

                it.layoutParams = layoutParams
                indicatorContainer.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(position: Int) {

        val childCount = indicatorContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorContainer.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.active_dot
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.inactive_dot
                    )
                )
            }
        }
    }

}