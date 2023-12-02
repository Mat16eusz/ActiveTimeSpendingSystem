package com.mateusz.jasiak.activetimespendingsystem.data.api

import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.UserDomain
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiApp {
    @GET("users")
    suspend fun getUsers(): Response<List<UserDomain>>

    @POST("users")
    suspend fun addUser(@Body userDomain: UserDomain): Response<UserDomain>

    @PUT("users/{id}")
    suspend fun updateUserToken(
        @Path("id") id: String?,
        @Body userDomain: UserDomain
    ): Response<UserDomain>
}