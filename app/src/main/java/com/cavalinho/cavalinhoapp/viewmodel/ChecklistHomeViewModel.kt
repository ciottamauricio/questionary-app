package com.cavalinho.cavalinhoapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cavalinho.cavalinhoapp.service.listener.APIListener
import com.cavalinho.cavalinhoapp.service.repository.AnswerRepository

class ChecklistHomeViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "ChecklistHomeViewModel"
    private val mContext = application.applicationContext
    val answerRepository: AnswerRepository = AnswerRepository(mContext)

    private val mSendOk = MutableLiveData<String>()
    val send: LiveData<String> = mSendOk

    fun sendAnswers(loginUser: String, password: String) {
        answerRepository.sendAnswer(loginUser, password, object : APIListener<String> {
            override fun onSuccess(result: String, statusCode: Int) {
                mSendOk.value = result
            }

            override fun onFailure(message: String) {
                mSendOk.value = message
            }
        })
    }

    fun cleanSendOk() {
        mSendOk.value = ""
    }

    fun enableSendButton(): Boolean {
        return answerRepository.getCountNotSend() > 0
    }

    fun getCountNotSend() {
        answerRepository.getCountNotSend()
    }

    fun cleanNotConfirmed(): Boolean = if (answerRepository.getCountNotConfirmed() > 0) {
        Log.d(TAG, "Count not confirmed is: ${answerRepository.getCountNotConfirmed()}")
        answerRepository.cleanNotConfirmed()
        true
    } else {
        false
    }
}