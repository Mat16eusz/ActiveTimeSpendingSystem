package com.mateusz.jasiak.activetimespendingsystem.common

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mateusz.jasiak.activetimespendingsystem.di.factory.ViewModelFactory
import com.mateusz.jasiak.activetimespendingsystem.utils.EMPTY_STRING
import com.mateusz.jasiak.activetimespendingsystem.utils.ID_SOCIAL_MEDIA_KEY
import com.mateusz.jasiak.activetimespendingsystem.utils.SHARED_PREFERENCES
import dagger.android.support.DaggerAppCompatActivity
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

    protected fun loadDataLogged(): String? {
        val sharedPreferences = context?.getSharedPreferences(
            SHARED_PREFERENCES,
            DaggerAppCompatActivity.MODE_PRIVATE
        )
        return sharedPreferences?.getString(ID_SOCIAL_MEDIA_KEY, EMPTY_STRING)
    }
}