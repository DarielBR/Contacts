package com.bravoromeo.contacts.navigation

sealed class AppScreens (val route: String){
    object MainScreen: AppScreens("main_screen")
    object ContactDetail: AppScreens("contact_detail")
    object ContactCreation: AppScreens("contact_creation")
}