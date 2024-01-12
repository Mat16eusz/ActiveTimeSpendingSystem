package com.mateusz.jasiak.activetimespendingsystem.di.modules.view_modules.map

import com.mateusz.jasiak.activetimespendingsystem.domain.repositories.CoordinateRepository
import com.mateusz.jasiak.activetimespendingsystem.domain.usecase.MapUseCase
import dagger.Module
import dagger.Provides

@Module
class MapModule {
    @Provides
    fun provideMapUseCase(
        coordinateRepository: CoordinateRepository
    ): MapUseCase = MapUseCase(
        coordinateRepository = coordinateRepository
    )
}