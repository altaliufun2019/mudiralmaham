package com.example.mudiralmaham.pages

import android.content.ContentValues
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.mudiralmaham.R

class SignupFragment : Fragment() {

    var name: EditText? = null
    var email: EditText? = null
    var password: EditText? = null
    var loginlink: TextView? = null
    var signupButton: Button? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView: View = inflater.inflate(R.layout.signup_fragment, container, false)
        name = rootView.findViewById(R.id.signup_input_name)
        email = rootView.findViewById(R.id.signup_input_email)
        password = rootView.findViewById(R.id.signup_input_password)
        loginlink = rootView.findViewById(R.id.link_login)
        signupButton = rootView.findViewById(R.id.btn_signup)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginlink?.setOnClickListener {
            Log.d(ContentValues.TAG, "signup link")
            val signupFragment = LoginFragment()
            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragment_holder, signupFragment)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()


        }

    }


}