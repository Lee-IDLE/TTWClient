package org.lee.talk_to_we_client.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
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
        }
    }

    fun String.sha256(): String {
        val md = MessageDigest.getInstance("SHA-256")
        val hash = md.digest(this.toByteArray(Charsets.UTF_8)) // this.toByteArray(Charsets.UTF_8)

        return hash.joinToString("") { "%02x".format(it) }
    }
}