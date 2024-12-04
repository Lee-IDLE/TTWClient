package org.lee.talk_to_we_client

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
actual fun NavHost(navigator: Navigator, content: @Composable () -> Unit) {
    val navController = rememberNavController()
    (navigator as AndroidNavigator).setNavController(navController)


}