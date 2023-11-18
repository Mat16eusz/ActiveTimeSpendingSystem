package com.mateusz.jasiak.activetimespendingsystem.ui.login

import com.mateusz.jasiak.activetimespendingsystem.common.BaseViewModel
import com.mateusz.jasiak.activetimespendingsystem.domain.usecase.LoginUseCase
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

}