package com.cavalinho.cavalinhoapp.service.repository.remote

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AnswerService {

    @POST("?param=olimpicaCorrenteEnvio")
    @FormUrlEncoded
    fun send(
        @Field("driverId") driverId: String,
        @Field("password") password: String,
        @Field("datechecklist[]") dateArray: ArrayList<String>,
        @Field("questionId[]") questionidArray: ArrayList<Int>,
        @Field("veiculo[]") vehicleArray: ArrayList<String>,
        @Field("answerid[]") answerArray: ArrayList<Int>,
        @Field("formversion[]") formversionArray: ArrayList<Int>
    ): Call<Int>
}

