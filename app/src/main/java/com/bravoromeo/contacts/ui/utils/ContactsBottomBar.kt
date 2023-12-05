package com.bravoromeo.contacts.ui.utils

import android.content.res.Configuration
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bravoromeo.contacts.R
import com.bravoromeo.contacts.navigation.AppScreens
import com.bravoromeo.contacts.ui.theme.ContactsTheme

@Preview(name = "LightMode", showBackground = true)
@Preview(name = "DarkMode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewContactsBottomBar(){
    ContactsTheme {
        ContactsBottomBar()
    }
}

@Composable
fun ContactsBottomBar(
    navHostController: NavHostController? = null
){
    val menuItems = listOf(
        MenuItems.ContactsScreen,
        MenuItems.CalendarView
    )
    val context = LocalContext.current

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant
        ) {
            val currentRoute = currentNavigationEntry(navController=navHostController!!)
            menuItems.forEach { item ->
                BottomNavigationItem(
                    selected = currentRoute == item.route,
                    onClick={
                        navHostController.navigate(item.route){
                            popUpTo(currentRoute){inclusive = true}
                        }
                    },
                    icon={
                        Icon(
                            imageVector=item.icon,
                            contentDescription = 
                                when(item.title){
                                    "ContactsScreen" -> stringResource(id=R.string.ui_contacts)
                                    "CalendarView" -> stringResource(id=R.string.ui_calendar)
                                    else -> "none"
                                }
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun currentNavigationEntry(navController: NavHostController): String{
    val entry by navController.currentBackStackEntryAsState()
    return entry?.destination?.route.toString()
}

sealed class MenuItems(
    val icon: ImageVector,
    val title: String,
    val route: String
){
    object ContactsScreen: MenuItems(
        icon = Icons.Default.AccountCircle,
        title = "ContactsScreen",
        route = AppScreens.MainScreen.route
    )

    object CalendarView: MenuItems(
        icon = Icons.Default.DateRange,
        title = "CalendarView",
        route = AppScreens.CalendarView.route
    )
}