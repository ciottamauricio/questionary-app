package com.cavalinho.cavalinhoapp.view.viewholder

import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cavalinho.cavalinhoapp.R
import com.cavalinho.cavalinhoapp.view.MainActivity


class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val TAG = "ChecklistQuestionsFragment1"
    val question: TextView = itemView.findViewById(R.id.question)
    val spinnerAnswer: Spinner = itemView.findViewById(R.id.answer)
    val checkAnswered: ImageView = itemView.findViewById(R.id.checkAnswered)

    fun bindSpinner() = if (MainActivity.layoutOrder == 5) {
        val adapter = ArrayAdapter(itemView.context, android.R.layout.simple_list_item_1, arrayListOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15))
        spinnerAnswer.adapter = adapter
    } else {
        val adapter = ArrayAdapter(itemView.context, android.R.layout.simple_spinner_dropdown_item, arrayListOf("Escolha", "Sim", "Não", "N/A"))
        spinnerAnswer.adapter = adapter
    }

}


