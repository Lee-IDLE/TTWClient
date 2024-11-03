package org.lee.talk_to_we_client.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.lee.talk_to_we_client.services.communicationManager
import java.security.MessageDigest

class loginViewModel : ViewModel() {
    var isLoading = mutableStateOf(false)

    fun login(userID: String, userPassword: CharArray) = runBlocking{
        viewModelScope.async {
            println("login process start ${userPassword.toString()}")
            val sendPassword = userPassword.toString().trim().sha256()
            communicationManager().loginProcess(userID, sendPassword)
        }

        println("login response")
    }

    fun String.sha256(): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(this.toByteArray(Charsets.UTF_8))

        return hashBytes.joinToString("") { "%02x".format(it) }
        //digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}