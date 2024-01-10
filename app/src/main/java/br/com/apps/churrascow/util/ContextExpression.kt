package br.com.apps.churrascow.util

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import br.com.apps.churrascow.R

fun Context.navigateTo(
    clazz: Class<*>,
    intent: Intent.() -> Unit = {}
) {
    Intent(this, clazz)
        .apply {
            intent()
            startActivity(this)
        }
}

fun Context.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT)
        .show()
}

fun Context.toolbarLightAndDarkColors(actionBar: ActionBar?, colorLight: Int, colorDark: Int){
    val nightModeFlags: Int = resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK

    when (nightModeFlags) {
        Configuration.UI_MODE_NIGHT_YES -> {
            actionBar?.setBackgroundDrawable(
                ColorDrawable(ContextCompat.getColor(this, colorDark))
            )
        }

        Configuration.UI_MODE_NIGHT_NO -> {
            actionBar?.setBackgroundDrawable(
                ColorDrawable(ContextCompat.getColor(this, colorLight))
            )
        }
    }
}
