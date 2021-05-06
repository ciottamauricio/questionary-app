package com.cavalinho.cavalinhoapp.service.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cavalinho.cavalinhoapp.service.model.QuestionModel

@Dao
interface QuestionDao {

    @Insert
    fun insert(questions: List<QuestionModel>)

    @Query("DELETE FROM tb_checklistquestions")
    fun cleanTableQuestions()

    @Query("SELECT * FROM tb_checklistquestions")
    fun getAll(): List<QuestionModel>

    @Query("SELECT * FROM tb_checklistquestions where type = :id")
    fun getOneType(id: Int): List<QuestionModel>
}