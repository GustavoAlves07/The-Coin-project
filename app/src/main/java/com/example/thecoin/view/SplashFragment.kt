package com.example.thecoin.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.thecoin.R
import com.example.thecoin.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        Handler(Looper.getMainLooper()).postDelayed(this::goToLoginPage, 3000)


        return binding.root
    }


    private fun goToLoginPage() {



        findNavController().navigate(R.id.action_homeFragment_to_loginPageFragment)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}