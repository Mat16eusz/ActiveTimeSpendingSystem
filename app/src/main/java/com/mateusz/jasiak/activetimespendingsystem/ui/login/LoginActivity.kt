package com.mateusz.jasiak.activetimespendingsystem.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.mateusz.jasiak.activetimespendingsystem.R
import com.mateusz.jasiak.activetimespendingsystem.common.BaseActivity
import com.mateusz.jasiak.activetimespendingsystem.databinding.ActivityLoginBinding
import com.mateusz.jasiak.activetimespendingsystem.ui.main.MainActivity
import com.mateusz.jasiak.activetimespendingsystem.utils.LOGGED_KEY
import com.mateusz.jasiak.activetimespendingsystem.utils.SHARED_PREFERENCES

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    override val viewModel: LoginViewModel by lazy {
        viewModelOf(LoginViewModel::class)
    }

    private lateinit var googleSignInClient: GoogleSignInClient
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            when (it.resultCode == Activity.RESULT_OK) {
                true -> {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                    viewModel.handleSignInResult(task)
                }

                else -> showInfoDialog(getString(R.string.wrong_logged_in))
            }
        }

    companion object {
        fun start(activity: Activity) =
            activity.startActivity(Intent(activity, MainActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        binding.signInButton.setSize(SignInButton.SIZE_WIDE)

        if (loadDataLogged()) {
            viewModel.isLogged = true
            signInGoogle()
        }

        binding.signInButton.setOnClickListener {
            signInGoogle()
        }

        initObserver()
    }

    private fun initObserver() {
        viewModel.action.observe(this) {
            when (it) {
                is LoginViewModel.Action.GetAccountGoogleData -> {
                    viewModel.getTokenFirebase()
                }

                is LoginViewModel.Action.GetTokenFirebase -> {
                    viewModel.getUsersFromApiAndCheckUserFirstLogin()
                }

                is LoginViewModel.Action.StartActivity -> {
                    if (!loadDataLogged()) {
                        saveDataLogged()
                    }
                    start(this)
                }

                else -> {
                    // Do nothing
                }
            }
        }
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private fun saveDataLogged() {
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(LOGGED_KEY, true)
        editor.apply()
    }

    private fun loadDataLogged(): Boolean {
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)
        return sharedPreferences.getBoolean(LOGGED_KEY, false)
    }
}