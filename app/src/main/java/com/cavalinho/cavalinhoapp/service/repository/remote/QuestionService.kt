package com.cavalinho.cavalinhoapp.service.repository.remote

import com.cavalinho.cavalinhoapp.service.model.QuestionModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface QuestionService {
    @POST("?param=voltaOlimpicaCorrente")
    @FormUrlEncoded
    fun getAll(
        @Field("driverId") login: String,
        @Field("password") password: String
    ): Call<List<QuestionModel>>
}