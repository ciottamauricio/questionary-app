package com.cavalinho.cavalinhoapp.service.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cavalinho.cavalinhoapp.service.model.UserModel

@Dao
interface UserDao {

    @Insert
    fun insert(user: UserModel)

    @Query("SELECT * FROM tb_users WHERE login = :login")
    fun getOne(login: String): UserModel

    @Query("DELETE FROM tb_users")
    fun cleanTableUsers()
}