package org.lee.talk_to_we_client

class DesktopNavigator : Navigator{
    var currentRoute: String = "friendsListView"

    override fun navigateTo(route: String) {
        currentRoute = route
    }
    override fun goBack() {

    }
}