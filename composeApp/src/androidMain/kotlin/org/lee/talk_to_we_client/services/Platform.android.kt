package org.lee.talk_to_we_client.services

class AndroidPlatform : Platform {
    override val isAndroid: Boolean = true
    override val isWindows: Boolean = false
}

actual object PlatformProvider {
    actual fun getCurrent(): Platform = AndroidPlatform()
}