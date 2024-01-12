package com.mateusz.jasiak.activetimespendingsystem.di.modules.view_modules.ranking

import androidx.lifecycle.ViewModel
import com.mateusz.jasiak.activetimespendingsystem.di.modules.ViewModelKey
import com.mateusz.jasiak.activetimespendingsystem.ui.fragments.ranking.RankingFragment
import com.mateusz.jasiak.activetimespendingsystem.ui.fragments.ranking.RankingViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(
    includes = [
        RankingModule::class
    ]
)
abstract class RankingViewModule {
    @ContributesAndroidInjector
    abstract fun contributeRankingFragment(): RankingFragment

    @Binds
    @IntoMap
    @ViewModelKey(RankingViewModel::class)
    abstract fun bindRankingViewModel(viewModel: RankingViewModel): ViewModel
}