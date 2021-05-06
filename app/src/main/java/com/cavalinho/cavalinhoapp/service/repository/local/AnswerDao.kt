package com.cavalinho.cavalinhoapp.service.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cavalinho.cavalinhoapp.service.model.AnswerModel
import java.util.ArrayList

@Dao
interface AnswerDao {

    @Insert
    fun insert(answer: AnswerModel)

    @Query("SELECT * FROM tb_checklistanswers")
    fun getAll(): List<AnswerModel>

    @Query("SELECT * FROM tb_checklistanswers  where send = 0")
    fun getNotSend(): List<AnswerModel>

    @Query("SELECT * FROM tb_checklistanswers  where confirmed = 0 and questiontype = :type")
    fun getOneQuestionType(type: Int): List<AnswerModel>

    @Query("SELECT count(*) FROM tb_checklistanswers  where send = 0")
    fun getCountNotSend(): Int

    @Query("select count(*) FROM tb_checklistanswers WHERE confirmed = 0")
    fun countNotConfirmed(): Int

    @Query("DELETE FROM tb_checklistanswers")
    fun cleanTableAnswers()

    @Query("DELETE FROM tb_checklistanswers WHERE confirmed = 0")
    fun cleanNotConfirmed()

    @Query("update tb_checklistanswers SET send = 1 WHERE send = 0")
    fun updateAfterSend()

    @Query("update tb_checklistanswers SET answerid = :answerid WHERE id = :id")
    fun updateAnswersSelected(id: Int, answerid: Int)

    @Query("update tb_checklistanswers SET confirmed = 1 WHERE confirmed = 0")
    fun confirmAnswers()
}