package com.example.mudiralmaham.pages

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.example.mudiralmaham.R
import com.example.mudiralmaham.utils.ContextHolder

class AccountFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.account_title)
                .setItems(
                    arrayOf("Name: \t" + ContextHolder.user?.name, "Email: \t" + ContextHolder.user?.email)
                ) { dialog, which ->

                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}