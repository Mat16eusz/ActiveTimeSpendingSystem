package com.mateusz.jasiak.activetimespendingsystem.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val baseAction = MutableLiveData<BaseAction>()

    sealed class BaseAction {
        data object NoNetwork : BaseAction()
        data object UnknownError : BaseAction()
    }
}