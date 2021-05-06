package com.cavalinho.cavalinhoapp.service.repository

import android.content.Context
import android.util.Log
import com.cavalinho.cavalinhoapp.service.model.QuestionModel
import com.cavalinho.cavalinhoapp.service.repository.local.AppDataBase
import com.cavalinho.cavalinhoapp.service.repository.remote.QuestionService
import com.cavalinho.cavalinhoapp.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionsRepository(context: Context) {
    private val TAG = "QuestionsRepository"

    //Acessar BD
    private val db = AppDataBase.getDataBase(context).questionDao()

    //Acessar API
    private val remote = RetrofitClient.createService(QuestionService::class.java)

    fun downloadAll(loginUser: String, password: String) {
        Log.d(TAG,"Login: $loginUser Phone:$password")
        val call: Call<List<QuestionModel>> = remote.getAll(loginUser,password)
        Log.d(TAG, "Response: $call")
        call.enqueue(object : Callback<List<QuestionModel>> {
            override fun onFailure(call: Call<List<QuestionModel>>, t: Throwable) {
                Log.d(TAG, "Response Failed: $t")
            }

            override fun onResponse(call: Call<List<QuestionModel>>, response: Response<List<QuestionModel>>) {
                Log.d(TAG, "Response Success: $response")
                response.body()?.let {
                    db.cleanTableQuestions()
                    db.insert(it)
                }
            }
        })
    }

    fun getAll(): List<QuestionModel> {
        return db.getAll()
    }

    fun getOneType(type: Int): List<QuestionModel> {
        return db.getOneType(type)
    }
}