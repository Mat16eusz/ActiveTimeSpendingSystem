package com.mateusz.jasiak.activetimespendingsystem.di.modules.general

import com.mateusz.jasiak.activetimespendingsystem.BuildConfig
import com.mateusz.jasiak.activetimespendingsystem.data.api.ApiApp
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {
    @Provides
    @Singleton
    fun providersRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.LOCAL_URL)
        //return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    internal fun provideApiApp(retrofit: Retrofit): ApiApp =
        retrofit.create(ApiApp::class.java)
}