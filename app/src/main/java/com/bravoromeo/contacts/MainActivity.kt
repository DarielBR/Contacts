package com.bravoromeo.contacts

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bravoromeo.contacts.navigation.AppNavigation
import com.bravoromeo.contacts.ui.screens.CreateContactScreen
import com.bravoromeo.contacts.ui.theme.ContactsTheme
import com.bravoromeo.contacts.viewmodel.ContactsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: ContactsViewModel by viewModels()
        setContent {
            val navHostController = rememberNavController()
            ContactsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier=Modifier.fillMaxSize(),
                    color=MaterialTheme.colorScheme.background
                ) {
                    MainComposition(
                        viewModel=viewModel,
                        navHostController=navHostController
                    )
                    /*CreateContactScreen(
                        viewModel = viewModel,
                        navHostController = navHostController
                    )*/
                }
            }
        }
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainComposition(
    modifier: Modifier = Modifier,
    viewModel: ContactsViewModel? = null,
    navHostController: NavHostController? = null
){
    ContactsTheme {
        Scaffold(
            modifier = modifier.fillMaxSize()
        ) {  padding ->
            if (navHostController != null) {
                if (viewModel != null) {
                    AppNavigation(
                        navHostController=navHostController,
                        viewModel=viewModel,
                        modifier = modifier
                    )
                }
            }
        }
    }
}

