package org.lee.talk_to_we_client

sealed class NavRoutes(val route: String) {
    object Login : NavRoutes("loginView")
    object FriendsList : NavRoutes("friendsListView")
    object ChatRoomList : NavRoutes("chatRoomListView")
}