package com.bravoromeo.contacts.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.bravoromeo.contacts.navigation.AppScreens
import com.bravoromeo.contacts.repositories.database.entities.Person
import com.bravoromeo.contacts.repositories.database.entities.PersonWithContacts
import com.bravoromeo.contacts.ui.theme.ContactsTheme
import com.bravoromeo.contacts.viewmodel.ContactsViewModel

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewPersonElementDetail(){
    ContactsTheme {
        Surface {
            PersonElementDetail(){}
        }
    }
}

@Composable
fun PersonElementDetail(
    modifier: Modifier = Modifier,
    personWithContacts: PersonWithContacts? = null,
    viewModel: ContactsViewModel? = null,
    onClick: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                viewModel?.setCurrentPerson(personWithContacts?.person?.personId ?: 0)
                onClick.invoke()
            }
    ) {
        val name = personWithContacts?.person?.personFullName ?: "John Doe"
        SmallPersonTag(name = name)
        Text(
            text = name,
            color=MaterialTheme.colorScheme.onSurface,
            fontSize = 24.sp,
            modifier = modifier
                .padding(start = 16.dp)
        )
    }
}