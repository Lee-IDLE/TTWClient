package org.lee.talk_to_we_client

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController

@Composable
actual fun createNavigator(): Navigator {
    val navController = rememberNavController()
    return remember { AndroidNavigator(navController) }
}