package com.john_halaka.core.domain.logging

interface ChirpLogger {
    fun info(message: String)
    fun debug(message: String)
    fun warn(message: String)
    fun error(message: String , throwable: Throwable? = null)
}


