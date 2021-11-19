package com.ozantopuz.rijksmuseumapp.util.extension

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import com.ozantopuz.rijksmuseumapp.R

fun Context?.toast(text: String?, duration: Int = Toast.LENGTH_LONG) =
    this?.let { Toast.makeText(it, text, duration).show() }

fun Context?.showErrorDialog(message: String){
    val title = this?.getString(R.string.title_error_dialog)
    with(AlertDialog.Builder(this)){
        setTitle(title)
        setMessage(message)
        setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
        show()
    }
}