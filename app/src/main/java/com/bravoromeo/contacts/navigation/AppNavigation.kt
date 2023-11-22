package com.bravoromeo.contacts.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bravoromeo.contacts.ui.screens.ContactDetail
import com.bravoromeo.contacts.ui.screens.CreateContactScreen
import com.bravoromeo.contacts.ui.screens.MainScreen
import com.bravoromeo.contacts.viewmodel.ContactsViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: ContactsViewModel
){
    NavHost(
        navController=navHostController,
        startDestination=AppScreens.MainScreen.route
    ){
        composable(AppScreens.MainScreen.route){
            MainScreen(
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
    }
}