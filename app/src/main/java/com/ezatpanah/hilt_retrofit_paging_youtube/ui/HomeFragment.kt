package com.ezatpanah.hilt_retrofit_paging_youtube.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import com.ezatpanah.hilt_retrofit_paging_youtube.Component.showCustomToast
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.HomeViewModel.HomeViewModel
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.TypeStoryModel
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.CreateUser
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.KioskList
import com.ezatpanah.hilt_retrofit_paging_youtube.Home.Model.ServiceList
import com.ezatpanah.hilt_retrofit_paging_youtube.Normal.NormalViewModel.NormalViewModel
import com.ezatpanah.hilt_retrofit_paging_youtube.R
import com.ezatpanah.hilt_retrofit_paging_youtube.databinding.FragmentHomeBinding
import com.ezatpanah.hilt_retrofit_paging_youtube.RetrofitApi.ApiRepository
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()
        bundle.putString("data", "null")
        bundle.putString("checkDataString", "HomeFragment")
        val dataToHome = MainInformationFragment()
        dataToHome.arguments = bundle
        fragmentManager?.beginTransaction()?.replace(R.id.fragment_container,dataToHome)?.commit()


    }

}
