package com.cavalinho.cavalinhoapp.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.cavalinho.cavalinhoapp.R
import com.cavalinho.cavalinhoapp.viewmodel.MessagesHomeViewModel

class MessagesHomeFragment : Fragment() {

    private val TAG = "MessagesHomeFragment"
    private lateinit var viewModel: MessagesHomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_messages_home, container, false)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MessagesHomeViewModel::class.java)
        val teste = viewModel.getAllMessagesDb()
        teste?.let { t ->
            t.forEach {
                Log.d(TAG, "It   id:${it.id} date:${it.messageDate} title:${it.messageTitle} status: ${it.messageStatus}")
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btn_messages_all).setOnClickListener {
            findNavController().navigate(R.id.action_nav_messages_home_to_nav_messages_all)
        }
    }
}