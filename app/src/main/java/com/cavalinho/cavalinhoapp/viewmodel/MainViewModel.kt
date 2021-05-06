package com.cavalinho.cavalinhoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cavalinho.cavalinhoapp.service.constants.UserConstants
import com.cavalinho.cavalinhoapp.service.model.QuestionModel
import com.cavalinho.cavalinhoapp.service.repository.MessagesRepository
import com.cavalinho.cavalinhoapp.service.repository.QuestionsRepository
import com.cavalinho.cavalinhoapp.service.repository.local.SecurityPreferences


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private val mPreferences = SecurityPreferences(application)
    private val questionRepository: QuestionsRepository = QuestionsRepository(mContext)
    private val messageRepository: MessagesRepository = MessagesRepository(mContext)

    private val mUserName = MutableLiveData<String>()
    val userName: LiveData<String> = mUserName

    fun downloadData(loginUser: String, password: String) {
        questionRepository.downloadAll(loginUser, password)
        messageRepository.downloadMessages(loginUser, password)
    }

    fun loadUserName() {
        mUserName.value = mPreferences.get(UserConstants.SHARED.USER_NAME)
    }

    fun getAllQuestions(): List<QuestionModel> {
        return questionRepository.getAll()
    }

}