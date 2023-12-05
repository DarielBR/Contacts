package com.bravoromeo.contacts.navigation

sealed class AppScreens (val route: String){
    object MainScreen: AppScreens("main_screen")
    object MainScreenLandscape: AppScreens("main_screen_landscape")
    object ContactDetail: AppScreens("contact_detail")
    object ContactCreation: AppScreens("contact_creation")
    object CalendarView: AppScreens("calendar_view")
    object AppointmentCreationCard: AppScreens("AppointmentCreation")
    object CalendarDayView: AppScreens("CalendarDayView")
}