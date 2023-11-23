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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bravoromeo.contacts.R
import com.bravoromeo.contacts.navigation.AppScreens
import com.bravoromeo.contacts.repositories.database.entities.Contact
import com.bravoromeo.contacts.repositories.database.entities.Person
import com.bravoromeo.contacts.repositories.database.entities.PersonWithContacts
import com.bravoromeo.contacts.ui.composables.ContactElementDetail
import com.bravoromeo.contacts.ui.composables.ContactMenuButton
import com.bravoromeo.contacts.ui.composables.ContactType
import com.bravoromeo.contacts.ui.composables.IntentButton
import com.bravoromeo.contacts.ui.composables.NavBackButton
import com.bravoromeo.contacts.ui.composables.PersonTag
import com.bravoromeo.contacts.ui.theme.ContactsTheme
import com.bravoromeo.contacts.viewmodel.ContactsViewModel
import kotlinx.coroutines.launch
import java.lang.Appendable

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewContactDetail(){
    ContactsTheme {
        ContactDetail()
    }
}

@Composable
fun ContactDetail(
    modifier: Modifier = Modifier,
    viewModel: ContactsViewModel? = null,
    navHostController: NavHostController? = null
){
    val currentPerson = viewModel?.contactsState?.currentPerson
        ?: PersonWithContacts(Person(), emptyList())

    var openDialog by remember { mutableStateOf(false) }
    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier=modifier
                .padding(8.dp)
                .fillMaxSize()
        ){
            Spacer(modifier=modifier.height(28.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier=modifier
                    .fillMaxWidth()
            ){
                Column{
                    NavBackButton { navHostController?.popBackStack() }
                }
                Column(){
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = modifier
                    ) {
                        ContactMenuButton(
                            textResource = R.string.ui_delete,
                            iconResource = R.drawable.delete
                        ) { openDialog = !openDialog }
                        ContactMenuButton(
                            textResource = R.string.ui_edit,
                            iconResource = R.drawable.edit
                        ) {
                            viewModel?.onPersonCreationNameChange(currentPerson.person.personFullName!!)
                            viewModel?.onPersonCreationAddressChange(currentPerson.person.personAddress!!)
                            currentPerson.contacts.forEach { contact ->
                                val contactType = when(contact.contactType){
                                    "MOBILE" -> ContactType.MOBILE
                                    "FIXED" -> ContactType.FIXED
                                    "EMAIL" -> ContactType.EMAIL
                                    else -> ContactType.NOTES
                                }
                                viewModel?.onPersonCreationFieldChange(contact.contactId,contactType)
                            }
                            viewModel?.setCreationState(false)
                            navHostController?.navigate(AppScreens.ContactCreation.route)
                        }
                    }
                }
            }
            Row(
                modifier=modifier
                    .fillMaxSize()
            ) {
                Column(
                    verticalArrangement=Arrangement.Center,
                    horizontalAlignment=Alignment.CenterHorizontally,
                    modifier=modifier
                        .fillMaxSize()
                ) {
                    Row(
                        horizontalArrangement=Arrangement.Center,
                        modifier=modifier
                            .fillMaxWidth()
                    ) {
                        PersonTag(
                            modifier=modifier,
                            name = currentPerson.person.personFullName
                                ?: "John Doe"
                        )
                    }
                    Row(
                        horizontalArrangement=Arrangement.Center,
                        modifier=modifier
                            .fillMaxWidth()
                            .padding(start=50.dp, end=50.dp, top=20.dp)
                    ) {
                        Text(
                            text = currentPerson.person.personFullName
                                ?: "John Doe",
                            color=MaterialTheme.colorScheme.onSurface,
                            fontSize = 30.sp
                        )
                    }
                    Row(
                        horizontalArrangement=Arrangement.SpaceBetween,
                        modifier=modifier
                            .fillMaxWidth()
                            .padding(start=50.dp, end=50.dp, top=20.dp)
                    ) {
                        IntentButton() {}
                        IntentButton(iconResource=R.drawable.sms) {}
                        IntentButton(iconResource=R.drawable.email) {}
                    }
                    Row(
                        modifier=modifier
                            .fillMaxWidth()
                    ) {
                        Surface(
                            shape = MaterialTheme.shapes.medium,
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier =modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            val contactList = currentPerson.contacts
                            LazyColumn(){
                                items(contactList){contact ->
                                    ContactElementDetail(
                                        contact = contact.contactId,
                                        type = contact.contactType
                                    )
                                }
                            }
                        }
                    }
                    Row(
                        horizontalArrangement=Arrangement.Start,
                        modifier=modifier
                            .fillMaxWidth()
                    ) {
                        ContactElementDetail(
                            contact = currentPerson.person.personAddress
                                ?: "John Doe",
                            type = "ADDRESS"
                        )
                    }
                }
            }
        }
        if (openDialog){
            val coroutineScope = rememberCoroutineScope()
            AlertDialog(
                text = {
                    Text(text=stringResource(id=R.string.ui_are_you_sure))
                },
                onDismissRequest={ openDialog = false },
                confirmButton={
                    Button(
                        onClick={
                            coroutineScope.launch {
                                viewModel?.deletePerson()
                            }
                            navHostController?.popBackStack()
                        }
                    ) {
                      Text(text=stringResource(id=R.string.yes))
                    }
                },
                dismissButton = {
                    Button(
                        onClick={ openDialog = false }
                    ) {
                        Text(text=stringResource(id=R.string.no))
                    }
                }
            )
        }
    }
}