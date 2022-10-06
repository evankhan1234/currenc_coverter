package com.currency.exchanger.presentation.common.extension

import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.currency.exchanger.R

fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showGenericAlertDialog(message: String){
    AlertDialog.Builder(this).apply {
        setMessage(message)
        setPositiveButton(getString(R.string.button_text_ok)){ dialog, _ ->
             dialog.dismiss()
        }
    }.show()
}