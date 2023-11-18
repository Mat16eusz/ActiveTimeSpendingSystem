package com.mateusz.jasiak.activetimespendingsystem.di.modules.view_modules.login

import androidx.lifecycle.ViewModel
import com.mateusz.jasiak.activetimespendingsystem.di.modules.ViewModelKey
import com.mateusz.jasiak.activetimespendingsystem.ui.login.LoginActivity
import com.mateusz.jasiak.activetimespendingsystem.ui.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(
    includes = [
        LoginModule::class
    ]
)
abstract class LoginViewModule {
    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel
}