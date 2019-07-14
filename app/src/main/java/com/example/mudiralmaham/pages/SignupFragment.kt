package com.example.mudiralmaham.pages

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.mudiralmaham.MainActivity
import com.example.mudiralmaham.R

class SignupFragment : Fragment() {

    var name: EditText? = null
    var email: EditText? = null
    var password: EditText? = null
    var loginlink: TextView? = null
    var signupButton: Button? = null
    private var root: View? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.signup_fragment, container, false)
        name = root?.findViewById(R.id.signup_input_name)
        email = root?.findViewById(R.id.signup_input_email)
        password = root?.findViewById(R.id.signup_input_password)
        loginlink = root?.findViewById(R.id.link_login)
        signupButton = root?.findViewById(R.id.btn_signup)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginlink?.setOnClickListener {
            Log.d(ContentValues.TAG, "signup link")
            nextPage(LoginFragment(), true)
        }
        signupButton?.setOnClickListener {
            startActivity(Intent(context, MainActivity::class.java))
        }


    }

    private fun nextPage(page: Fragment, crossFade: Boolean = false) {
        val transaction = fragmentManager?.beginTransaction()
        if (crossFade)
            transaction?.setCustomAnimations(R.anim.fade_in, R.anim.ltr_slide_out)
        transaction?.replace(R.id.fragment_holder, page)?.disallowAddToBackStack()
        transaction?.commit()
    }


}