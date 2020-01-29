package com.example.apps.optiki.lifecycle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apps.optiki.model.OrganisationName
import com.example.apps.optiki.repository.OrganisationRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OrganisationNamesViewModel @Inject constructor(
    private val organisationRepository: OrganisationRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val organisationNames: MutableLiveData<List<OrganisationName>> by lazy {
        MutableLiveData<List<OrganisationName>>().also {
            getOrganisationNames()
        }
    }

    fun getNames(): LiveData<List<OrganisationName>> {
        return organisationNames
    }

    fun getOrganisationNames() {
        compositeDisposable.add(organisationRepository.getOrganisationNames()
            .subscribeOn(Schedulers.io()).subscribe({
                organisationNames.postValue(it.organisations)
            }, {
                organisationNames.postValue(emptyList())
            }))
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}