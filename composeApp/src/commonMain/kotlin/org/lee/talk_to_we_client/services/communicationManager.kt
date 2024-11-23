package org.lee.talk_to_we_client.services

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
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

    }

    suspend fun sendLoginData(loginData: RequestData<LoginData>){
        val jsonData = Json.encodeToString(
            RequestData.serializer(LoginData.serializer()), loginData
        )
        val viewmodel = viewModels as loginViewModel


        // 참고 https://java-jedi.medium.com/welcome-ktor-client-your-next-http-client-for-kotlin-based-project-part-ii-236462d4c836
        println("try connection")
        webSocket.connect()
        webSocket.send(jsonData)
        println("try response")
        getLoginResponse()
        viewModels = null
    }

    fun getLoginResponse() = runBlocking {
        val viewModel = viewModels as loginViewModel
        viewModel.isLoading.value = true
        withTimeoutOrNull(5000) {
            while(true) {
                webSocket.map.remove("login")?.let {it ->
                    // 0: result, 1: message
                    if (it.get(0).equals("success")){
                        viewModel.isLogin.value = true
                        println("로그인 성공")
                    }
                    else {
                        viewModel.isLogin.value = false
                        viewModel.loginMessage.value = it.get(1)
                        println("로그인 실패")
                    }
                    return@withTimeoutOrNull
                }
            }
        }
        viewModel.isLoading.value = false
    }
}