package org.lee.talk_to_we_client.viewModels

import androidx.lifecycle.ViewModel

class loginViewModel : ViewModel() {
    fun login(userID: String, userPassword: CharArray){


        userPassword.fill('0')
    }
}