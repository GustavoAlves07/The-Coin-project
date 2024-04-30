package com.example.thecoin.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.thecoin.R
import com.example.thecoin.databinding.FragmentLoginPageBinding


class LoginPageFragment : Fragment() {
    private var _binding : FragmentLoginPageBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentLoginPageBinding.inflate(LayoutInflater.from(requireContext()),container,false)



        binding.loginButton.setOnClickListener{
            verification()

        }



        return binding.root
    }







    private fun verification(){
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()


        if (email.isEmpty()){

            Toast.makeText(requireContext(),"Digite um email valido", Toast.LENGTH_LONG).show()

        }
        if(password.isEmpty()){
            Toast.makeText(requireContext(),"Digite uma senha valida", Toast.LENGTH_LONG).show()

        }
        else{
            findNavController().navigate(R.id.action_loginPageFragment_to_viewPagerHomeFragment)

        }





    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}