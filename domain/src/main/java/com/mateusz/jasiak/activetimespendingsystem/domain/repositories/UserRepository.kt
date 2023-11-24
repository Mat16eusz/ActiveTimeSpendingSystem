package com.mateusz.jasiak.activetimespendingsystem.domain.repositories

import com.mateusz.jasiak.activetimespendingsystem.domain.api.response.ApiResponse
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.UserDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.repositories.base.BaseRepository

interface UserRepository : BaseRepository {
    suspend fun getUsers(): ApiResponse<List<UserDomain>>

    suspend fun addUser(userDomain: UserDomain): ApiResponse<UserDomain>

    suspend fun updateUserToken(id: String?, userDomain: UserDomain): ApiResponse<UserDomain>
}