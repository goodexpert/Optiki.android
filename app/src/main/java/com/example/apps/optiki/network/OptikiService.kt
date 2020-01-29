package com.example.apps.optiki.network

import com.example.apps.optiki.model.OrganisationNames
import io.reactivex.Observable
import retrofit2.http.GET

interface OptikiService {

    @GET("account/organisation/names")
    fun getOrganisationNames(): Observable<OrganisationNames>
}