package com.mateusz.jasiak.activetimespendingsystem.domain.model.domain

data class UserDomain(
    var idSocialMedia: String? = null,
    var firstName: String? = null,
    var surname: String? = null,
    var name: String? = null,
    var personPhoto: String? = "",
    var token: String? = null
)