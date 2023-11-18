package com.mateusz.jasiak.activetimespendingsystem.ui.login

import android.os.Bundle
import com.mateusz.jasiak.activetimespendingsystem.R
import com.mateusz.jasiak.activetimespendingsystem.common.BaseActivity
import com.mateusz.jasiak.activetimespendingsystem.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    override val viewModel: LoginViewModel by lazy {
        viewModelOf(LoginViewModel::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}