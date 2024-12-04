package org.lee.talk_to_we_client.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SimpleNavController(initialScreen: Screen) {
    // currentScreen
    val _currentScreen = MutableStateFlow(initialScreen)
    val currentScreen = _currentScreen.asStateFlow()

    // backStack
    private val backStack: MutableList<Screen> = mutableListOf()

    // navigateTo
    fun navigateTo(screen: Screen) {
        backStack.add(_currentScreen.value)
        _currentScreen.update { screen }
    }

    // navigateBack
    fun navigateBack() {
        if (backStack.isNotEmpty()) {
            _currentScreen.update{ backStack.removeLast() }
        }
    }

    fun backStackClear(){
        backStack.clear()
    }
}