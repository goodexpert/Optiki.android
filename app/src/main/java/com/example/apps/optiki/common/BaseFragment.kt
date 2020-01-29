package com.example.apps.optiki.common

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.apps.optiki.AppComponent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ClassCastException

abstract class BaseFragment : Fragment() {

    private var compositeDisposable: CompositeDisposable? = null
    private var fragmentInterface: FragmentInterface? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            fragmentInterface = context as FragmentInterface
        } catch (ex: ClassCastException) {
            throw NotImplementedError("FragmentInterface not implemented")
        }
    }

    override fun onDetach() {
        fragmentInterface?.let {
            fragmentInterface = null
        }
        cleanup()
        super.onDetach()
    }

    @Synchronized
    private fun getCompositeDisposable(): CompositeDisposable {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        return compositeDisposable!!
    }

    fun addDisposable(disposable: Disposable) {
        getCompositeDisposable().add(disposable)
    }

    @Synchronized
    fun cleanup() {
        compositeDisposable?.let { disposable ->
            if (disposable.isDisposed) disposable.dispose()
        }
    }

    protected fun getInterface(): FragmentInterface? {
        return fragmentInterface
    }

    fun getAppInjector(): AppComponent? {
        return getInterface()?.getAppInjector()
    }

    fun setDisplayHomeAsUpEnabled(showHomeAsUp: Boolean) {
        getInterface()?.setDisplayHomeAsUpEnabled(showHomeAsUp)
    }

    fun pushFragment(fragment: BaseFragment) {
        getInterface()?.pushFragment(fragment)
    }

    fun popBackStack() {
        getInterface()?.popBackStack()
    }
}
