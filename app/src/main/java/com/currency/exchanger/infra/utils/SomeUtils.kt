package com.currency.exchanger.infra.utils

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.currency.exchanger.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.floor

object SomeUtils {
    fun convertDateTime() : String{
        val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd,yyyy hh:mm a")
        val now: LocalDateTime = LocalDateTime.now()
        return dtf.format(now)
    }
    fun isInteger(d: Double): Boolean {
        // Note that Double.NaN is not equal to anything, even itself.
        return d == floor(d) && !java.lang.Double.isInfinite(d)
    }
    fun showPopUp(activity: Activity,description:String) {
        try {
            val dialog = Dialog(activity, R.style.AppCompatAlertDialogStyleBig)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            val mInflater = LayoutInflater.from(activity)
            val mView: View =
                (mInflater as LayoutInflater).inflate(R.layout.layout_popup, null, false)
            val btnYes = mView.findViewById<Button>(R.id.btn_forget_pin)
            val txtClose = mView.findViewById<ImageView>(R.id.img_cross)
            val txtDescription = mView.findViewById<TextView>(R.id.txt_description)
            txtDescription.text=description
            txtClose.setOnClickListener { dialog.dismiss() }
            btnYes.setOnClickListener {

                dialog.dismiss()
            }
            dialog.setContentView(mView)
            dialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}