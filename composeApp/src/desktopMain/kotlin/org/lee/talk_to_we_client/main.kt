package org.lee.talk_to_we_client

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import kotlinx.coroutines.*

fun main() = application {
    val windowState = rememberWindowState(width = 400.dp, height = 863.dp)

    Window(
        onCloseRequest = ::exitApplication,
        title = "TTWClient",
        state = windowState,
        undecorated = true,
        resizable = false
    ) {
        CustomTitleBar(
            windowState = windowState,
            onMinimize =  { windowState.isMinimized = true },
            onMaximize =  {
                windowState.placement = if(windowState.placement == WindowPlacement.Maximized)
                    WindowPlacement.Floating
                else
                    WindowPlacement.Maximized
            },
            onClose = ::exitApplication,
        )
    }
}

@Composable
fun CustomTitleBar(
    windowState: WindowState,
    onMinimize: () -> Unit,
    onMaximize: () -> Unit,
    onClose: () -> Unit,
) {
    var offset by remember { mutableStateOf(Offset.Zero)}
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(Color.LightGray)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        offset = Offset(windowState.position.x.value, windowState.position.y.value)
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        offset = Offset(
                            offset.x + dragAmount.x,
                            offset.y + dragAmount.y
                        )
                        CoroutineScope(Dispatchers.Main).launch {
                            windowState.position = WindowPosition(offset.x.toDp(), offset.y.toDp())
                        }
                    }
                )
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Row {
            IconButton(onClick = onMinimize) {
                Text("_")
            }

            IconButton(onClick = onMaximize) {
                Text("□")
            }

            IconButton(onClick = onClose) {
                Text("X")
            }
        }
    }
    App()
}