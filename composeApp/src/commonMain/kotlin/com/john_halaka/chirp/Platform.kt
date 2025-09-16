package com.john_halaka.chirp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform