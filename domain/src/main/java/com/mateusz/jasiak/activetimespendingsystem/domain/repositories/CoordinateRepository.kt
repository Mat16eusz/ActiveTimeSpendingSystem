package com.mateusz.jasiak.activetimespendingsystem.domain.repositories

import com.mateusz.jasiak.activetimespendingsystem.domain.api.response.ApiResponse
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.CoordinateDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.repositories.base.BaseRepository

interface CoordinateRepository : BaseRepository {
    suspend fun getCoordinatesFromApi(): ApiResponse<List<CoordinateDomain>>

    suspend fun updateCoordinateByIdOnApi(
        id: String,
        coordinateDomain: CoordinateDomain
    ): ApiResponse<CoordinateDomain>

    suspend fun deleteCoordinateByIdOnApi(id: String): ApiResponse<CoordinateDomain>
}