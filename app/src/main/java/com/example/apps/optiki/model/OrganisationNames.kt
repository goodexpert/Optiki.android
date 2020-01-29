package com.example.apps.optiki.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class OrganisationNames : Serializable {

    @SerializedName("message")
    var message: String? = null

    @SerializedName("organisations")
    var organisations: List<OrganisationName>? = null
}