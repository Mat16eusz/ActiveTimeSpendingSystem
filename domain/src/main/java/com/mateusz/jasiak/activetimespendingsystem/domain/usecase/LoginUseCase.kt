package com.mateusz.jasiak.activetimespendingsystem.domain.usecase

import com.mateusz.jasiak.activetimespendingsystem.domain.api.response.ApiResponse
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.UserDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.repositories.UserRepository

class LoginUseCase(
    private val userRepository: UserRepository
) {
    suspend fun getUsersFromApi(): ApiResponse<List<UserDomain>> {
        return userRepository.getUsersFromApi()
    }

    suspend fun getUserByIdFromApi(idSocialMedia: String): ApiResponse<UserDomain> {
        return userRepository.getUserByIdFromApi(idSocialMedia)
    }

    suspend fun addUserOnApi(userDomain: UserDomain): ApiResponse<UserDomain> {
        return userRepository.addUserOnApi(userDomain)
    }

    suspend fun updateUserByIdOnApi(id: String?, userDomain: UserDomain): ApiResponse<UserDomain> {
        return userRepository.updateUserByIdOnApi(id, userDomain)
    }
}