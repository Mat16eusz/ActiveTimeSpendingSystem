package com.mateusz.jasiak.activetimespendingsystem.data.repository

import com.mateusz.jasiak.activetimespendingsystem.data.api.ApiApp
import com.mateusz.jasiak.activetimespendingsystem.domain.api.response.ApiResponse
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.CoordinateDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.repositories.CoordinateRepository

class CoordinateRepositoryImpl(
    private val apiApp: ApiApp
) : CoordinateRepository {
    override suspend fun getCoordinatesFromApi(): ApiResponse<List<CoordinateDomain>> {
        return handleRequest { apiApp.getCoordinatesFromApi() }
    }

    override suspend fun updateCoordinateByIdOnApi(
        id: String,
        coordinateDomain: CoordinateDomain
    ): ApiResponse<CoordinateDomain> {
        return handleRequest { apiApp.updateCoordinateByIdOnApi(id, coordinateDomain) }
    }

    override suspend fun deleteCoordinateByIdOnApi(id: String): ApiResponse<CoordinateDomain> {
        return handleRequest { apiApp.deleteCoordinateByIdOnApi(id) }
    }
}