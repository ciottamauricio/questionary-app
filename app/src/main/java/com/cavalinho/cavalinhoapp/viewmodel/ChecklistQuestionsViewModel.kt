package com.cavalinho.cavalinhoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cavalinho.cavalinhoapp.service.model.AnswerModel
import com.cavalinho.cavalinhoapp.service.repository.AnswerRepository
import com.cavalinho.cavalinhoapp.service.repository.QuestionsRepository
import com.cavalinho.cavalinhoapp.view.MainActivity
import com.cavalinho.cavalinhoapp.view.adapter.QuestionsAdapter
import java.text.SimpleDateFormat
import java.util.*

class ChecklistQuestionsViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private val questionRepository: QuestionsRepository = QuestionsRepository(mContext)
    private val answerRepository: AnswerRepository = AnswerRepository(mContext)

    private var mQuestionList = MutableLiveData<List<AnswerModel>>()

    fun loadQuestionList(type: Int) {
        mQuestionList.apply {
            value = answerRepository.getOneQuestionType(type)
        }
    }

    val questionList: LiveData<List<AnswerModel>> = mQuestionList

    fun insertAnswers(loginUser: String) {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val currentDate = sdf.format(Date())
        val answer = AnswerModel()

        questionRepository.getAll().forEach {
            answer.apply {
                this.formVersion = it.formVersion
                this.questionid = it.id
                this.questiontext = it.question
                this.questiontype = it.type
                this.login = loginUser
                this.phonenumber = "999999999"
                this.vehicle = "AAA9999"
                this.date = currentDate.toString()
            }
            answerRepository.insertOne(answer)
        }
    }

    fun updateAnswersSelected(questionAdapter: QuestionsAdapter): Boolean {
        var cont = 0
        var result = false
        questionAdapter.mQuestionList.forEach {
            if (it.answerid == 0) {
                cont++
            }
        }
        if (cont == 0 || MainActivity.layoutOrder == 5) {
            questionAdapter.mQuestionList.forEach {
                answerRepository.updateAnswersSelected(it.id, it.answerid)
            }
            result = true
        }
        return result
    }

    fun confirmAnswers() {
        answerRepository.conformAnswers()
    }


    fun getAllAnswers(): List<AnswerModel> {
        return answerRepository.getAll()
    }

    fun cleanTableAnswers() {
        answerRepository.cleanTableAnswers()
    }
}