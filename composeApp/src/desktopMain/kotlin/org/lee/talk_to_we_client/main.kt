package org.lee.talk_to_we_client

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TTWClient",
        state = WindowState(width = 400.dp, height = 863.dp)
    ) {
        App()
    }
}