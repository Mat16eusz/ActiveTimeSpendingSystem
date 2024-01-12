package com.mateusz.jasiak.activetimespendingsystem.ui.fragments.ranking

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mateusz.jasiak.activetimespendingsystem.common.BaseViewModel
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.RankingDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.model.enums.ErrorCodeEnum
import com.mateusz.jasiak.activetimespendingsystem.domain.usecase.RankingUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class RankingViewModel @Inject constructor(
    private val rankingUseCase: RankingUseCase,
) : BaseViewModel() {
    val action = MutableLiveData<Action>()
    var rankingList: List<RankingDomain>? = null

    fun getRankingsFromApi() {
        viewModelScope.launch {
            val response = rankingUseCase.getRankingsFromApi()
            when (response.isSuccessful) {
                true -> {
                    rankingList = response.data
                    rankingList = rankingList?.sortedByDescending {
                        it.score
                    }
                    action.postValue(Action.GetRankingsData)
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
        data object GetRankingsData : Action()
    }
}