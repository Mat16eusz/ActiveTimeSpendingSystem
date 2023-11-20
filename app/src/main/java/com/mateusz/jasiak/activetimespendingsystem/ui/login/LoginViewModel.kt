package com.mateusz.jasiak.activetimespendingsystem.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.mateusz.jasiak.activetimespendingsystem.common.BaseViewModel
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.UserDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.model.enums.ErrorCode
import com.mateusz.jasiak.activetimespendingsystem.domain.usecase.LoginUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {
    private var userDomain = UserDomain()
    val action = MutableLiveData<Action>()
    var isLogged: Boolean = false

    fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result

            account?.let {
                getAccountGoogleData(account)
            }
        } else {
            Log.e("GOOGLE ERROR", task.exception.toString())
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
                return@OnCompleteListener
            }

            userDomain.token = it.result
            Log.d("TOKEN FCM", it.result.toString())

            when (isLogged) {
                true -> updateAccountGoogleData(userDomain)
                false -> action.postValue(Action.GetTokenFirebase)
            }
        })
    }

    private fun updateAccountGoogleData(userDomain: UserDomain) {
        viewModelScope.launch {
            val response = loginUseCase.updateUserToken(userDomain.idSocialMedia, userDomain)
            when (response.isSuccessful) {
                true -> {
                    if (isLogged) {
                        action.postValue(Action.StartActivity)
                    }
                }

                else -> {
                    when (response.errorResponse?.code) {
                        ErrorCode.NO_NETWORK -> {
                            // TODO: Add implement
                        }

                        else -> {
                            // TODO: Add implement
                        }
                    }
                }
            }
        }
    }

    fun getUsersFromApiAndCheckUserFirstLogin() {
        viewModelScope.launch {
            val response = loginUseCase.getUsers()
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
                            updateAccountGoogleData(userDomain)
                            action.postValue(Action.StartActivity)
                        }

                        false -> sendPlayerDataToApi(userDomain)
                    }
                }

                else -> {
                    when (response.errorResponse?.code) {
                        ErrorCode.NO_NETWORK -> {
                            // TODO: Add implement
                        }

                        else -> {
                            // TODO: Add implement
                        }
                    }
                }
            }
        }
    }

    private fun sendPlayerDataToApi(userDomain: UserDomain) {
        viewModelScope.launch {
            val response = loginUseCase.addUser(userDomain)
            when (response.isSuccessful) {
                true -> action.postValue(Action.StartActivity)

                else -> {
                    when (response.errorResponse?.code) {
                        ErrorCode.NO_NETWORK -> {
                            // TODO: Add implement
                        }

                        else -> {
                            // TODO: Add implement
                        }
                    }
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