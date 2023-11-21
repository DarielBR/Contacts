package com.bravoromeo.contacts.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bravoromeo.contacts.navigation.AppScreens
import com.bravoromeo.contacts.viewmodel.ContactsViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: ContactsViewModel? = null,
    navHostController: NavHostController? = null
){
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            Button(
                onClick={ navHostController?.navigate(AppScreens.ContactCreation.route){} },
                modifier=modifier
                    .padding(16.dp)
            ) {

            }
        }
    }
}