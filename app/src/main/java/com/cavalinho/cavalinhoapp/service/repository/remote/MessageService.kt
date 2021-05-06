package com.cavalinho.cavalinhoapp.service.repository.remote

import com.cavalinho.cavalinhoapp.service.model.MessageModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MessageService {

    @POST("?param=pendingMessages")
    @FormUrlEncoded
    fun getAll(
        @Field("driverId") login: String,
        @Field("password") password: String,
        @Field("lastId") lastId: Int
    ): Call<List<MessageModel>>

    @POST("?param=readMessages")
    @FormUrlEncoded
    fun sendAllViewed(
        @Field("driverId") login: String,
        @Field("password") password: String,
        @Field("messageId[]") messagesid: List<Int>
    ): Call<Int>


}