package com.bravoromeo.contacts.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bravoromeo.contacts.navigation.AppScreens
import com.bravoromeo.contacts.repositories.database.entities.Person
import com.bravoromeo.contacts.repositories.database.entities.PersonWithContacts
import com.bravoromeo.contacts.ui.composables.GeneralSearchBar
import com.bravoromeo.contacts.ui.composables.PersonElementDetail
import com.bravoromeo.contacts.ui.theme.ContactsTheme
import com.bravoromeo.contacts.viewmodel.ContactsViewModel

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
    navHostController: NavHostController? = null
){
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier =modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Spacer(modifier=modifier.height(28.dp))
            GeneralSearchBar(viewModel = viewModel)
            Spacer(modifier=modifier.height(28.dp))
            var personList by remember { mutableStateOf((emptyList<PersonWithContacts>())) }
            LaunchedEffect(Unit){
                viewModel?.setPersonList()
                personList = viewModel?.getPersonsList() ?: emptyList()
            }

            LazyColumn(
                contentPadding = PaddingValues(8.dp)
            ){
                items(personList){person ->
                    var searchValue by remember { mutableStateOf("") }
                    searchValue = viewModel?.contactsState?.searchValue ?: ""
                    if(person.person.personFullName!!.contains(searchValue,true)) {
                        //viewModel?.setCurrentPerson(person.person.personId)
                        PersonElementDetail(
                            viewModel=viewModel,
                            personWithContacts=person
                        ){
                            navHostController?.navigate(AppScreens.ContactDetail.route)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun calculateScrollIndicatorPosition(scrollState: LazyListState): Float {
    val totalItemsHeight = scrollState.layoutInfo.viewportEndOffset
    val scrollFraction = if (totalItemsHeight > 0) {
        scrollState.firstVisibleItemIndex.toFloat() / totalItemsHeight
    } else {
        0f
    }

    return scrollFraction * totalItemsHeight
}