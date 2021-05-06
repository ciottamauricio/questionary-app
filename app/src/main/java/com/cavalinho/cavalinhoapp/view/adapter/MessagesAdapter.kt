package com.cavalinho.cavalinhoapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cavalinho.cavalinhoapp.R
import com.cavalinho.cavalinhoapp.service.listener.MessageListener
import com.cavalinho.cavalinhoapp.service.model.MessageModel
import com.cavalinho.cavalinhoapp.view.viewholder.MessagViewHolder

class MessagesAdapter : RecyclerView.Adapter<MessagViewHolder>() {

    val TAG = "MessagesAdapter"
    var mMessagelist: List<MessageModel> = arrayListOf()
    private lateinit var mListener: MessageListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagViewHolder {
        return MessagViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_messages_all_item, parent, false), mListener)
    }


    fun loadNewData(newQuestions: List<MessageModel>) {
        mMessagelist = newQuestions
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mMessagelist.count()
    }

    fun attachListener(listener: MessageListener) {
        mListener = listener
    }

    override fun onBindViewHolder(holder: MessagViewHolder, position: Int) {
        holder.messageTitle.text = mMessagelist[position].messageTitle
        holder.messageDate.text = mMessagelist[position].messageDate
        if (mMessagelist[position].messageStatus != 0) {
            holder.messageStatus.visibility = View.INVISIBLE
        }
        holder.bindListener(mMessagelist[position].id)
    }
}