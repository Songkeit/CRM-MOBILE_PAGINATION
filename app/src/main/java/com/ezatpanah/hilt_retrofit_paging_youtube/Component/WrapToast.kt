package com.ezatpanah.hilt_retrofit_paging_youtube.Component

import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ezatpanah.hilt_retrofit_paging_youtube.R


var layout: View? = null

fun Toast.showCustomToast(message: String, activity: Fragment, black: Int) {
    layout = activity.layoutInflater.inflate(
        R.layout.custon_popup_toast,
        activity.view!!.findViewById(R.id.toast_container)

    )

    // set the text of the TextView of the message
    val textView = layout!!.findViewById<TextView>(R.id.toast_text)
    textView.text = message
    val buttonClickParent = layout!!.findViewById<FrameLayout>(R.id.button_accent_border)
    buttonClickParent.setBackgroundResource(black)

    // use the application extension function
    this.apply {
        duration = Toast.LENGTH_SHORT
        view = layout
        show()
    }
}
