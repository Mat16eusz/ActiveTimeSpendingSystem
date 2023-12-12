package com.mateusz.jasiak.activetimespendingsystem.domain.repositories

import com.mateusz.jasiak.activetimespendingsystem.domain.api.response.ApiResponse
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.UserDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.repositories.base.BaseRepository

interface UserRepository : BaseRepository {
    suspend fun getUsersFromApi(): ApiResponse<List<UserDomain>>

    suspend fun getUserByIdFromApi(idSocialMedia: String): ApiResponse<UserDomain>

    suspend fun addUserOnApi(userDomain: UserDomain): ApiResponse<UserDomain>

    suspend fun updateUserByIdOnApi(id: String?, userDomain: UserDomain): ApiResponse<UserDomain>
}