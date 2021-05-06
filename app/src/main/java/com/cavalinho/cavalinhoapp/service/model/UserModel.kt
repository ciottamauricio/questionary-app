package com.cavalinho.cavalinhoapp.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tb_users")
class UserModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @SerializedName("result")
    @ColumnInfo(name = "login")
    var login: String = ""

    @SerializedName("password")
    @ColumnInfo(name = "password")
    var password: String = ""

    @SerializedName("driverName")
    @ColumnInfo(name = "name")
    var name: String = ""
    
}