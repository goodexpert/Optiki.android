package com.example.apps.optiki.repository

import com.example.apps.optiki.model.OrganisationNames
import com.example.apps.optiki.network.OptikiService
import io.reactivex.Observable
import javax.inject.Inject

class OrganisationRepository @Inject constructor(
    private val optikiService: OptikiService
) {

    fun getOrganisationNames(): Observable<OrganisationNames> {
        return optikiService.getOrganisationNames()
    }
}
