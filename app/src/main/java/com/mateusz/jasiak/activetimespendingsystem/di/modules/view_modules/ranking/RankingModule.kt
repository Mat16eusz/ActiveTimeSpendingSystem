package com.mateusz.jasiak.activetimespendingsystem.di.modules.view_modules.ranking

import com.mateusz.jasiak.activetimespendingsystem.domain.usecase.RankingUseCase
import dagger.Module
import dagger.Provides

@Module
class RankingModule {
    @Provides
    fun provideRankingUseCase(): RankingUseCase = RankingUseCase()
}