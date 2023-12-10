package com.mateusz.jasiak.activetimespendingsystem.ui.fragments.map

import androidx.lifecycle.viewModelScope
import com.mateusz.jasiak.activetimespendingsystem.common.BaseViewModel
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.CoordinateDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.model.enums.ErrorCodeEnum
import com.mateusz.jasiak.activetimespendingsystem.domain.usecase.LoginUseCase
import com.mateusz.jasiak.activetimespendingsystem.domain.usecase.MapUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val mapUseCase: MapUseCase,
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {
    var allUserCoordinates: List<CoordinateDomain>? = null

    fun getUserByIdFromApi(idSocialMedia: String) {
        viewModelScope.launch {
            val response = loginUseCase.getUserByIdFromApi(idSocialMedia)
            when (response.isSuccessful) {
                true -> {
                    loggedUserData = response.data
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

    fun getCoordinatesFromApi() {
        viewModelScope.launch {
            val response = mapUseCase.getCoordinatesFromApi()
            when (response.isSuccessful) {
                true -> allUserCoordinates = response.data
                else -> {
                    when (response.errorResponse?.code) {
                        ErrorCodeEnum.NO_NETWORK -> baseAction.postValue(BaseAction.NoNetwork)
                        else -> baseAction.postValue(BaseAction.UnknownError)
                    }
                }
            }
        }
    }

    fun updateCoordinateByIdOnApi(idSocialMedia: String, coordinateDomain: CoordinateDomain) {
        viewModelScope.launch {
            val response = mapUseCase.updateCoordinateByIdOnApi(idSocialMedia, coordinateDomain)
            when (response.isSuccessful) {
                true -> {
                    // Do nothing
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

    fun deleteCoordinateByIdOnApi(idSocialMedia: String) {
        viewModelScope.launch {
            val response = mapUseCase.deleteCoordinateByIdOnApi(idSocialMedia)
            when (response.isSuccessful) {
                true -> {
                    // Do nothing
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
}