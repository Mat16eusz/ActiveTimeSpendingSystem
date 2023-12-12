package com.mateusz.jasiak.activetimespendingsystem.data.repository

import com.mateusz.jasiak.activetimespendingsystem.data.api.ApiApp
import com.mateusz.jasiak.activetimespendingsystem.domain.api.response.ApiResponse
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.UserDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.repositories.UserRepository

class UserRepositoryImpl(
    private val apiApp: ApiApp
) : UserRepository {
    override suspend fun getUsersFromApi(): ApiResponse<List<UserDomain>> {
        return handleRequest { apiApp.getUsersFromApi() }
    }

    override suspend fun getUserByIdFromApi(idSocialMedia: String): ApiResponse<UserDomain> {
        return handleRequest { apiApp.getUserByIdFromApi(idSocialMedia) }
    }

    override suspend fun addUserOnApi(userDomain: UserDomain): ApiResponse<UserDomain> {
        return handleRequest { apiApp.addUserOnApi(userDomain) }
    }

    override suspend fun updateUserByIdOnApi(
        id: String?,
        userDomain: UserDomain
    ): ApiResponse<UserDomain> {
        return handleRequest { apiApp.updateUserByIdOnApi(id, userDomain) }
    }
}