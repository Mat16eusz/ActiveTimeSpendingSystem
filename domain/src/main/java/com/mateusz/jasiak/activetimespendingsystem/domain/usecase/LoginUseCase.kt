package com.mateusz.jasiak.activetimespendingsystem.domain.usecase

import com.mateusz.jasiak.activetimespendingsystem.domain.api.response.ApiResponse
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.UserDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.repositories.UserRepository

class LoginUseCase(
    private val userRepository: UserRepository
) {
    suspend fun getUsers(): ApiResponse<List<UserDomain>> {
        return userRepository.getUsers()
    }

    suspend fun addUser(userDomain: UserDomain): ApiResponse<UserDomain> {
        return userRepository.addUser(userDomain)
    }

    suspend fun updateUserToken(id: String?, userDomain: UserDomain): ApiResponse<UserDomain> {
        return userRepository.updateUserToken(id, userDomain)
    }
}