package com.example.thecoin.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.thecoin.R
import com.example.thecoin.adapter.ViewPagerAdapter
import com.example.thecoin.databinding.FragmentViewPagerHomeBinding
import com.google.android.material.tabs.TabLayoutMediator


class ViewPagerHomeFragment : Fragment() {
    private var _binding : FragmentViewPagerHomeBinding? = null
    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerHomeBinding.inflate(inflater,container,false)

        clickListener()



        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()





    }



    private fun initViewPager(){
        val pageAdapter = ViewPagerAdapter(requireActivity())

        binding.viewPager.adapter = pageAdapter

        pageAdapter.addFragment(CurrencyExchangeFragment(),R.string.fragment_exchange)

        binding.viewPager.offscreenPageLimit = pageAdapter.itemCount

        TabLayoutMediator(binding.tabLayout,binding.viewPager){tab,position ->


            tab.text = getString(pageAdapter.getTitle(position))
        }.attach()









    }

    private fun clickListener(){

        binding.btnExit.setOnClickListener{
            Toast.makeText(requireActivity(),"Teste",Toast.LENGTH_SHORT)
                .show()
        }




    }






}