package com.example.mudiralmaham.pages

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mudiralmaham.R
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.design.widget.Snackbar
import android.util.Log
import android.widget.*
import com.example.mudiralmaham.MainActivity
import com.example.mudiralmaham.dataModels.User
import com.example.mudiralmaham.utils.ContextHolder
import com.example.mudiralmaham.webservice.request.LoginRequest
import com.example.mudiralmaham.webservice.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment: Fragment() {

    private var _emailText: EditText? = null
    private var _passwordText: EditText? = null
    private var _loginButton: Button? = null
    private var _signupLink: TextView? = null
    private var _layout: RelativeLayout? = null
    private var _progress_dialog: ProgressDialog? = null
    private var _root: View? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _root = inflater.inflate(R.layout.login_fragment, container, false)
        _emailText = _root?.findViewById(R.id.input_email)
        _passwordText = _root?.findViewById(R.id.input_password)
        _loginButton = _root?.findViewById(R.id.btn_login)
        _signupLink = _root?.findViewById(R.id.link_signup)
        _layout = _root?.findViewById(R.id.login_fragment)
        return _root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _loginButton?.setOnClickListener{
            loginToServer()
        }
        _signupLink?.setOnClickListener {
            nextPage(SignupFragment(), true)
        }
    }

    override fun onPause() {
        super.onPause()
    }

    private fun loginToServer() {
        Log.d(TAG, "Login initialized")

        if (!validate()) {
            onLoginFailed()
            return
        }

        _loginButton?.isEnabled = false

        _progress_dialog = ProgressDialog()
        _progress_dialog?.isCancelable = false
        _progress_dialog?.show(fragmentManager, "login_progress")

        val email = _emailText?.text.toString()
        val password = _passwordText?.text.toString()
        val data = LoginRequest(email, password)

        val request: Call<LoginResponse> = ContextHolder.webservice.login(data)
        request.enqueue(object: Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.code() == 200)
                    onLoginSuccess()
                else
                    onLoginFailed()
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onLoginSuccess()
            }
        })
    }

    fun onLoginSuccess() {
        Snackbar.make(
            _root!!,
            "logged in successfully",
            Snackbar.LENGTH_SHORT
        ).show()
        _loginButton?.isEnabled = true
        _progress_dialog?.dismiss()

        dataUpdate()
        startActivity(Intent(context, MainActivity::class.java))
    }

    fun onLoginFailed() {
        Snackbar.make(
            _root!!,
            "authentication failed",
            Snackbar.LENGTH_SHORT
        ).show()
        _loginButton?.isEnabled = true
        _progress_dialog?.dismiss()
    }

    private fun dataUpdate() {
        // TODO: request server for past tasks and projects
        cacheData()
    }

    private fun cacheData() {
        // TODO: change "mudir" to name gotten from server
        ContextHolder.user = User("mudir", _emailText?.text?.toString())
        val sharedPreferences: SharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)!!
        with(sharedPreferences.edit()) {
            putString("mudir_email", ContextHolder.user?.email)
            putString("mudir_name", ContextHolder.user?.name)
            apply()
        }

        ContextHolder.getCacheData()
    }


    private fun nextPage(page: Fragment, crossFade:Boolean = false) {
        val transaction = fragmentManager?.beginTransaction()
        if (crossFade)
            transaction?.setCustomAnimations(R.anim.no_anim, R.anim.ltr_slide_in)
        transaction?.replace(R.id.fragment_holder, page)?.disallowAddToBackStack()
        transaction?.commit()
    }

    fun validate(): Boolean {
        var valid = true

        val email = _emailText?.text.toString()
        val password = _passwordText?.text.toString()

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText?.error = "enter DueTimeReceiver valid email address"
            valid = false
        } else {
            _emailText?.error = null
        }

        if (password.isEmpty() || password.length < 4 || password.length > 10) {
            _passwordText?.error = "between 4 and 10 alphanumeric characters"
            valid = false
        } else {
            _passwordText?.error = null
        }

        return valid
    }
}