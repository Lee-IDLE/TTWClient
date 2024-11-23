package org.lee.talk_to_we_client.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val Category: String,
    val Data: List<LoginResponseDatas>
)

@Serializable
data class LoginResponseDatas(
    val Result: String,
    val Message: String
)

@Serializable
data class LoginData(
    val UserId: String,
    val UserPassword: String
)