package com.cavalinho.cavalinhoapp.service.repository

import android.content.Context
import android.util.Log
import com.cavalinho.cavalinhoapp.R
import com.cavalinho.cavalinhoapp.service.listener.APIListener
import com.cavalinho.cavalinhoapp.service.model.UserModel
import com.cavalinho.cavalinhoapp.service.repository.local.AppDataBase
import com.cavalinho.cavalinhoapp.service.repository.remote.RetrofitClient
import com.cavalinho.cavalinhoapp.service.repository.remote.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserRepository(val context: Context) : BaseRepository(context) {
    val TAG = "UserRepositoryTag"

    private val db = AppDataBase.getDataBase(context).userDao()
    private val remote = RetrofitClient.createService(UserService::class.java)

    fun insert(user: UserModel) {
        db.insert(user)
    }

    fun getUserLocal(login: String): UserModel {
        return db.getOne(login)
    }

    fun cleanTableUsers() {
        db.cleanTableUsers()
    }

    fun downloadUser(login: String, password: String, listener: APIListener<UserModel>) {
        if(!isConnectionAvailable(context)){
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONECTION))
            return
        }
        val call: Call<UserModel> = remote.login(login, password)
        call.enqueue(object : Callback<UserModel> {
            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Log.d(TAG, "onFailure called: $t")
                listener.onFailure(t.toString())
            }

            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                Log.d(TAG, "onResponse Success: $response")
                response.body()?.let {
                    listener.onSuccess(it, response.code())
                }
            }
        })
    }
}