package org.lee.talk_to_we_client.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.lee.talk_to_we_client.services.communicationManager

class loginViewModel : ViewModel() {
    fun login(userID: String, userPassword: CharArray) = runBlocking{
        val run = viewModelScope.async {
            println("login process start")
            communicationManager().LoginTest(userID, String(userPassword))
        }
        println("login response")
        userPassword.fill('0')
    }
}