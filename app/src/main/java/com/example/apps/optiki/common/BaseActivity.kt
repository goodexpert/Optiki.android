package com.example.apps.optiki.common

import androidx.appcompat.app.AppCompatActivity
import com.example.apps.optiki.AppApplication
import com.example.apps.optiki.AppComponent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity : AppCompatActivity(), FragmentInterface {

    private var compositeDisposable: CompositeDisposable? = null

    override fun onDestroy() {
        cleanup()
        super.onDestroy()
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

    abstract fun getContainerLayoutId(): Int

    override fun getAppInjector(): AppComponent {
        return (application as AppApplication).appComponent
    }

    override fun setDisplayHomeAsUpEnabled(showHomeAsUp: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pushFragment(fragment: BaseFragment) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(getContainerLayoutId(), fragment)
            .commitAllowingStateLoss()
    }

    override fun popBackStack() {
        onBackPressed()
    }
}