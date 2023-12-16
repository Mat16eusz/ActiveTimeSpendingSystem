package com.mateusz.jasiak.activetimespendingsystem.data.api

import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.CoordinateDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.RankingDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.UserDomain
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiApp {
    @GET("users")
    suspend fun getUsersFromApi(): Response<List<UserDomain>>

    @GET("user/{id}")
    suspend fun getUserByIdFromApi(
        @Path("id") idSocialMedia: String
    ): Response<UserDomain>

    @POST("user")
    suspend fun addUserOnApi(@Body userDomain: UserDomain): Response<UserDomain>

    @PUT("user/{id}")
    suspend fun updateUserByIdOnApi(
        @Path("id") id: String?,
        @Body userDomain: UserDomain
    ): Response<UserDomain>

    @GET("coordinates")
    suspend fun getCoordinatesFromApi(): Response<List<CoordinateDomain>>

    @PUT("coordinate/{id}")
    suspend fun updateCoordinateByIdOnApi(
        @Path("id") idSocialMedia: String,
        @Body coordinateDomain: CoordinateDomain
    ): Response<CoordinateDomain>

    @DELETE("coordinate/{id}")
    suspend fun deleteCoordinateByIdOnApi(
        @Path("id") idSocialMedia: String
    ): Response<CoordinateDomain>

    @GET("rankings")
    suspend fun getRankingsFromApi(): Response<List<RankingDomain>>

    @POST("ranking")
    suspend fun addRankingOnApi(@Body rankingDomain: RankingDomain): Response<RankingDomain>

    @PUT("ranking/{id}")
    suspend fun updateRankingByIdOnApi(
        @Path("id") idSocialMedia: String,
        @Body rankingDomain: RankingDomain
    ): Response<RankingDomain>
}