package com.ezatpanah.hilt_retrofit_paging_youtube.Contact.UI

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ezatpanah.hilt_retrofit_paging_youtube.Contact.Adapter.APIModelAdapterAgency
import com.ezatpanah.hilt_retrofit_paging_youtube.Contact.Adapter.APIModelAdapterSearch
import com.ezatpanah.hilt_retrofit_paging_youtube.Contact.ViewModel.ContactSearchViewModel
import com.ezatpanah.hilt_retrofit_paging_youtube.R
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactDialogSearch() : DialogFragment() {
    var textInputLayOut: TextInputLayout? = null
    private lateinit var APIModelAdapterSearch: APIModelAdapterSearch
    private lateinit var APIModelAdapterAgency: APIModelAdapterAgency
    var DefaultItemSelecter = "คนปกติ"

    private val viewModelSearch: ContactSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val items = listOf("ผู้พิการ", "คนปกติ", " หน่วยงาน")
        var rootView: View = inflater.inflate(R.layout.contac_dialog_search, container, false)
        var closeBtn = rootView.findViewById<TextView>(R.id.close_button_contact_search)
        var editText = rootView.findViewById<EditText>(R.id.editTextProvinceSearch)
        var recycleViewSearch: RecyclerView =
            rootView.findViewById(R.id.recyclerViewProvinceSearch)
        var progressBarDestination = rootView.findViewById<ProgressBar>(R.id.progressBarDestination)
        var message_empty_destination =
            rootView.findViewById<TextView>(R.id.message_empty_destination)
        textInputLayOut = rootView.findViewById(R.id.textInputLayOutProvinceSearch)

        closeBtn.setOnClickListener {
            dismiss()
        }
        val autoComplete: AutoCompleteTextView = rootView.findViewById(R.id.dropdown_type)
        val adapter1 = ArrayAdapter(requireContext(), R.layout.list_items, items)
        autoComplete.setText(DefaultItemSelecter)
        autoComplete.setAdapter(adapter1)
        APIModelAdapterSearch = APIModelAdapterSearch(null)
        recycleViewSearch.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = APIModelAdapterSearch
            viewModelSearch.apiModelAdapterSearchProvince =
                APIModelAdapterSearch
        }
        editText.text = null
        textInputLayOut!!.setEndIconOnClickListener {
            viewModelSearch.getDataFromApiProvinceSearchDestination(
                editText.text.toString()
            )
            APIModelAdapterSearch = APIModelAdapterSearch(arrayListOf())
            recycleViewSearch.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = APIModelAdapterSearch
                viewModelSearch.apiModelAdapterSearchProvince =
                    APIModelAdapterSearch
            }

            lifecycle.coroutineScope.launchWhenCreated {
                viewModelSearch.state.collect {
                    when (it) {
                        is ContactSearchViewModel.StateController.Loading -> {
                            progressBarDestination.visibility = View.VISIBLE
                            recycleViewSearch.visibility = View.GONE
                            message_empty_destination.visibility = View.GONE
                            Log.i("state", "onCreateView: load")

                        }
                        is ContactSearchViewModel.StateController.Success -> {
                            recycleViewSearch.visibility = View.VISIBLE
                            progressBarDestination.visibility = View.GONE
                            message_empty_destination.visibility = View.GONE
                            Log.i("state", "onCreateView: success")

                        }
                        is ContactSearchViewModel.StateController.Empty -> {
                            message_empty_destination.visibility = View.VISIBLE
                            progressBarDestination.visibility = View.GONE
                            recycleViewSearch.visibility = View.GONE
                            Log.i("state", "onCreateView: empty")
                        }
                        else -> {}
                    }
                }
            }
        }
        autoComplete.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, position, id ->

                val itemSelecter = adapterView.getItemAtPosition(position)
                DefaultItemSelecter = itemSelecter.toString()

                if (DefaultItemSelecter == "คนปกติ") {
                    APIModelAdapterSearch = APIModelAdapterSearch(null)
                    recycleViewSearch.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = APIModelAdapterSearch
                        viewModelSearch.apiModelAdapterSearchProvince =
                            APIModelAdapterSearch
                    }
                    editText.text = null
                    message_empty_destination.visibility = View.GONE
                    Toast.makeText(requireContext(), itemSelecter.toString(), Toast.LENGTH_SHORT)
                        .show()
                    Log.i("selector", "onCreateView: ${itemSelecter}")
                    textInputLayOut!!.setEndIconOnClickListener {
                        viewModelSearch.getDataFromApiProvinceSearchDestination(
                            editText.text.toString()
                        )
                        APIModelAdapterSearch = APIModelAdapterSearch(arrayListOf())
                        recycleViewSearch.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = APIModelAdapterSearch
                            viewModelSearch.apiModelAdapterSearchProvince =
                                APIModelAdapterSearch
                        }

                        lifecycle.coroutineScope.launchWhenCreated {
                            viewModelSearch.state.collect {
                                when (it) {
                                    is ContactSearchViewModel.StateController.Loading -> {
                                        progressBarDestination.visibility = View.VISIBLE
                                        recycleViewSearch.visibility = View.GONE
                                        message_empty_destination.visibility = View.GONE
                                        Log.i("state", "onCreateView: load")

                                    }
                                    is ContactSearchViewModel.StateController.Success -> {
                                        recycleViewSearch.visibility = View.VISIBLE
                                        progressBarDestination.visibility = View.GONE
                                        message_empty_destination.visibility = View.GONE
                                        Log.i("state", "onCreateView: success")

                                    }
                                    is ContactSearchViewModel.StateController.Empty -> {
                                        message_empty_destination.visibility = View.VISIBLE
                                        progressBarDestination.visibility = View.GONE
                                        recycleViewSearch.visibility = View.GONE
                                        Log.i("state", "onCreateView: empty")
                                    }
                                    else -> {}
                                }
                            }
                        }
                    }
                } else {

                    APIModelAdapterAgency = APIModelAdapterAgency(null)
                    recycleViewSearch.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = APIModelAdapterAgency
                        viewModelSearch.apiModelAdapterAgency =
                            APIModelAdapterAgency
                    }

                    editText.text = null
                    message_empty_destination.visibility = View.GONE
                    Toast.makeText(requireContext(), itemSelecter.toString(), Toast.LENGTH_SHORT)
                        .show()
                    Log.i("selector", "onCreateView: ${itemSelecter}")
                    textInputLayOut!!.setEndIconOnClickListener {
                        viewModelSearch.getDataFromApiProvinceSearchVideophones(
                            editText.text.toString()
                        )
                        APIModelAdapterAgency = APIModelAdapterAgency(arrayListOf())
                        recycleViewSearch.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = APIModelAdapterAgency
                            viewModelSearch.apiModelAdapterAgency =
                                APIModelAdapterAgency
                        }

                        lifecycle.coroutineScope.launchWhenCreated {
                            viewModelSearch.state.collect {
                                when (it) {
                                    is ContactSearchViewModel.StateController.Loading -> {
                                        progressBarDestination.visibility = View.VISIBLE
                                        recycleViewSearch.visibility = View.GONE
                                        message_empty_destination.visibility = View.GONE
                                        Log.i("state", "onCreateView: load")

                                    }
                                    is ContactSearchViewModel.StateController.Success -> {
                                        recycleViewSearch.visibility = View.VISIBLE
                                        progressBarDestination.visibility = View.GONE
                                        message_empty_destination.visibility = View.GONE
                                        Log.i("state", "onCreateView: success")

                                    }
                                    is ContactSearchViewModel.StateController.Empty -> {
                                        message_empty_destination.visibility = View.VISIBLE
                                        progressBarDestination.visibility = View.GONE
                                        recycleViewSearch.visibility = View.GONE
                                        Log.i("state", "onCreateView: empty")
                                    }
                                    else -> {}
                                }
                            }
                        }
                    }
                }

            }



        return rootView

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_contact)
        // Set the width and height of the dialog
        val window = dialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(false)
        return dialog
    }
}