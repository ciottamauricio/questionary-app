package com.cavalinho.cavalinhoapp.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat

@Entity(tableName = "tb_messages")
class MessageModel {

    @SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = 0

    @SerializedName("data")
    @ColumnInfo(name = "messagedate")
    var messageDate: String = ""

    @SerializedName("title")
    @ColumnInfo(name = "messagetitle")
    var messageTitle: String = ""

    @SerializedName("message")
    @ColumnInfo(name = "messagebody")
    var messageBody: String = ""

    @SerializedName("messagestatus")
    @ColumnInfo(name = "messagestatus")
    var messageStatus: Int = 0

    @SerializedName("sender")
    @ColumnInfo(name = "messagesender")
    var messageSender: String = ""

    @SerializedName("expiration")
    @ColumnInfo(name = "messageexpiration")
    var messageexpiration: String = ""

}