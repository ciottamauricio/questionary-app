package com.cavalinho.cavalinhoapp.service.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cavalinho.cavalinhoapp.service.model.AnswerModel
import com.cavalinho.cavalinhoapp.service.model.MessageModel
import com.cavalinho.cavalinhoapp.service.model.QuestionModel
import com.cavalinho.cavalinhoapp.service.model.UserModel

@Database(entities = [QuestionModel::class, AnswerModel::class, UserModel::class, MessageModel::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun questionDao(): QuestionDao
    abstract fun answerDao(): AnswerDao
    abstract fun messageDao(): MessageDao

    companion object {
        private lateinit var INSTANCE: AppDataBase
        fun getDataBase(context: Context): AppDataBase {
            if (!Companion::INSTANCE.isInitialized) {
                synchronized(AppDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context, AppDataBase::class.java, "Cavalinhoapp.db")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}