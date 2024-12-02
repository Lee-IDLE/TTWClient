package org.lee.talk_to_we_client

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
//import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.lee.talk_to_we_client.objectClass.AppStatus
import org.lee.talk_to_we_client.views.loginScreen
import org.lee.talk_to_we_client.views.loginView

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
    //val navController = rememberNavController()
    if(AppStatus.isLogin.value == false) {
        loginScreen()
    } else {

    }

}