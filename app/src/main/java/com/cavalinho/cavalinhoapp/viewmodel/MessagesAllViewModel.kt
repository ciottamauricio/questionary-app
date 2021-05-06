package com.cavalinho.cavalinhoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cavalinho.cavalinhoapp.service.model.MessageModel
import com.cavalinho.cavalinhoapp.service.repository.MessagesRepository

class MessagesAllViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private val messageRepository: MessagesRepository = MessagesRepository(mContext)
    private var mMessageList = MutableLiveData<List<MessageModel>>()

    fun loadMessageList() {
        mMessageList.apply {
            value = messageRepository.getAllDb()
        }
    }

    fun apiConnection(loginUser: String, password: String) {
        messageRepository.downloadMessages(loginUser,password)
        messageRepository.sendMessageViewed(loginUser,password)
    }

    val messageList: LiveData<List<MessageModel>> = mMessageList

}