package com.cavalinho.cavalinhoapp.view

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cavalinho.cavalinhoapp.R
import com.cavalinho.cavalinhoapp.R.id.btn_next_question
import com.cavalinho.cavalinhoapp.R.id.text_type_question
import com.cavalinho.cavalinhoapp.service.constants.UserConstants
import com.cavalinho.cavalinhoapp.service.repository.local.SecurityPreferences
import com.cavalinho.cavalinhoapp.view.adapter.QuestionsAdapter
import com.cavalinho.cavalinhoapp.viewmodel.ChecklistQuestionsViewModel
import com.google.android.material.snackbar.Snackbar

class ChecklistQuestionsFragment() : Fragment() {
    val TAG = "ChecklistQuestionsFragmentTag"
    private lateinit var questionsViewmodel: ChecklistQuestionsViewModel
    private lateinit var loginUser: String
    private val questionAdapter: QuestionsAdapter = QuestionsAdapter()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        questionsViewmodel = ViewModelProviders.of(this).get(ChecklistQuestionsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_checklist_questions, container, false)
        val recycler = root.findViewById<RecyclerView>(R.id.recycler_questions)
        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = questionAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
        root.findViewById<TextView>(text_type_question).text = when (MainActivity.layoutOrder) {
            0, 1 -> getString(R.string.txt_type1)
            2 -> getString(R.string.txt_type2)
            3 -> getString(R.string.txt_type3)
            4 -> getString(R.string.txt_type4)
            5 -> getString(R.string.txt_type5)
            else -> "Tipo não econtrado"
        }.toString()

        if (MainActivity.layoutOrder == 4) {
            root.findViewById<Button>(btn_next_question).text = getString(R.string.btn_txt_save)
        } else {
            root.findViewById<Button>(btn_next_question).text = getString(R.string.btn_txt_next)
        }

        val mPreferences = SecurityPreferences(root.context)
        loginUser = mPreferences.get(UserConstants.SHARED.USER_LOGIN)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, true) {
            Log.d(TAG, "back is working (y)")
            MainActivity.layoutOrder--
            NavHostFragment.findNavController(this@ChecklistQuestionsFragment).navigateUp()
        }
        observe()
        return root
    }

    private fun observe() {
        if (MainActivity.layoutOrder == 0) {
            questionsViewmodel.insertAnswers(loginUser)
            MainActivity.layoutOrder = 1
        }
        questionsViewmodel.loadQuestionList(MainActivity.layoutOrder)
        questionsViewmodel.questionList.observe(viewLifecycleOwner, Observer {
            questionAdapter.loadNewData(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(btn_next_question).setOnClickListener {
            if (questionsViewmodel.updateAnswersSelected(this.questionAdapter)) {
                if (MainActivity.layoutOrder == 4) {
                    questionsViewmodel.confirmAnswers()
                    Snackbar.make(view, "Respostas salvas", Snackbar.LENGTH_SHORT).setAction("Action", null).show()
                    findNavController().navigate(R.id.action_nav_checklist_questions_to_nav_checklist_home)
                } else {
                    findNavController().navigate(R.id.action_nav_checklist_questions_to_itself)
                    MainActivity.layoutOrder++
                }
            } else {
                val toast = Toast.makeText(context, "Por favor, responda todas as questões.", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP, 0, 0)
                toast.show()
            }
        }
        view.findViewById<Button>(R.id.btn_previous_question).setOnClickListener {
            MainActivity.layoutOrder--
            NavHostFragment.findNavController(this@ChecklistQuestionsFragment).navigateUp()
        }
    }
}