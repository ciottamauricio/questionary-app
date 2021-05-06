package com.cavalinho.cavalinhoapp.view


import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cavalinho.cavalinhoapp.R
import com.cavalinho.cavalinhoapp.service.constants.UserConstants
import com.cavalinho.cavalinhoapp.service.listener.MessageListener
import com.cavalinho.cavalinhoapp.service.repository.local.SecurityPreferences
import com.cavalinho.cavalinhoapp.view.adapter.MessagesAdapter
import com.cavalinho.cavalinhoapp.viewmodel.MessagesAllViewModel

class MessagesAllFragment : Fragment() {

    val TAG = "MessagesAllFragment"
    private lateinit var viewModel: MessagesAllViewModel
    private val messageAdapter: MessagesAdapter = MessagesAdapter()
    private lateinit var mListener: MessageListener
    private lateinit var loginUser: String
    private lateinit var password: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MessagesAllViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_messages_all, container, false)
        val recycler = root.findViewById<RecyclerView>(R.id.recycler_messages_all)

        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = messageAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        mListener = object : MessageListener {
            override fun onListClick(id: Int) {
                Log.d(TAG, "ID Message: $id")
                MessagesOneFragment.messageId = id
                findNavController().navigate(R.id.action_nav_messages_all_to_nav_messages_one)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, true) {
            //callback for back button, doesn't do anything.
        }
        val mPreferences = SecurityPreferences(root.context)
        loginUser = mPreferences.get(UserConstants.SHARED.USER_LOGIN)
        password = mPreferences.get(UserConstants.SHARED.USER_PASSWORD)

        observe()
        return root
    }

    fun observe() {
        viewModel.apiConnection(loginUser, password)
        viewModel.loadMessageList()
        messageAdapter.attachListener(mListener)
        viewModel.messageList.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "it: $it")
            messageAdapter.loadNewData(it)
        })
    }
}