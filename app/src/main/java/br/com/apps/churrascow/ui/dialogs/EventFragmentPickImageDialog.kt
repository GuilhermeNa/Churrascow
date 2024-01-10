package br.com.apps.churrascow.ui.dialogs

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import br.com.apps.churrascow.databinding.DialogEventImageBinding
import br.com.apps.churrascow.util.loadImageThroughUrl

class EventFragmentPickImageDialog(

    private val context: Context

) {

    /**
     * This is the dialog shown when adding or editing a image for the Event.
     *
     * @return high order function with the image url.
     */
    fun show(
        urlImage: String? = null,
        urlLoadedListener: (urlImage: String) -> Unit
    ) {
        DialogEventImageBinding
            .inflate(LayoutInflater.from(context)).apply {
                val urlField = formImageUrl
                val imgFieldDialog = formImageImage

                urlImage?.let {
                    imgFieldDialog.loadImageThroughUrl(it, context)
                    urlField.setText(it)
                }

                formImageButton.setOnClickListener {
                    val urlString = urlField.text.toString()
                    imgFieldDialog.loadImageThroughUrl(urlString, context)
                }

                AlertDialog
                    .Builder(context)
                    .setView(root)
                    .setPositiveButton("Confirm") { _, _ ->
                        val url = urlField.text.toString()
                        urlLoadedListener(url)
                    }
                    .show()
            }
    }

}