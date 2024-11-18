package org.lee.talk_to_we_client.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val Category: String,
    val Data: List<LoginResponseDatas>
)

@Serializable
data class LoginResponseDatas(
    val result: String,
    val message: String
)

@Serializable
data class LoginData(
    val UserId: String,
    val UserPassword: String
)