package com.cavalinho.cavalinhoapp.service.repository

import android.content.Context
import android.util.Log
import com.cavalinho.cavalinhoapp.R
import com.cavalinho.cavalinhoapp.service.listener.APIListener
import com.cavalinho.cavalinhoapp.service.model.AnswerModel
import com.cavalinho.cavalinhoapp.service.repository.local.AppDataBase
import com.cavalinho.cavalinhoapp.service.repository.remote.AnswerService
import com.cavalinho.cavalinhoapp.service.repository.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AnswerRepository(val context: Context) : BaseRepository(context) {
    private val TAG = "AnswerRepository"

    private val db = AppDataBase.getDataBase(context).answerDao()
    private val remote = RetrofitClient.createService(AnswerService::class.java)

    fun insertOne(answer: AnswerModel) = db.insert(answer)

    fun getAll() = db.getAll()

    fun getOneQuestionType(type: Int): List<AnswerModel> = db.getOneQuestionType(type)

    fun updateAnswersSelected(id: Int, answerid: Int) = db.updateAnswersSelected(id, answerid)

    fun cleanTableAnswers() = db.cleanTableAnswers()

    fun updateAfterSend() = db.updateAfterSend()

    fun getCountNotSend(): Int = db.getCountNotSend()

    fun cleanNotConfirmed() = db.cleanNotConfirmed()

    fun conformAnswers() = db.confirmAnswers()

    fun getCountNotConfirmed(): Int = db.countNotConfirmed()

    fun sendAnswer(loginUser: String, password: String, listener: APIListener<String>) {
        if (!isConnectionAvailable(context)) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONECTION))
            return
        }

        val notsend = db.getNotSend()
        val dateArray = ArrayList<String>()
        val questionidArray = ArrayList<Int>()
        val vehicleArray = ArrayList<String>()
        val answerArray = ArrayList<Int>()
        val formversionArray = ArrayList<Int>()

        notsend.forEach {
            dateArray.add(it.date)
            questionidArray.add(it.questionid)
            vehicleArray.add(it.vehicle)
            answerArray.add(it.answerid)
            formversionArray.add(it.formVersion)
        }

        val call: Call<Int> = remote.send(
            loginUser,
            password,
            dateArray,
            questionidArray,
            vehicleArray,
            answerArray,
            formversionArray
        )

        call.enqueue(object : Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.d(TAG, "onFailure: couldnt post $t")
                listener.onFailure(t.toString())
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                Log.d(TAG, "onResponse Success response: $response")
                response.body()?.let {
                    Log.d(TAG, "onResponse Success it: $it")
                    if (it > 0) {
                        updateAfterSend()
                        listener.onSuccess(context.getString(R.string.SEND_SUCCESS), response.code())
                    } else {
                        listener.onFailure(context.getString(R.string.SEND_FAILURE))
                    }
                }
            }
        })
    }
}

