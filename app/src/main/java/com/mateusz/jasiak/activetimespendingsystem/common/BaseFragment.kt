package com.mateusz.jasiak.activetimespendingsystem.common

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mateusz.jasiak.activetimespendingsystem.di.factory.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseFragment : DaggerFragment() {
    @Inject
    lateinit var factory: ViewModelFactory

    protected open val viewModel: BaseViewModel? = null

    fun <T : ViewModel> viewModelOf(viewModelClass: KClass<T>) =
        ViewModelProvider(this, factory)[viewModelClass.java]

    fun <T : ViewModel, PF : Fragment> viewModelOf(
        parentFragment: PF,
        viewModelClass: KClass<T>
    ) = ViewModelProvider(parentFragment, factory)[viewModelClass.java]

    fun <T : ViewModel> viewModelOfActivity(viewModelClass: KClass<T>) =
        ViewModelProvider(requireActivity(), factory)[viewModelClass.java]
}