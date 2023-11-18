package com.mateusz.jasiak.activetimespendingsystem.di.modules.view_modules.login

import com.mateusz.jasiak.activetimespendingsystem.domain.usecase.LoginUseCase
import dagger.Module
import dagger.Provides

@Module
class LoginModule {
    @Provides
    fun provideLoginUseCase(): LoginUseCase = LoginUseCase()
}