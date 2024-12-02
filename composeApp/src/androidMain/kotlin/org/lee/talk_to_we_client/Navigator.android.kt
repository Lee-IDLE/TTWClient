package org.lee.talk_to_we_client

actual interface Navigator {
    actual fun navigateTo(route: String)
    actual fun goBack()
}