package com.example.newrecipie.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.example.newrecipie.databinding.FragmentSplashBinding
import com.example.newrecipie.view.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {

    lateinit var binding: FragmentSplashBinding
    private val sharedPrefs by lazy {
        requireContext().getSharedPreferences("main", Context.MODE_PRIVATE)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isBoarding = sharedPrefs.getBoolean("isBoarding", false)
        if (isBoarding) {
            moveToMain()
        } else {
            moveToOnBoard()
        }


    }

    private fun moveToOnBoard() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                var action = SplashFragmentDirections.actionSplashFragmentToOnBoardingFragment()
                delay(2000)
                requireView().findNavController().navigate(action)
            }

        }
    }

    private fun moveToMain() {
        lifecycleScope.launch {
            val intent = Intent(requireContext(), MainActivity::class.java)
            delay(2000)
            startActivity(intent)
            activity?.finish()
        }
    }

}