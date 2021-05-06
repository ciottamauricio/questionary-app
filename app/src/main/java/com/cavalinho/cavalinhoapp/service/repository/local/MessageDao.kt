package com.cavalinho.cavalinhoapp.service.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cavalinho.cavalinhoapp.service.model.MessageModel


@Dao
interface MessageDao {

    @Insert
    fun insert(messages: List<MessageModel>)

    @Insert
    fun insertOne(messages: MessageModel)

    @Query("SELECT * FROM tb_messages")
    fun getAll(): List<MessageModel>

    @Query("SELECT * FROM tb_messages WHERE id = :id")
    fun getOne(id: Int): MessageModel

    @Query("SELECT IFNULL(MAX(id),0) FROM tb_messages")
    fun getMaxId(): Int

    @Query("SELECT id FROM tb_messages WHERE messagestatus = 1")
    fun getAllViewedId(): List<Int>

    @Query("DELETE FROM tb_messages")
    fun cleanTableMessages()

    @Query("UPDATE tb_messages SET messagestatus = 1 WHERE id = :id")
    fun updateMessageViewed(id: Int)

}