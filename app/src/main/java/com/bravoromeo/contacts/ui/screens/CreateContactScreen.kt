package com.bravoromeo.contacts.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bravoromeo.contacts.R
import com.bravoromeo.contacts.repositories.database.entities.Person
import com.bravoromeo.contacts.ui.composables.ContactField
import com.bravoromeo.contacts.ui.composables.ContactType
import com.bravoromeo.contacts.ui.composables.NavBackButton
import com.bravoromeo.contacts.ui.theme.ContactsTheme
import com.bravoromeo.contacts.viewmodel.ContactsViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewCreateContactScreen(){
    ContactsTheme {
        Surface {
            CreateContactScreen()
        }
    }
}

@Composable
fun CreateContactScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController? = null,
    viewModel: ContactsViewModel? = null
){
    Column(
        modifier =modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier=modifier.height(28.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
        ){
            NavBackButton { navHostController?.popBackStack() }
            val coroutineScope = rememberCoroutineScope()
            Button(
                onClick={
                    coroutineScope.launch{
                        viewModel?.insertPersonAndContacts()
                        //viewModel?.insertPerson()
                        viewModel?.clearStateCreationFields()
                    }
                },
                modifier=modifier
                    .padding(16.dp)
            ) {
                Text(text=stringResource(id=R.string.ui_button_save))
            }
        }
        ContactField(
            viewModel = viewModel,
        )
        ContactField(
            viewModel = viewModel,
            type = ContactType.ADDRESS
        )
        ContactField(
            viewModel = viewModel,
            type = ContactType.MOBILE
        )
        ContactField(
            viewModel = viewModel,
            type = ContactType.FIXED
        )
        ContactField(
            viewModel = viewModel,
            type = ContactType.EMAIL
        )
        val coroutineScope = rememberCoroutineScope()
        var lazyItems by remember { mutableStateOf(emptyList<Person>()) }
        LaunchedEffect(key1=true) {
            viewModel?.getAllPersons()
            lazyItems = viewModel?.getPersonsList() ?: emptyList()
        }
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
        ){
            items(lazyItems){item ->
                Text(text=item.personFullName + " " + item.personId + " de " + item.personAddress)
            }
        }
    }
}