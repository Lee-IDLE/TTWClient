package org.lee.talk_to_we_client.services

import androidx.lifecycle.ViewModel
import io.ktor.client.*
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.json.Json
import org.lee.talk_to_we_client.models.LoginData
import org.lee.talk_to_we_client.models.RequestData
import org.lee.talk_to_we_client.objectClass.webSocket
import org.lee.talk_to_we_client.viewModels.loginViewModel

class communicationManager {
    var viewModels: ViewModel? = null
    suspend fun loginProcess(viewModel: loginViewModel, UserId: String, UserPassword: String) {
        viewModels = viewModel

        val loginDataList = mutableListOf(LoginData(UserId, UserPassword))
        sendLoginData(RequestData<LoginData>("login", loginDataList))
        viewModels = null
    }

    suspend fun sendLoginData(loginData: RequestData<LoginData>){
        val jsonData = Json.encodeToString(
            RequestData.serializer(LoginData.serializer()), loginData
        )
        val viewmodel = viewModels as loginViewModel

        viewmodel.isLoading.value = true
        // 참고 https://java-jedi.medium.com/welcome-ktor-client-your-next-http-client-for-kotlin-based-project-part-ii-236462d4c836
        println("try connection")
        webSocket.connect()
        webSocket.send(jsonData)
        webSocket.receive()
        viewmodel.isLoading.value = false
    }
}