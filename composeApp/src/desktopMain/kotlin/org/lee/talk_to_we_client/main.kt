package org.lee.talk_to_we_client

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TTWClient",
    ) {
        App()
    }
}