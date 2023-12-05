package com.bravoromeo.contacts.navigation

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bravoromeo.contacts.ui.composables.AppointmentCreationCard
import com.bravoromeo.contacts.ui.composables.CalendarDayView
import com.bravoromeo.contacts.ui.composables.CalendarViewScreen
import com.bravoromeo.contacts.ui.screens.ContactDetail
import com.bravoromeo.contacts.ui.screens.CreateContactScreen
import com.bravoromeo.contacts.ui.screens.MainScreen
import com.bravoromeo.contacts.ui.screens.MainScreenPortrait
import com.bravoromeo.contacts.viewmodel.ContactsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: ContactsViewModel,
    orientation: Int
){
    NavHost(
        navController=navHostController,
        startDestination=
            if (orientation == Configuration.ORIENTATION_PORTRAIT)
                AppScreens.MainScreen.route
            else
                AppScreens.MainScreenLandscape.route
    ){
        composable(AppScreens.MainScreen.route){
            MainScreen(
                modifier = modifier,
                viewModel = viewModel,
                navHostController = navHostController
            )
        }
        composable(AppScreens.MainScreenLandscape.route){
            MainScreenPortrait(
                modifier = modifier,
                viewModel = viewModel,
                navHostController = navHostController
            )
        }
        composable(AppScreens.ContactDetail.route){
            ContactDetail(
                viewModel = viewModel,
                navHostController = navHostController,
                modifier = modifier
            )
        }
        composable(AppScreens.ContactCreation.route){
            CreateContactScreen(
                viewModel = viewModel,
                navHostController = navHostController,
                modifier = modifier
            )
        }
        composable(AppScreens.CalendarView.route){
            CalendarViewScreen(
                viewModel = viewModel,
                navHostController = navHostController
            )
        }
        composable(AppScreens.AppointmentCreationCard.route){
            AppointmentCreationCard(
                viewModel = viewModel
            ) {
                navHostController.popBackStack()
            }
        }
        composable(AppScreens.CalendarDayView.route){
            CalendarDayView(
                viewModel = viewModel,
                navHostController = navHostController
            )
        }
    }
}