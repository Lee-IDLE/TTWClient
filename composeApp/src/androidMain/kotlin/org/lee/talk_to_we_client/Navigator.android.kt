package org.lee.talk_to_we_client

import androidx.navigation.NavHostController

class AndroidNavigator(private var navController: NavHostController) : Navigator {
    override fun navigateTo(route: String) {
        navController.navigate(route)
    }
    override fun goBack() {
        navController.popBackStack()
    }

    fun setNavController(navController: NavHostController) {
        this.navController = navController
    }
}