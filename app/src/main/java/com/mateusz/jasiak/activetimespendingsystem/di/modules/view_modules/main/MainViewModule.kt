package com.mateusz.jasiak.activetimespendingsystem.di.modules.view_modules.main

import androidx.lifecycle.ViewModel
import com.mateusz.jasiak.activetimespendingsystem.di.modules.ViewModelKey
import com.mateusz.jasiak.activetimespendingsystem.di.modules.view_modules.login.LoginModule
import com.mateusz.jasiak.activetimespendingsystem.ui.activities.main.MainActivity
import com.mateusz.jasiak.activetimespendingsystem.ui.activities.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(
    includes = [
        LoginModule::class
    ]
)
abstract class MainViewModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}