package org.lee.talk_to_we_client.models

import kotlinx.serialization.Serializable

@Serializable
data class RequestData<T>(
    val Category: String,
    val Data: MutableList<T>
)
