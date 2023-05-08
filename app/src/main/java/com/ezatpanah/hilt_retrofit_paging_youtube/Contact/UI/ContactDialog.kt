package com.ezatpanah.hilt_retrofit_paging_youtube.Contact.UI

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ezatpanah.hilt_retrofit_paging_youtube.Contact.Adapter.APIModelAdapterSearchProvince
import com.ezatpanah.hilt_retrofit_paging_youtube.R
import com.ezatpanah.hilt_retrofit_paging_youtube.Contact.ViewModel.ContactVIewModel
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactDialog() : DialogFragment() {
    private lateinit var APIModelAdapterSearchProvince: APIModelAdapterSearchProvince
    var textInputLayOut: TextInputLayout? = null
    val fragment: Fragment = Fragment()

    private val viewModelProvince: ContactVIewModel by viewModels()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.dialog_contact, container, false)
        var closeBtn = rootView.findViewById<TextView>(R.id.close_button_contact)
        var editText = rootView.findViewById<EditText>(R.id.editTextProvince)
        var recycleViewProvince: RecyclerView = rootView.findViewById(R.id.recyclerViewProvince)
        var message_empty_province: LinearLayout = rootView.findViewById(R.id.message_empty_province)
        var progressBarProvince: ProgressBar = rootView.findViewById(R.id.progressBarProvince)
        textInputLayOut = rootView.findViewById(R.id.textInputLayOutProvince)

        closeBtn.setOnClickListener {
            dismiss()
        }
        APIModelAdapterSearchProvince = APIModelAdapterSearchProvince(arrayListOf())
        recycleViewProvince.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = APIModelAdapterSearchProvince
            viewModelProvince.apiModelAdapterProvince =
                APIModelAdapterSearchProvince
        }

        textInputLayOut!!.setEndIconOnClickListener {
            viewModelProvince.getDataFromApiProvince(
                editText.text.toString()
            )
            lifecycle.coroutineScope.launchWhenCreated {
                viewModelProvince.state.collect {
                    when (it) {
                        is ContactVIewModel.StateController.Loading -> {
                            recycleViewProvince.visibility = View.GONE
                            message_empty_province.visibility = View.GONE
                            progressBarProvince.visibility = View.VISIBLE
                        }
                        is ContactVIewModel.StateController.Empty -> {
                            recycleViewProvince.visibility = View.GONE
                            message_empty_province.visibility = View.VISIBLE
                            progressBarProvince.visibility = View.GONE
                        }
                        is ContactVIewModel.StateController.Success -> {
                            recycleViewProvince.visibility = View.VISIBLE
                            message_empty_province.visibility = View.GONE
                            progressBarProvince.visibility = View.GONE
                        }
                        else -> {}
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