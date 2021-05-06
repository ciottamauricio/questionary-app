package com.cavalinho.cavalinhoapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cavalinho.cavalinhoapp.R
import com.cavalinho.cavalinhoapp.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    val TAG = "LoginActivityTag"

    private lateinit var mViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        setContentView(R.layout.activity_login)

        setListeners()
        observe()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_login) {
            handleLogin(v)
        }
    }

    private fun setListeners() {
        btn_login.setOnClickListener(this)
    }

    private fun handleLogin(v: View) {
        val login = edit_login.text.toString()
        val password = edit_password.text.toString()

        Log.d(TAG, "handleLogin Login: ${edit_login.text}")
        Log.d(TAG, "handleLogin Password: $password")

        mViewModel.doLogin(login, password)
        findViewById<ProgressBar>(R.id.progressBar).setVisibility(View.VISIBLE)
    }

    private fun observe() {
        mViewModel.login.observe(this, Observer {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(applicationContext, "Falha no login, entre em contato com o suporte.", Toast.LENGTH_SHORT).show()
                findViewById<ProgressBar>(R.id.progressBar).setVisibility(View.INVISIBLE)
            }
        })
    }
}