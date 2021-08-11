package com.vkochenkov.imagesearch.presentation

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Activity.showToast(strId: Int) {
    Toast.makeText(this, this.applicationContext?.getText(strId), Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(strId: Int) {
    Toast.makeText(activity, activity?.applicationContext?.getText(strId), Toast.LENGTH_SHORT).show()
}