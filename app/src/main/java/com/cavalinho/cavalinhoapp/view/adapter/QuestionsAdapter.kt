package com.cavalinho.cavalinhoapp.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.cavalinho.cavalinhoapp.R
import com.cavalinho.cavalinhoapp.service.model.AnswerModel
import com.cavalinho.cavalinhoapp.view.MainActivity
import com.cavalinho.cavalinhoapp.view.viewholder.QuestionViewHolder


class QuestionsAdapter : RecyclerView.Adapter<QuestionViewHolder>() {

    val TAG = "QuestionsAdapter"
    var mQuestionList: List<AnswerModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_checklist_questions_item, parent, false))
    }

    fun loadNewData(newQuestions: List<AnswerModel>) {
        mQuestionList = newQuestions
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mQuestionList.count()
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, itemPosition: Int) {
        holder.question.text = mQuestionList[itemPosition].questiontext
        holder.bindSpinner()
        mQuestionList[itemPosition].answerid
        holder.spinnerAnswer.setSelection(mQuestionList[itemPosition].answerid)
        if (mQuestionList[itemPosition].answerid != 0 && MainActivity.layoutOrder != 5) {
            holder.checkAnswered.visibility = View.VISIBLE
        } else {
            holder.checkAnswered.visibility = View.INVISIBLE
        }

        holder.spinnerAnswer.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(TAG, "Selected nothing.")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                mQuestionList[itemPosition].answerid = position
                if (position != 0 && MainActivity.layoutOrder != 5) {
                    holder.checkAnswered.visibility = View.VISIBLE
                } else {
                    holder.checkAnswered.visibility = View.INVISIBLE
                }
            }
        }
    }

}

