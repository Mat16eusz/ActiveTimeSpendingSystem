package com.mateusz.jasiak.activetimespendingsystem.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.UserDomain

abstract class BaseViewModel : ViewModel() {
    val baseAction = MutableLiveData<BaseAction>()
    var idSocialMedia: String? = null
    var loggedUserData: UserDomain? = null

    sealed class BaseAction {
        data object NoNetwork : BaseAction()
        data object UnknownError : BaseAction()
    }
}