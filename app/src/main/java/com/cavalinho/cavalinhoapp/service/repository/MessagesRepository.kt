package com.cavalinho.cavalinhoapp.service.repository

import android.content.Context
import android.util.Log

import com.cavalinho.cavalinhoapp.service.model.MessageModel
import com.cavalinho.cavalinhoapp.service.repository.local.AppDataBase
import com.cavalinho.cavalinhoapp.service.repository.remote.MessageService
import com.cavalinho.cavalinhoapp.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessagesRepository(context: Context) {
    private val TAG = "MessagesRepository"

    private val db = AppDataBase.getDataBase(context).messageDao()
    private val remote = RetrofitClient.createService(MessageService::class.java)

    fun insertOne(messages: MessageModel) = db.insertOne(messages)

    fun downloadMessages(loginUser: String, password: String) {
        val call: Call<List<MessageModel>> = remote.getAll(loginUser, password, db.getMaxId())
        call.enqueue(object : Callback<List<MessageModel>> {
            override fun onFailure(call: Call<List<MessageModel>>, t: Throwable) {
                Log.d(TAG, "Response Failed downloadMessages: $t")
            }

            override fun onResponse(call: Call<List<MessageModel>>, response: Response<List<MessageModel>>) {
                Log.d(TAG, "Response Success downloadMessages: $response")
                response.body()?.let { it ->
                    if (!it.isEmpty()) {
                        it.forEach {
                            Log.d(TAG, "Response Success downloadMessages: ${it.messageTitle}")
                        }
                        db.insert(it)
                    }
                }
            }
        })
    }

    fun getAllDb(): List<MessageModel> {
        return db.getAll()
    }

    fun getOne(id: Int): MessageModel {
        return db.getOne(id)
    }

    fun updateMessageViewed(id: Int) {
        db.updateMessageViewed(id)
    }

    fun sendMessageViewed(loginUser: String, password: String) {
        val call: Call<Int> = remote.sendAllViewed(loginUser, password, db.getAllViewedId())
        call.enqueue(object : Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.d(TAG, "Response Failed sendMessageViewed: $t")
            }
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                response.body()?.let { it ->
                    Log.d(TAG,"Response Success sendMessageViewed it : $it")
                }
            }
        })
    }

    fun cleanTableMessages() {
        db.cleanTableMessages()
    }
}