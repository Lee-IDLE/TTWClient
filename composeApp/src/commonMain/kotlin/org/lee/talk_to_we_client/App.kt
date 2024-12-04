package org.lee.talk_to_we_client

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.lee.talk_to_we_client.navigation.Screen
import org.lee.talk_to_we_client.navigation.SimpleNavController
import org.lee.talk_to_we_client.views.chatRoomListScreen
import org.lee.talk_to_we_client.views.friendsListScreen
import org.lee.talk_to_we_client.views.loginScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        BaseView()
        /*
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
         */
    }
}

@Composable
fun BaseView(){
    val navController = remember { SimpleNavController(Screen.loginView) }
    val currentScreen by navController.currentScreen.collectAsState()

    loginScreen(visible = currentScreen == Screen.loginView, navController = navController)
    friendsListScreen(visible = currentScreen == Screen.friendsListView, navController = navController)
    chatRoomListScreen(visible = currentScreen == Screen.chatRoomListView, navController = navController)
    /*
    val navigator = createNavigator()
    NavHost(navigator) {

    }

    if(AppStatus.isLogin.value == false) {
        loginScreen()
    } else {

    }
    */
}

@Composable
expect fun NavHost(
    navigator: Navigator,
    content: @Composable () -> Unit
)
