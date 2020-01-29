package com.example.apps.optiki.common

import com.example.apps.optiki.AppComponent

interface FragmentInterface {

    fun getAppInjector(): AppComponent

    fun setDisplayHomeAsUpEnabled(showHomeAsUp: Boolean)

    fun pushFragment(fragment: BaseFragment)
    fun popBackStack()
}