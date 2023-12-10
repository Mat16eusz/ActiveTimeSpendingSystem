package com.mateusz.jasiak.activetimespendingsystem.di.modules

import com.mateusz.jasiak.activetimespendingsystem.data.api.ApiApp
import com.mateusz.jasiak.activetimespendingsystem.data.repository.CoordinateRepositoryImpl
import com.mateusz.jasiak.activetimespendingsystem.data.repository.UserRepositoryImpl
import com.mateusz.jasiak.activetimespendingsystem.domain.repositories.CoordinateRepository
import com.mateusz.jasiak.activetimespendingsystem.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModules {
    @Provides
    internal fun provideUserRepositoryImpl(
        apiApp: ApiApp
    ): UserRepository = UserRepositoryImpl(apiApp)

    @Provides
    internal fun provideCoordinateRepositoryImpl(
        apiApp: ApiApp
    ): CoordinateRepository = CoordinateRepositoryImpl(apiApp)
}