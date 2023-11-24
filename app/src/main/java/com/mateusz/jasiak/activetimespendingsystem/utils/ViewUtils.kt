package com.mateusz.jasiak.activetimespendingsystem.utils

fun String?.or(message: String): String {
    if (this.isNullOrEmpty()) {
        return message
    }
    return this
}