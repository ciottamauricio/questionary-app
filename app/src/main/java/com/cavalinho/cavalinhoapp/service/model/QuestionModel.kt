package com.cavalinho.cavalinhoapp.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tb_checklistquestions")
class QuestionModel {

    @SerializedName("questionId")
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = 0

    @SerializedName("formVersion")
    @ColumnInfo(name = "formversion")
    var formVersion: Int = 0

    @SerializedName("questionText")
    @ColumnInfo(name = "question")
    var question: String = ""

    @SerializedName("questionOrder")
    @ColumnInfo(name = "sortorder")
    var order: Int = 0

    @SerializedName("questionType")
    @ColumnInfo(name = "type")
    var type: Int = 0

    var answer: Int = 0
}