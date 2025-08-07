package com.marrapps.kmp_io

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform