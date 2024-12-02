package org.lee.talk_to_we_client

expect interface Navigator {
    fun navigateTo(route: String)
    fun goBack()
}
