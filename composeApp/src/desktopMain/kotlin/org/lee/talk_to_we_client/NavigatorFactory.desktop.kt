package org.lee.talk_to_we_client

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun createNavigator(): Navigator {
    return remember { DesktopNavigator()}
}