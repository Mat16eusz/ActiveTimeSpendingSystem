package com.mateusz.jasiak.activetimespendingsystem.di.modules.general

import android.app.Application
import com.mateusz.jasiak.activetimespendingsystem.ActiveTimeSpendingSystemApp
import dagger.Binds
import dagger.Module

@Module
internal abstract class AppModule {
    @Binds
    abstract fun bindApplication(application: ActiveTimeSpendingSystemApp): Application
}