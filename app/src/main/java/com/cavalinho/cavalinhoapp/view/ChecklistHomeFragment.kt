package com.cavalinho.cavalinhoapp.view

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.cavalinho.cavalinhoapp.R
import com.cavalinho.cavalinhoapp.service.constants.UserConstants
import com.cavalinho.cavalinhoapp.service.repository.local.SecurityPreferences
import com.cavalinho.cavalinhoapp.viewmodel.ChecklistHomeViewModel
import com.google.android.material.snackbar.Snackbar

class ChecklistHomeFragment() : Fragment() {
    val TAG = "ChecklistHomeFragment"
    private lateinit var homeViewModel: ChecklistHomeViewModel
    private lateinit var loginUser: String
    private lateinit var password: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this).get(ChecklistHomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_checklist_home, container, false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        MainActivity.layoutOrder = 0

        val mPreferences = SecurityPreferences(root.context)
        loginUser = mPreferences.get(UserConstants.SHARED.USER_LOGIN)
        password = mPreferences.get(UserConstants.SHARED.USER_PASSWORD)
        observe(root)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, true) {
            //callback for back button, don't do anything.
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btn_formchecklist).setOnClickListener {
            findNavController().navigate(R.id.action_nav_checklist_home_to_nav_checklist_question)
        }
    }

    fun observe(v: View) {
        homeViewModel.cleanSendOk()
        if (!homeViewModel.cleanNotConfirmed() && homeViewModel.enableSendButton()) {
            homeViewModel.sendAnswers(loginUser, password)
        }

        homeViewModel.send.observe(viewLifecycleOwner, Observer {
            if (it != "") {
                Snackbar.make(v, it, Snackbar.LENGTH_SHORT).setAction("Action", null).show()
            }
        })
    }

}
