package com.cavalinho.cavalinhoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.cavalinho.cavalinhoapp.service.model.MessageModel
import com.cavalinho.cavalinhoapp.service.repository.MessagesRepository


class MessagesHomeViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private val messageRepository: MessagesRepository = MessagesRepository(mContext)

    fun getAllMessagesDb(): List<MessageModel> {
        return messageRepository.getAllDb()
    }

}