package com.mateusz.jasiak.activetimespendingsystem.ui.fragments.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mateusz.jasiak.activetimespendingsystem.common.BaseViewModel
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.CoordinateDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.RankingDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.model.enums.ErrorCodeEnum
import com.mateusz.jasiak.activetimespendingsystem.domain.usecase.LoginUseCase
import com.mateusz.jasiak.activetimespendingsystem.domain.usecase.MapUseCase
import com.mateusz.jasiak.activetimespendingsystem.domain.usecase.RankingUseCase
import com.mateusz.jasiak.activetimespendingsystem.utils.SCORE_ZERO
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val mapUseCase: MapUseCase,
    private val loginUseCase: LoginUseCase,
    private val rankingUseCase: RankingUseCase
) : BaseViewModel() {
    val action = MutableLiveData<Action>()
    var allUserCoordinates: List<CoordinateDomain>? = null
    var timestampStart: Long? = null
    var timestampEnd: Long? = null

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
                true -> {
                    allUserCoordinates = response.data
                    action.postValue(Action.SuccessGetCoordinateData)
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

    private fun calculateTime(): Long {
        timestampEnd?.let { timestampEnd ->
            timestampStart?.let { timestampStart ->
                return timestampEnd.minus(timestampStart)
            }
        }
        return SCORE_ZERO
    }

    fun updateRankingByIdOnApi(idSocialMedia: String) {
        viewModelScope.launch {
            val score = calculateTime()
            timestampStart = null
            timestampEnd = null
            val rankingDomain = RankingDomain(
                idSocialMedia,
                null,
                score
            )

            val response = rankingUseCase.updateRankingByIdOnApi(idSocialMedia, rankingDomain)
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

    sealed class Action {
        data object SuccessGetCoordinateData : Action()
    }
}