package com.mateusz.jasiak.activetimespendingsystem.domain.usecase

import com.mateusz.jasiak.activetimespendingsystem.domain.api.response.ApiResponse
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.CoordinateDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.repositories.CoordinateRepository

class MapUseCase(
    private val coordinateRepository: CoordinateRepository
) {
    suspend fun getCoordinatesFromApi(): ApiResponse<List<CoordinateDomain>> {
        return coordinateRepository.getCoordinatesFromApi()
    }

    suspend fun updateCoordinateByIdOnApi(
        id: String,
        coordinateDomain: CoordinateDomain
    ): ApiResponse<CoordinateDomain> {
        return coordinateRepository.updateCoordinateByIdOnApi(id, coordinateDomain)
    }

    suspend fun deleteCoordinateByIdOnApi(id: String): ApiResponse<CoordinateDomain> {
        return coordinateRepository.deleteCoordinateByIdOnApi(id)
    }
}