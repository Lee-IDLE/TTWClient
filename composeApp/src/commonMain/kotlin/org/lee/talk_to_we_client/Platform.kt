package org.lee.talk_to_we_client

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform