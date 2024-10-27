package org.lee.talk_to_we_client.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginData(
    val UserId: String,
    val UserPassword: String
)