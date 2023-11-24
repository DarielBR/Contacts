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
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bravoromeo.contacts.R
import com.bravoromeo.contacts.ui.composables.ContactField
import com.bravoromeo.contacts.ui.composables.ContactType
import com.bravoromeo.contacts.ui.composables.NavBackButton
import com.bravoromeo.contacts.ui.theme.ContactsTheme
import com.bravoromeo.contacts.viewmodel.ContactsViewModel
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
                        val isNewCreation = viewModel?.contactsState?.isNewCreation ?: true
                        if (isNewCreation) viewModel?.insertPersonAndContacts()
                        else viewModel?.updatePersonWithContacts()
                        //viewModel?.insertPerson()
                        viewModel?.clearStateCreationFields()
                        viewModel?.setCreationState(true)
                        navHostController?.popBackStack()
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
    }
}