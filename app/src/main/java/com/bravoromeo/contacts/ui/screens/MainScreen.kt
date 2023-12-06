package com.bravoromeo.contacts.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bravoromeo.contacts.R
import com.bravoromeo.contacts.navigation.AppScreens
import com.bravoromeo.contacts.repositories.database.entities.PersonWithContacts
import com.bravoromeo.contacts.ui.composables.CalendarViewScreen
import com.bravoromeo.contacts.ui.composables.ContactMenuButton
import com.bravoromeo.contacts.ui.composables.GeneralSearchBar
import com.bravoromeo.contacts.ui.composables.PersonElementDetail
import com.bravoromeo.contacts.ui.theme.ContactsTheme
import com.bravoromeo.contacts.viewmodel.ContactsViewModel
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMainScreen(){
    ContactsTheme {
        MainScreen()
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: ContactsViewModel? = null,
    navHostController: NavHostController? = null,
    orientation: Int = Configuration.ORIENTATION_PORTRAIT
){
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier =modifier
                .padding(
                    top = 8.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom =
                        if (orientation == Configuration.ORIENTATION_PORTRAIT) 70.dp
                        else 8.dp
                )
                .fillMaxSize()
        ) {
            Spacer(modifier=modifier.height(4.dp))
            GeneralSearchBar(viewModel = viewModel)
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier=modifier
                    .weight(1f)
                    .fillMaxSize()
            ){
                val coroutineScope = rememberCoroutineScope()
                ContactMenuButton(
                    iconResource = R.drawable.export_file,
                    textResource = R.string.ui_share
                ) {
                    coroutineScope.launch { viewModel?.exportContacts() }
                }
                ContactMenuButton(
                    iconResource = R.drawable.import_file,
                    textResource = R.string.ui_import
                ) {
                    coroutineScope.launch { viewModel?.importContacts() }
                }
            }
            Spacer(modifier=modifier.height(28.dp))
            var personList by remember { mutableStateOf((emptyList<PersonWithContacts>())) }
            LaunchedEffect(Unit){
                viewModel?.setPersonList()
                personList = viewModel?.getPersonsList() ?: emptyList()
            }

            Row(
                modifier =modifier
                    .weight(10f)
                    .fillMaxSize()
            ){
                LazyColumn(
                    contentPadding=PaddingValues(8.dp)
                ) {
                    items(personList) { person ->
                        var searchValue by remember { mutableStateOf("") }
                        searchValue=viewModel?.contactsState?.searchValue ?: ""
                        if (person.person.personFullName!!.contains(searchValue, true)) {
                            //viewModel?.setCurrentPerson(person.person.personId)
                            PersonElementDetail(
                                viewModel=viewModel,
                                personWithContacts=person
                            ) {
                                navHostController?.navigate(AppScreens.ContactDetail.route)
                            }
                        }
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    onClick={ navHostController?.navigate(AppScreens.ContactCreation.route) },
                    modifier =modifier
                        .clip(CircleShape)
                        .size(50.dp)
                ) {
                    Icon(painter=painterResource(id=R.drawable.add), contentDescription="")
                }
            }
        }
    }
}

@Composable
fun MainScreenPortrait(
    modifier: Modifier = Modifier,
    viewModel: ContactsViewModel? = null,
    navHostController: NavHostController? = null,
    orientation: Int = Configuration.ORIENTATION_PORTRAIT
){
    Row(
        modifier = modifier
            .fillMaxSize()
    ){
        Column(
            modifier =modifier
                .fillMaxHeight()
                .fillMaxWidth(0.5f)
        ){
            MainScreen(
                viewModel = viewModel,
                navHostController = navHostController,
                orientation = orientation
            )
        }
        Column(
            modifier =modifier
                .fillMaxHeight()
        ) {
            CalendarViewScreen(
                viewModel = viewModel,
                navHostController = navHostController,
                orientation = orientation
            )
        }
    }
}