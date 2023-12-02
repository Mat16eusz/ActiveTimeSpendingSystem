package com.mateusz.jasiak.activetimespendingsystem.domain.api.response

import com.mateusz.jasiak.activetimespendingsystem.domain.model.enums.ErrorCodeEnum

data class ErrorResponse(
    val code: ErrorCodeEnum = ErrorCodeEnum.UNKNOWN,
    val error: String = "",
    val message: String = ""
) {
    override fun toString(): String {
        return code.name + " - " + error + ": " + message
    }
}