package org.lee.talk_to_we_client.services

import java.util.*

class DesktopPlatform : Platform {
    override val isAndroid: Boolean = false
    override val isWindows: Boolean = System.getProperty("os.name").lowercase(Locale.getDefault()).contains("win")
}

actual object PlatformProvider {
    actual fun getCurrent(): Platform = DesktopPlatform()
}