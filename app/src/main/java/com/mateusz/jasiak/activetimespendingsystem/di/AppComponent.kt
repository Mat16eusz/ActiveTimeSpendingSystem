package com.mateusz.jasiak.activetimespendingsystem.di

import com.mateusz.jasiak.activetimespendingsystem.ActiveTimeSpendingSystemApp
import com.mateusz.jasiak.activetimespendingsystem.di.modules.RepositoryModules
import com.mateusz.jasiak.activetimespendingsystem.di.modules.general.ApiModule
import com.mateusz.jasiak.activetimespendingsystem.di.modules.general.AppModule
import com.mateusz.jasiak.activetimespendingsystem.di.modules.view_modules.login.LoginViewModule
import com.mateusz.jasiak.activetimespendingsystem.di.modules.view_modules.main.MainViewModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        // General
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ApiModule::class,
        RepositoryModules::class,

        // ViewModule
        LoginViewModule::class,
        MainViewModule::class
    ]
)
interface AppComponent : AndroidInjector<ActiveTimeSpendingSystemApp> {
    @Component.Builder
    abstract class Builder :
        AndroidInjector.Factory<ActiveTimeSpendingSystemApp> {

        override fun create(application: ActiveTimeSpendingSystemApp): AppComponent {
            seedApplication(application)
            return build()
        }

        @BindsInstance
        internal abstract fun seedApplication(application: ActiveTimeSpendingSystemApp)

        internal abstract fun build(): AppComponent
    }
}