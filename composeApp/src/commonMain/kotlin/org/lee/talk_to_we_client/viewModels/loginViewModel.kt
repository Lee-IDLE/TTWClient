package org.lee.talk_to_we_client.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.lee.talk_to_we_client.services.communicationManager
import sun.rmi.server.Dispatcher
import java.security.MessageDigest
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class loginViewModel : ViewModel() {
    var isLoading = MutableStateFlow(false)
    fun login(userID: String, userPassword: String) = runBlocking {
        viewModelScope.launch(Dispatchers.IO) {
            val sendPassword = userPassword.trim().sha256()
            communicationManager().loginProcess(this@loginViewModel, userID, sendPassword)
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    fun String.sha256(): String {
        val md = MessageDigest.getInstance("SHA-256")
        val hash = md.digest(this.toByteArray(Charsets.UTF_8)) // this.toByteArray(Charsets.UTF_8)

        return hash.joinToString("") { "%02x".format(it) }
    }
}