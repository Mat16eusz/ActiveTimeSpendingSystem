package com.mateusz.jasiak.activetimespendingsystem.di.modules.general

import androidx.lifecycle.ViewModelProvider
import com.mateusz.jasiak.activetimespendingsystem.di.factory.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module(includes = [AppModule::class])
abstract class ViewModelFactoryModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}