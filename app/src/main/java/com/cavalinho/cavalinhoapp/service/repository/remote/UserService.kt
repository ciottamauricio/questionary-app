package com.cavalinho.cavalinhoapp.service.repository.remote

import com.cavalinho.cavalinhoapp.service.model.UserModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserService {
    @POST("?param=driverLogin")
    @FormUrlEncoded
    fun login(
        @Field("driverId") login: String,
        @Field("password") password: String
    ): Call<UserModel>

}