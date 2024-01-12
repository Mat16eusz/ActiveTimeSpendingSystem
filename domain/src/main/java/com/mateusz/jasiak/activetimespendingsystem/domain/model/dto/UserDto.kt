package com.mateusz.jasiak.activetimespendingsystem.domain.model.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserDto(
    @SerializedName("idSocialMedia")
    val idSocialMedia: String? = null,

    @SerializedName("firstName")
    val firstName: String? = null,

    @SerializedName("surname")
    val surname: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("name")
    val personPhoto: String? = null,

    @SerializedName("name")
    val token: String? = null
)