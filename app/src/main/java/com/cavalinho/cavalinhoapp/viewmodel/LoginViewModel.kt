package com.cavalinho.cavalinhoapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cavalinho.cavalinhoapp.service.constants.UserConstants
import com.cavalinho.cavalinhoapp.service.listener.APIListener
import com.cavalinho.cavalinhoapp.service.model.UserModel
import com.cavalinho.cavalinhoapp.service.repository.UserRepository
import com.cavalinho.cavalinhoapp.service.repository.local.SecurityPreferences

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "LoginViewModelTag"
    private val mContext = application.applicationContext
    private val userRepository = UserRepository(mContext)
    private val mSharededPreferences = SecurityPreferences(application)
    private var returnFalse = false

    private val mLogin = MutableLiveData<Boolean>()
    val login: LiveData<Boolean> = mLogin

    fun insertUser(user: UserModel) {
        userRepository.insert(user)
    }

    fun getUser(login: String): UserModel {
        return userRepository.getUserLocal(login)
    }

    fun cleanTableUsers() {
        userRepository.cleanTableUsers()
    }

    fun doLogin(login: String, password: String) {
        userRepository.downloadUser(login, password, object : APIListener<UserModel> {
            override fun onSuccess(result: UserModel, statusCode: Int) {
                var logindb = "ini"
                var password = "ini"
                getUser(result.login)?.let { t ->
                    logindb = t.login
                    password = t.password
                }

                if (!result.login.equals("") && !result.login.equals(logindb) || (result.login.equals(logindb) && !result.password.equals(password))) {
                    Log.d(TAG, "onSuccess Id: ${result.id}  Login: ${result.login} Phone: ${result.password} Name: ${result.name} (insert)")
                    cleanTableUsers()
                    insertUser(result)
                    mSharededPreferences.store(UserConstants.SHARED.USER_LOGIN, result.login)
                    mSharededPreferences.store(UserConstants.SHARED.USER_NAME, result.name)
                    mSharededPreferences.store(UserConstants.SHARED.USER_PASSWORD, result.password)
                    mLogin.value = true
                    returnFalse = true
                } else {
                    getUser(login)?.let { t ->
                        Log.d(TAG, "onSuccess Id: ${t.id}  Login: ${t.login} Phone: ${t.password} Name: ${t.name} (justquery)")
                        if (login == t.login && password == t.password) {
                            mSharededPreferences.store(UserConstants.SHARED.USER_LOGIN, t.login)
                            mSharededPreferences.store(UserConstants.SHARED.USER_NAME, t.name)
                            mSharededPreferences.store(UserConstants.SHARED.USER_PASSWORD, t.password)
                            mLogin.value = true
                            returnFalse = true
                        }
                    }
                }
                if (!returnFalse) {
                    mLogin.value = false
                }
            }

            override fun onFailure(message: String) {
                getUser(login)?.let { t ->
                    Log.d(TAG, "onFailure Id: ${t.id}  Login: ${t.login} Phone: ${t.password} Name: ${t.name}")
                    if (login == t.login && password == t.password) {
                        mSharededPreferences.store(UserConstants.SHARED.USER_LOGIN, t.login)
                        mSharededPreferences.store(UserConstants.SHARED.USER_NAME, t.name)
                        mSharededPreferences.store(UserConstants.SHARED.USER_PASSWORD, t.password)
                        mLogin.value = true
                        returnFalse = true
                    }
                }
                if (!returnFalse) {
                    mLogin.value = false
                }
            }
        })
    }
}