package br.com.apps.churrascow.util

import android.graphics.Color
import android.view.View
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar

fun View.snackBarGreen(text: String) {
    Snackbar.make(this, text, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(Color.parseColor("#3ED745"))
        .setTextColor(Color.parseColor("#FFFFFF"))
        .show()
}

fun View.snackBarRed(text: String) {
    Snackbar.make(this, text, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(Color.parseColor("#FF0000"))
        .setTextColor(Color.parseColor("#FFFFFF"))
        .show()
}

fun View.controllerPopBackStack(){
    Navigation.findNavController(this).popBackStack()
}