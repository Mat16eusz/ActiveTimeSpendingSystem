package com.mateusz.jasiak.activetimespendingsystem.data.repository

import com.mateusz.jasiak.activetimespendingsystem.data.api.ApiApp
import com.mateusz.jasiak.activetimespendingsystem.domain.api.response.ApiResponse
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.RankingDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.repositories.RankingRepository

class RankingRepositoryImpl(
    private val apiApp: ApiApp
) : RankingRepository {
    override suspend fun getRankingsFromApi(): ApiResponse<List<RankingDomain>> {
        return handleRequest { apiApp.getRankingsFromApi() }
    }

    override suspend fun addRankingOnApi(rankingDomain: RankingDomain): ApiResponse<RankingDomain> {
        return handleRequest { apiApp.addRankingOnApi(rankingDomain) }
    }

    override suspend fun updateRankingByIdOnApi(
        id: String,
        rankingDomain: RankingDomain
    ): ApiResponse<RankingDomain> {
        return handleRequest { apiApp.updateRankingByIdOnApi(id, rankingDomain) }
    }
}