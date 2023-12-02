package com.mateusz.jasiak.activetimespendingsystem.common

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mateusz.jasiak.activetimespendingsystem.R
import com.mateusz.jasiak.activetimespendingsystem.di.factory.ViewModelFactory
import com.mateusz.jasiak.activetimespendingsystem.utils.or
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

        initObserver()
    }

    private fun initObserver() {
        viewModel?.baseAction?.observe(this) {
            when (it) {
                is BaseViewModel.BaseAction.NoNetwork -> {
                    showInfoDialog(getString(R.string.no_network))
                }

                is BaseViewModel.BaseAction.UnknownError -> {
                    showInfoDialog(getString(R.string.unknown_error))
                }

                else -> {
                    // Do nothing
                }
            }
        }
    }

    protected fun showInfoDialog(message: String?) {
        BaseDialog.openDialog(
            fragmentManager = supportFragmentManager,
            title = message.or(getString(R.string.unknown_error))
        )
    }

    protected fun showInfoToast(message: String?) {
        message?.let {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

    protected fun openFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction().apply {
                this.replace(R.id.constraint_layout_child, fragment, tag)
                this.addToBackStack(null)
            }.commit()
    }
}