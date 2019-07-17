package com.example.mudiralmaham.pages

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
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
import com.example.mudiralmaham.utils.ContextHolder
import com.example.mudiralmaham.webservice.request.SignUpRequest
import com.example.mudiralmaham.webservice.response.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupFragment : Fragment() {

    var name: EditText? = null
    var email: EditText? = null
    var password: EditText? = null
    var loginlink: TextView? = null
    var signupButton: Button? = null
    private var _progress_dialog: ProgressDialog? = null
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
//            signupRequest()
        }

    }

    private fun nextPage(page: Fragment, crossFade: Boolean = false) {
        val transaction = fragmentManager?.beginTransaction()
        if (crossFade)
            transaction?.setCustomAnimations(R.anim.no_anim, R.anim.ltr_slide_out)
        transaction?.replace(R.id.fragment_holder, page)?.disallowAddToBackStack()
        transaction?.commit()
    }

    private fun onSuccess() {
        Snackbar.make(
            root!!,
            "signed up in successfully",
            Snackbar.LENGTH_SHORT
        ).show()
        signupButton?.isEnabled = true
        _progress_dialog?.dismiss()

        nextPage(LoginFragment(), true)
    }

    private fun onFailure() {
        Snackbar.make(
            root!!,
            "sign up failed",
            Snackbar.LENGTH_SHORT
        ).show()
        signupButton?.isEnabled = true
        _progress_dialog?.dismiss()


    }


    private fun signupRequest() {
        Log.d(ContentValues.TAG, "Signup attempt")

        if (!validate()) {
            onFailure()
            return
        }

        signupButton?.isEnabled = false

        _progress_dialog = ProgressDialog()
        _progress_dialog?.isCancelable = false
        _progress_dialog?.show(fragmentManager, "sign_progress")

        val emailText = email?.text.toString()
        val passwordText = password?.text.toString()
        val nameText = name?.text.toString()
        val data = SignUpRequest(nameText, passwordText, emailText)

        val request: Call<SignUpResponse> = ContextHolder.webservice.signUp(data)
        request.enqueue(object : Callback<SignUpResponse> {
            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                onFailure()
            }

            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                if (response.code() == 200)
                    onSuccess()
                else
                    onFailure()

            }
        })

    }

    private fun validate(): Boolean {

        var valid = true

        val nameText = name?.text.toString()
        val emailText = email?.text.toString()
        val passwordText = password?.text.toString()

        if (nameText.isEmpty()) {
            name?.error = "enter your name"
            valid = false
        } else {
            name?.error = null
        }

        if (emailText.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email?.error = "enter valid email address"
            valid = false
        } else {
            email?.error = null
        }

        if (passwordText.isEmpty() || passwordText.length < 4 || passwordText.length > 10) {
            password?.error = "between 4 and 10 alphanumeric characters"
            valid = false
        } else {
            password?.error = null
        }

        return valid
    }


}