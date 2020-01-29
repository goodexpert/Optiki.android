package com.example.apps.optiki

import com.example.apps.optiki.lifecycle.ViewModelModule
import com.example.apps.optiki.network.NetworkModule
import com.example.apps.optiki.ui.register.RegisterFragment
import dagger.Component

// Definition of the Application graph
// The "modules" attribute in the @Component annotation tells Dagger what Modules
// to include when building the graph
@Component(modules = [ViewModelModule::class, NetworkModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: RegisterFragment)
}