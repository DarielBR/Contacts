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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bravoromeo.contacts.R
import com.bravoromeo.contacts.repositories.database.entities.Contact
import com.bravoromeo.contacts.repositories.database.entities.Person
import com.bravoromeo.contacts.repositories.database.entities.PersonWithContacts
import com.bravoromeo.contacts.ui.composables.ContactElementDetail
import com.bravoromeo.contacts.ui.composables.IntentButton
import com.bravoromeo.contacts.ui.composables.NavBackButton
import com.bravoromeo.contacts.ui.composables.PersonTag
import com.bravoromeo.contacts.ui.theme.ContactsTheme
import com.bravoromeo.contacts.viewmodel.ContactsViewModel

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
    //TODO hay que cambiar la manera de ocultar el boton flotante!!!!!!!!!!!!!!!!!!!!
    viewModel?.setFloatingButtonVisibility(false)
    val currentPerson = viewModel?.contactsState?.currentPerson
        ?: PersonWithContacts(Person(), emptyList())
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
                    //TODO elimanate, share and edit buttons
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
    }
}

private val Lista: List<Contact> = listOf(
    Contact(
        personId = 0,
        contactId = "+34 611 516051",
        contactType = "MOBILE",
        contactNotes = ""
    ),
    Contact(
        personId = 0,
        contactId = "dbombinorevuelta@gmail.com",
        contactType = "EMAIL",
        contactNotes = ""
    )
)