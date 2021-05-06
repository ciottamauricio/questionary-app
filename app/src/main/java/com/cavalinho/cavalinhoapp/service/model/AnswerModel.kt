package com.cavalinho.cavalinhoapp.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_checklistanswers")
class AnswerModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "formversion")
    var formVersion: Int = 0

    @ColumnInfo(name = "questionid")
    var questionid: Int = 0

    @ColumnInfo(name = "questiontext")
    var questiontext: String = ""

    @ColumnInfo(name = "questiontype")
    var questiontype: Int = 0

    @ColumnInfo(name = "answerid")
    var answerid: Int = 0

    @ColumnInfo(name = "login")
    var login: String = ""

    @ColumnInfo(name = "vehicle")
    var vehicle: String = ""

    @ColumnInfo(name = "phonenumber")
    var phonenumber: String = ""

    @ColumnInfo(name = "datechecklist")
    var date: String = ""

    @ColumnInfo(name = "confirmed")
    var confirmed: Boolean = false

    @ColumnInfo(name = "send")
    var send: Boolean = false
}