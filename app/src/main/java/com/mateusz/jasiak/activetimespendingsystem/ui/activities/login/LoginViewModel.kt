package com.mateusz.jasiak.activetimespendingsystem.ui.activities.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.mateusz.jasiak.activetimespendingsystem.common.BaseViewModel
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.RankingDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.UserDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.model.enums.ErrorCodeEnum
import com.mateusz.jasiak.activetimespendingsystem.domain.usecase.LoginUseCase
import com.mateusz.jasiak.activetimespendingsystem.domain.usecase.RankingUseCase
import com.mateusz.jasiak.activetimespendingsystem.utils.SCORE_ZERO
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val rankingUseCase: RankingUseCase
) : BaseViewModel() {
    val action = MutableLiveData<Action>()
    var userDomain = UserDomain()
    var isLogged: Boolean = false

    fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        when (task.isSuccessful) {
            true -> getAccountGoogleData(task.result)
            else -> {
                Log.e("GOOGLE ERROR", task.exception.toString())
                baseAction.postValue(BaseAction.UnknownError)
            }
        }
    }

    private fun getAccountGoogleData(account: GoogleSignInAccount) {
        userDomain.idSocialMedia = account.id
        userDomain.name = account.displayName
        userDomain.firstName = account.givenName
        userDomain.surname = account.familyName
        account.photoUrl?.let {
            userDomain.personPhoto = it.encodedPath.toString()
        }

        action.postValue(Action.GetAccountGoogleData)
    }

    fun getTokenFirebase() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener {
            if (!it.isSuccessful) {
                Log.e("TOKEN FCM ERROR", "Fetching FCM registration token failed", it.exception)
                baseAction.postValue(BaseAction.UnknownError)
                return@OnCompleteListener
            }

            userDomain.token = it.result
            Log.d("TOKEN FCM", it.result.toString())

            when (isLogged) {
                true -> updateAccountGoogleDataOnApi(userDomain)
                false -> action.postValue(Action.GetTokenFirebase)
            }
        })
    }

    private fun updateAccountGoogleDataOnApi(userDomain: UserDomain) {
        viewModelScope.launch {
            val response = loginUseCase.updateUserByIdOnApi(userDomain.idSocialMedia, userDomain)
            when (response.isSuccessful) {
                true -> {
                    if (isLogged) {
                        action.postValue(Action.StartActivity)
                    }
                }

                else -> {
                    when (response.errorResponse?.code) {
                        ErrorCodeEnum.NO_NETWORK -> baseAction.postValue(BaseAction.NoNetwork)
                        else -> baseAction.postValue(BaseAction.UnknownError)
                    }
                }
            }
        }
    }

    fun getUsersFromApiAndCheckUserFirstLogin() {
        viewModelScope.launch {
            val response = loginUseCase.getUsersFromApi()
            when (response.isSuccessful) {
                true -> {
                    var userIsRegister = false

                    response.data?.let {
                        for (user in it) {
                            if (user.idSocialMedia == userDomain.idSocialMedia) {
                                userIsRegister = true
                                break
                            }
                        }
                    }

                    when (userIsRegister) {
                        true -> {
                            updateAccountGoogleDataOnApi(userDomain)
                            action.postValue(Action.StartActivity)
                        }

                        false -> sendPlayerDataToApi(userDomain)
                    }
                }

                else -> {
                    when (response.errorResponse?.code) {
                        ErrorCodeEnum.NO_NETWORK -> baseAction.postValue(BaseAction.NoNetwork)
                        else -> baseAction.postValue(BaseAction.UnknownError)
                    }
                }
            }
        }
    }

    private fun sendPlayerDataToApi(userDomain: UserDomain) {
        viewModelScope.launch {
            val response = loginUseCase.addUserOnApi(userDomain)
            when (response.isSuccessful) {
                true -> addRankingOnApi(userDomain)

                else -> {
                    when (response.errorResponse?.code) {
                        ErrorCodeEnum.NO_NETWORK -> baseAction.postValue(BaseAction.NoNetwork)
                        else -> baseAction.postValue(BaseAction.UnknownError)
                    }
                }
            }
        }
    }

    private suspend fun addRankingOnApi(userDomain: UserDomain) {
        val rankingDomain = RankingDomain(
            userDomain.idSocialMedia,
            userDomain.name,
            SCORE_ZERO
        )
        val response = rankingUseCase.addRankingOnApi(rankingDomain)
        when (response.isSuccessful) {
            true -> action.postValue(Action.StartActivity)
            else -> {
                when (response.errorResponse?.code) {
                    ErrorCodeEnum.NO_NETWORK -> baseAction.postValue(BaseAction.NoNetwork)
                    else -> baseAction.postValue(BaseAction.UnknownError)
                }
            }
        }
    }

    sealed class Action {
        data object GetAccountGoogleData : Action()
        data object GetTokenFirebase : Action()
        data object StartActivity : Action()
    }
}