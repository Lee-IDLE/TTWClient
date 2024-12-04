package org.lee.talk_to_we_client.navigation

sealed interface Screen {
    data object loginView: Screen
    data object friendsListView: Screen
    data object chatRoomListView: Screen
}