package com.cavalinho.cavalinhoapp.view.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cavalinho.cavalinhoapp.R
import com.cavalinho.cavalinhoapp.service.listener.MessageListener

class MessagViewHolder(itemView: View, val listener: MessageListener) : RecyclerView.ViewHolder(itemView) {
    val messageTitle: TextView = itemView.findViewById(R.id.messageTitle)
    val messageDate: TextView = itemView.findViewById(R.id.messageDate)
    val messageStatus: ImageView = itemView.findViewById(R.id.messageStatus)

    fun bindListener(id: Int) {
        messageTitle.setOnClickListener {
            listener.onListClick(id)
        }
        messageDate.setOnClickListener{
            listener.onListClick(id)
        }

    }
}