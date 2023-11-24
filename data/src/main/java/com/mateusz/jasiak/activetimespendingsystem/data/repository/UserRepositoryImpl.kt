package com.mateusz.jasiak.activetimespendingsystem.data.repository

import com.mateusz.jasiak.activetimespendingsystem.data.api.ApiApp
import com.mateusz.jasiak.activetimespendingsystem.domain.api.response.ApiResponse
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.UserDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.repositories.UserRepository

class UserRepositoryImpl constructor(
    private val apiApp: ApiApp
) : UserRepository {
    override suspend fun getUsers(): ApiResponse<List<UserDomain>> {
        return handleRequest { apiApp.getUsers() }
    }

    override suspend fun addUser(userDomain: UserDomain): ApiResponse<UserDomain> {
        return handleRequest { apiApp.addUser(userDomain) }
    }

    override suspend fun updateUserToken(
        id: String?,
        userDomain: UserDomain
    ): ApiResponse<UserDomain> {
        return handleRequest { apiApp.updateUserToken(id, userDomain) }
    }
}