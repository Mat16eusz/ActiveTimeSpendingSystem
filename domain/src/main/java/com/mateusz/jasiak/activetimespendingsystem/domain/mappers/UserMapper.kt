package com.mateusz.jasiak.activetimespendingsystem.domain.mappers

import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.UserDomain
import com.mateusz.jasiak.activetimespendingsystem.domain.model.dto.UserDto

fun UserDto.dtoToDomain(): UserDomain = UserDomain(
    idSocialMedia = idSocialMedia,
    firstName = firstName,
    surname = surname,
    name = name,
    personPhoto = personPhoto,
    token = token
)

fun List<UserDto>.dtoToDomainList(): List<UserDomain> = map {
    it.dtoToDomain()
}