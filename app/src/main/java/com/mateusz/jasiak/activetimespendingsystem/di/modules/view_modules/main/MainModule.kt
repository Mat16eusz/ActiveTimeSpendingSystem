package com.mateusz.jasiak.activetimespendingsystem.di.modules.view_modules.main

import com.mateusz.jasiak.activetimespendingsystem.domain.usecase.MainUseCase
import dagger.Module
import dagger.Provides

@Module
class MainModule {
    @Provides
    fun provideMainUseCase(): MainUseCase = MainUseCase()
}