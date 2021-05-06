package com.cavalinho.cavalinhoapp.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.cavalinho.cavalinhoapp.R
import com.cavalinho.cavalinhoapp.viewmodel.MessagesOneViewModel

class MessagesOneFragment : Fragment() {

    val TAG = "MessagesOneFragment"

    companion object {
        var messageId = 0
    }

    private lateinit var viewModel: MessagesOneViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MessagesOneViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_messages_one, container, false)
        observe(root)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateMessageViewed(messageId)
    }

    fun observe(v: View) {
        viewModel.loadView(messageId)
        viewModel.message.observe(viewLifecycleOwner, Observer {
            v.findViewById<TextView>(R.id.txt_message_title).text = it.messageTitle
            v.findViewById<TextView>(R.id.txt_message_date).text = it.messageDate
            v.findViewById<TextView>(R.id.txt_sender).text = it.messageSender
            v.findViewById<TextView>(R.id.txt_message_body).text = it.messageBody
        })
    }

}