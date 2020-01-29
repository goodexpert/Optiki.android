package com.example.apps.optiki.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class OrganisationName : Serializable {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("name")
    var name: String? = null
}