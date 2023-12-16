package com.mateusz.jasiak.activetimespendingsystem.domain.repositories

import com.mateusz.jasiak.activetimespendingsystem.domain.api.response.ApiResponse
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.RankingDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.repositories.base.BaseRepository

interface RankingRepository : BaseRepository {
    suspend fun getRankingsFromApi(): ApiResponse<List<RankingDomain>>

    suspend fun addRankingOnApi(rankingDomain: RankingDomain): ApiResponse<RankingDomain>

    suspend fun updateRankingByIdOnApi(
        id: String,
        rankingDomain: RankingDomain
    ): ApiResponse<RankingDomain>
}