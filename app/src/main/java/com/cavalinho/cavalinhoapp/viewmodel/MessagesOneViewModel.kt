package com.cavalinho.cavalinhoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cavalinho.cavalinhoapp.service.model.MessageModel
import com.cavalinho.cavalinhoapp.service.repository.MessagesRepository

class MessagesOneViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private val messageRepository: MessagesRepository = MessagesRepository(mContext)
    private var mMessage = MutableLiveData<MessageModel>()

    fun loadView(id: Int) {
        mMessage.apply {
            value = messageRepository.getOne(id)
        }
    }

    val message: LiveData<MessageModel> = mMessage
    fun updateMessageViewed(id: Int) {
        messageRepository.updateMessageViewed(id)
    }
}