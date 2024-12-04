package org.lee.talk_to_we_client

import androidx.compose.runtime.Composable

@Composable
expect fun createNavigator(): Navigator
class NavigatorFactory {
}