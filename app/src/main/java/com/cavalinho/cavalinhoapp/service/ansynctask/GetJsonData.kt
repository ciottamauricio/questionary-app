package com.cavalinho.cavalinhoapp.service.ansynctask

import android.os.AsyncTask
import android.util.Log
import com.cavalinho.cavalinhoapp.service.model.QuestionModel
import org.json.JSONException
import org.json.JSONObject

class GetJsonData(private val listener: OnDataAvailable) : AsyncTask<String, Void, ArrayList<QuestionModel>>() {

    private val TAG = "GetQuestionsJsonData"

    interface OnDataAvailable {
        fun onDataAvailable(data: List<QuestionModel>)
        fun onError(exception: Exception)
    }

    override fun doInBackground(vararg params: String): ArrayList<QuestionModel> {
        val questionList = ArrayList<QuestionModel>()
        try {
            val jsonData = JSONObject(params[0])
            val itemsArray = jsonData.getJSONArray("questions")

            for (i in 0 until itemsArray.length()) {
                val json = itemsArray.getJSONObject(i)
                val id = json.getInt("id")
                val question = json.getString("question")
                val order = json.getInt("order")
                val questionObject = QuestionModel().apply {
                    this.id = id
                    this.question = question
                    this.order = order
                }
                questionList.add(questionObject)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, ".doInBackground: Error processing Json data. ${e.message}")
            cancel(true)
            listener.onError(e)
        }
//        Log.d(TAG, ".doInBackground $questionList")
        return questionList
    }

    override fun onPostExecute(result: ArrayList<QuestionModel>) {
        Log.d(TAG, "onPostExecute starts")
        super.onPostExecute(result)
        listener.onDataAvailable(result)
        Log.d(TAG, ".onPostExecute ends")
    }
}