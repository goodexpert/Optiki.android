package com.example.apps.optiki.lifecycle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(OrganisationNamesViewModel::class)
    abstract fun bindOrganisationNamesViewModel(viewModel: OrganisationNamesViewModel): ViewModel
}