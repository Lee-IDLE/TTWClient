package org.lee.talk_to_we_client

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.Check
import androidx.compose.material.icons.sharp.Close
import androidx.compose.material.icons.sharp.Refresh
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadXmlImageVector
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.*
import org.xml.sax.InputSource
import ttwclient.composeapp.generated.resources.Res

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter

fun main() = application {
    val windowState = rememberWindowState(width = 400.dp, height = 863.dp)

    Window(
        onCloseRequest = ::exitApplication,
        title = "TTWClient",
        state = windowState,
        undecorated = true,
        resizable = false
    ) {
        // 최상위 창 관리
        // https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-desktop-top-level-windows-management.html#dialogs
        AppWindowTitleBar(
            onMinimize = { windowState.isMinimized = true },
            onMaximize = {
                windowState.placement =
                    if (windowState.placement == WindowPlacement.Maximized)
                        WindowPlacement.Floating
                    else
                        WindowPlacement.Maximized
            },
            onClose = ::exitApplication
        )
        App()
    }
}

@Composable
private fun WindowScope.AppWindowTitleBar(
    onMinimize: () -> Unit,
    onMaximize: () -> Unit,
    onClose: () -> Unit) = WindowDraggableArea {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(Color.Transparent),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            IconButton(onClick = {onMinimize()}) {
                /*
                Icon(
                    modifier = Modifier
                        .fillMaxHeight(),
                    contentDescription = "minimize",
                    painter = painterResource("drawable/ic_minimize")
                )

                 */
            }

            IconButton(onClick = {onMaximize()}) {
                /*
                Icon(
                    modifier = Modifier
                        .fillMaxHeight(),
                    contentDescription = "maximize",
                    painter = painterResource("drawable/ic_maximize_black_24.xml")
                )
                 */
            }

            IconButton(onClick = {onClose()}) {
                /*
                Icon(
                    modifier = Modifier
                        .fillMaxHeight(),
                    contentDescription = "close",
                    painter = painterResource("drawable/ic_close_black_24.xml")
                )

                 */
            }
        }
    }
}