package com.mateusz.jasiak.activetimespendingsystem.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.mateusz.jasiak.activetimespendingsystem.R
import com.mateusz.jasiak.activetimespendingsystem.common.BaseActivity
import com.mateusz.jasiak.activetimespendingsystem.databinding.ActivityMainBinding
import com.mateusz.jasiak.activetimespendingsystem.ui.login.LoginActivity
import com.mateusz.jasiak.activetimespendingsystem.utils.LOGGED_KEY
import com.mateusz.jasiak.activetimespendingsystem.utils.SHARED_PREFERENCES

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    override val viewModel: MainViewModel by lazy {
        viewModelOf(MainViewModel::class)
    }

    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        fun start(activity: Activity) =
            activity.startActivity(Intent(activity, LoginActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        showInfoToast(getString(R.string.logged_in))

        binding.signOutButton.setOnClickListener {
            googleSignInClient.signOut().addOnCompleteListener {
                when (it.isSuccessful) {
                    true -> {
                        saveDataLogged()
                        showInfoToast(getString(R.string.logged_out))
                        start(this)
                    }

                    else -> showInfoToast(getString(R.string.wrong_logout))
                }
            }
        }
    }

    private fun saveDataLogged() {
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(LOGGED_KEY, false)
        editor.apply()
    }
}