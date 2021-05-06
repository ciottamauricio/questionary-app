package com.cavalinho.cavalinhoapp.view

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cavalinho.cavalinhoapp.R
import com.cavalinho.cavalinhoapp.service.constants.UserConstants
import com.cavalinho.cavalinhoapp.service.repository.local.SecurityPreferences
import com.cavalinho.cavalinhoapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


private const val TAG = "MainActivityTag"

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var loginUser: String
    private lateinit var password: String

    companion object {
        var layoutOrder = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val mPreferences = SecurityPreferences(this)
        loginUser = mPreferences.get(UserConstants.SHARED.USER_LOGIN)
        password = mPreferences.get(UserConstants.SHARED.USER_PASSWORD)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setupNavigation()
        observe(loginUser, password)
    }

    override fun onResume() {
        mainViewModel.loadUserName()
        super.onResume()
    }

    private fun setupNavigation() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_checklist_home,
                R.id.nav_messages_all
                //,R.id.OtherFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        btn_closeApp.setOnClickListener(){
            finishAffinity()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        if (layoutOrder > 0) {
            layoutOrder--
        }
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun observe(loginUser: String, password: String) {
        mainViewModel.downloadData(loginUser, password)

        mainViewModel.getAllQuestions().forEach {
            Log.d(TAG, "Question: ${it.question} Type: ${it.type}")
        }

        mainViewModel.userName.observe(this, Observer {
            val navView = findViewById<NavigationView>(R.id.nav_view)
            val header = navView.getHeaderView(0)
            header.findViewById<TextView>(R.id.nav_header_label).text = "Olá $it, seja bem vindo!"
        })
    }

}
