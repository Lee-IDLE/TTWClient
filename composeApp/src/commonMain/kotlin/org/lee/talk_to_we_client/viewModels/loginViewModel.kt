package org.lee.talk_to_we_client.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.lee.talk_to_we_client.objectClass.AppStatus
import org.lee.talk_to_we_client.objectClass.webSocket
import org.lee.talk_to_we_client.services.communicationManager
import java.security.MessageDigest

class loginViewModel : ViewModel() {
    val isLoading = MutableStateFlow(false)
    val isLogin = mutableStateOf(false)
    val loginMessage = mutableStateOf("")

    fun login(userID: String, userPassword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val sendPassword = userPassword.trim().sha256()
            communicationManager().loginProcess(this@loginViewModel, userID, sendPassword)
            println("try response")
            getLoginResponse()
        }
    }

    fun String.sha256(): String {
        val md = MessageDigest.getInstance("SHA-256")
        val hash = md.digest(this.toByteArray(Charsets.UTF_8)) // this.toByteArray(Charsets.UTF_8)

        return hash.joinToString("") { "%02x".format(it) }
    }

    fun getLoginResponse() = runBlocking {
        isLoading.value = true
        withTimeoutOrNull(5000) {
            while(true) {
                webSocket.map.remove("login")?.let { it ->
                    // 0: result, 1: message
                    if (it.get(0).equals("success")){
                        isLogin.value = true
                        AppStatus.isLogin.value = true
                        println("로그인 성공")
                    }
                    else {
                        isLogin.value = false
                        loginMessage.value = it.get(1)
                        println("로그인 실패")
                    }
                    return@withTimeoutOrNull
                }
            }
        }
        isLoading.value = false
    }
}