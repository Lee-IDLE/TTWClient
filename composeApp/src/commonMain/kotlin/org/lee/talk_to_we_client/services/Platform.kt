package org.lee.talk_to_we_client.services

interface Platform {
    val isAndroid: Boolean
    val isWindows: Boolean

    companion object {
        fun getCurrent(): Platform = PlatformProvider.getCurrent()
    }
}

expect object PlatformProvider {
    fun getCurrent(): Platform
}