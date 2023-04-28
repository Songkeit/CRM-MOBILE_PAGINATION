package com.ezatpanah.hilt_retrofit_paging_youtube.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezatpanah.hilt_retrofit_paging_youtube.Emergency.Adapter.EmergencyAdapter
import com.ezatpanah.hilt_retrofit_paging_youtube.Emergency.MovieViewModel.EmergencyViewModel
import com.ezatpanah.hilt_retrofit_paging_youtube.R
import com.ezatpanah.hilt_retrofit_paging_youtube.databinding.FragmentEmergencyBinding
import com.ezatpanah.hilt_retrofit_paging_youtube.databinding.FragmentUnsavedBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class UnsavedFragment : Fragment() {
    private lateinit var binding:FragmentUnsavedBinding

    @Inject
    lateinit var unsavedAdapter: UnsavedAdapter
    private val viewModel: UnsavesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUnsavedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.stateIntent.collect {
                Log.i("check", "runningProgram: $it")
                when (it) {
                    is UnsavesViewModel.StateControllerIntent.Success -> {
                        Log.i("id", "onCreate: ${it.id}")
                        val bundle = Bundle()
                        bundle.putString("data", it.id.toString())//
                        bundle.putString("checkDataString", "UnsavedFragment")
                        val dataToHome = MainInformationFragment()
                        dataToHome.arguments = bundle
                        fragmentManager?.beginTransaction()
                            ?.replace(R.id.fragment_container, dataToHome)
                            ?.commit()
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        runningProgram()
    }
    private fun runningProgram(){
        binding.apply {
            lifecycleScope.launchWhenCreated {
                viewModel.unsavedList.collect{
                    unsavedAdapter.submitData(it)

                }
            }

            recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = unsavedAdapter
            }
            lifecycleScope.launchWhenCreated {
                unsavedAdapter.loadStateFlow.collect {
                    val state = it.refresh
                    progressBar.isVisible = state is LoadState.Loading
                }
            }

            //click to intent
            unsavedAdapter.setOnItemClickListener {
                val data = it.id
                Log.i("test01", "runningProgram: $data")
                viewModel.getDataCheckIntent(data!!.toInt())
            }
        }

    }
}