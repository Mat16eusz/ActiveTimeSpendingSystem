package com.mateusz.jasiak.activetimespendingsystem.di.modules.general

import com.mateusz.jasiak.activetimespendingsystem.data.api.ApiApp
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {
    @Provides
    @Singleton
    internal fun provideApiLogistic(retrofit: Retrofit): ApiApp =
        retrofit.create(ApiApp::class.java)
}