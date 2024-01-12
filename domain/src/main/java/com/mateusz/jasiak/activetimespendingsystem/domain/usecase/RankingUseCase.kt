package com.mateusz.jasiak.activetimespendingsystem.domain.usecase

import com.mateusz.jasiak.activetimespendingsystem.domain.api.response.ApiResponse
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.RankingDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.repositories.RankingRepository

class RankingUseCase(
    private val rankingRepository: RankingRepository
) {
    suspend fun getRankingsFromApi(): ApiResponse<List<RankingDomain>> {
        return rankingRepository.getRankingsFromApi()
    }

    suspend fun addRankingOnApi(rankingDomain: RankingDomain): ApiResponse<RankingDomain> {
        return rankingRepository.addRankingOnApi(rankingDomain)
    }

    suspend fun updateRankingByIdOnApi(
        id: String,
        rankingDomain: RankingDomain
    ): ApiResponse<RankingDomain> {
        return rankingRepository.updateRankingByIdOnApi(id, rankingDomain)
    }
}