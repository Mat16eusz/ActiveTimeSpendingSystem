package com.mateusz.jasiak.activetimespendingsystem.di.modules.view_modules.map

import androidx.lifecycle.ViewModel
import com.mateusz.jasiak.activetimespendingsystem.di.modules.ViewModelKey
import com.mateusz.jasiak.activetimespendingsystem.ui.fragments.map.MapFragment
import com.mateusz.jasiak.activetimespendingsystem.ui.fragments.map.MapViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(
    includes = [
        MapModule::class
    ]
)
abstract class MapViewModule {
    @ContributesAndroidInjector
    abstract fun contributeMapFragment(): MapFragment

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    abstract fun bindMapViewModel(viewModel: MapViewModel): ViewModel
}