package com.mateusz.jasiak.activetimespendingsystem.common

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mateusz.jasiak.activetimespendingsystem.di.factory.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var factory: ViewModelFactory

    protected open val viewModel: BaseViewModel? = null

    fun <T : ViewModel> viewModelOf(viewModelClass: KClass<T>) =
        ViewModelProvider(this, factory)[viewModelClass.java]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}