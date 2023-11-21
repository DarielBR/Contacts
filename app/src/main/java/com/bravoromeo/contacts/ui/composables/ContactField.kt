package com.bravoromeo.contacts.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bravoromeo.contacts.R
import com.bravoromeo.contacts.ui.theme.ContactsTheme
import com.bravoromeo.contacts.viewmodel.ContactsViewModel

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewContactField(){
    ContactsTheme {
        Surface {
            Column{
                ContactField()
                ContactField(type=ContactType.ADDRESS)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactField(
    modifier: Modifier = Modifier,
    viewModel: ContactsViewModel? = null,
    type: ContactType = ContactType.PERSON
){
    val state = viewModel?.contactsState
    val iconResource = when(type){
        ContactType.MOBILE -> R.drawable.cell_phone
        ContactType.EMAIL -> R.drawable.email
        ContactType.FIXED -> R.drawable.fixed_phone
        ContactType.ADDRESS -> R.drawable.address
        else -> R.drawable.person
    }
    val placeholderResource = when(type){
        ContactType.MOBILE -> R.string.ui_value_mobile
        ContactType.EMAIL -> R.string.ui_value_email
        ContactType.FIXED -> R.string.ui_value_fixed
        ContactType.ADDRESS -> R.string.ui_value_address
        else -> R.string.ui_value_full_name
    }

    Row(
        modifier =modifier
            .padding(top=8.dp, bottom=8.dp, start=8.dp, end=8.dp)
            .fillMaxWidth()
    ) {

        OutlinedTextField(
            value = when(type){
                ContactType.PERSON -> state?.personCreationName ?: ""
                ContactType.ADDRESS -> state?.personCreationAddress ?: ""
                ContactType.MOBILE -> state?.contactCreationMobileId ?: ""
                ContactType.FIXED -> state?.contactCreationFixedId ?: ""
                ContactType.EMAIL -> state?.contactCreationEmailId ?: ""
                else -> state?.personCreationName ?: ""
            },
            onValueChange = { viewModel?.onPersonCreationFieldChange(it, type) },
            leadingIcon = { Icon(painter=painterResource(id=iconResource), contentDescription="") },
            placeholder = {
                Text(text=stringResource(id=placeholderResource))
            },
            shape = MaterialTheme.shapes.medium,
            modifier = modifier
                .fillMaxWidth()
        )
    }
}

enum class ContactType {
    MOBILE,
    FIXED,
    EMAIL,
    NOTES,
    PERSON,
    ADDRESS
}