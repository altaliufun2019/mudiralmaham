package com.example.mudiralmaham.pages

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.example.mudiralmaham.R

class ChangeThemeFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.pick_color)
                .setItems(
                    R.array.themes
                ) { dialog, which ->

                    activity?.application?.getSharedPreferences("AppTheme", Context.MODE_PRIVATE)?.edit()
                        ?.putInt("Theme", which)
                        ?.apply()
                    dialog.dismiss()
                    activity?.recreate()

                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}