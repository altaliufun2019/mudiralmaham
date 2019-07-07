package com.example.mudiralmaham.Pages

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mudiralmaham.R
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.widget.*
import com.example.mudiralmaham.Constants
import com.example.mudiralmaham.Webservice.Request.LoginRequest
import com.example.mudiralmaham.Webservice.Response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment: Fragment() {

    var _emailText: EditText? = null
    var _passwordText: EditText? = null
    var _loginButton: Button? = null
    var _signupLink: TextView? = null
    var _layout: RelativeLayout? = null
    var _progress_dialog: ProgressDialog? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.login_fragment, container, false)
        _emailText = rootView.findViewById(R.id.input_email)
        _passwordText = rootView.findViewById(R.id.input_password)
        _loginButton = rootView.findViewById(R.id.btn_login)
        _signupLink = rootView.findViewById(R.id.link_signup)
        _layout = rootView.findViewById(R.id.login_fragment)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _loginButton?.setOnClickListener{
            loginToServer()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
            }
        }
    }

    fun loginToServer() {
        Log.d(TAG, "Login initialized")

        if (!validate()) {
            onLoginFailed()
            return
        }

        _loginButton?.isEnabled = false

        _progress_dialog = ProgressDialog()
        _progress_dialog?.isCancelable = false
        _progress_dialog?.show(fragmentManager, "login_progress")

        val email = _emailText?.getText().toString()
        val password = _passwordText?.getText().toString()
        val data = LoginRequest(email, password)

        val request: Call<LoginResponse> = Constants.webservice.login(data)
        request.enqueue(object: Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Toast.makeText(activity?.applicationContext, "logined", Toast.LENGTH_SHORT).show()
                _loginButton?.isEnabled = true
                _progress_dialog?.dismiss()
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d(TAG, "response failed")
                _loginButton?.isEnabled = true
                _progress_dialog?.dismiss()
            }
        })
    }

    fun onBackPressed() {
        activity?.finish()
    }

    fun onLoginSuccess() {
        _loginButton?.setEnabled(true)
    }

    fun onLoginFailed() {
        Toast.makeText(activity?.applicationContext, "Login failed", Toast.LENGTH_LONG).show()

        _loginButton?.setEnabled(true)
    }

    fun validate(): Boolean {
        var valid = true

        val email = _emailText?.getText().toString()
        val password = _passwordText?.getText().toString()

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText?.setError("enter a valid email address")
            valid = false
        } else {
            _emailText?.setError(null)
        }

        if (password.isEmpty() || password.length < 4 || password.length > 10) {
            _passwordText?.setError("between 4 and 10 alphanumeric characters")
            valid = false
        } else {
            _passwordText?.setError(null)
        }

        return valid
    }
}