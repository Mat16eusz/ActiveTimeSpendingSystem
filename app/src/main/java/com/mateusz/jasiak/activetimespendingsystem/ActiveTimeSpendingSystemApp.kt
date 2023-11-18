package com.mateusz.jasiak.activetimespendingsystem

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import com.mateusz.jasiak.activetimespendingsystem.di.DaggerAppComponent

class ActiveTimeSpendingSystemApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}