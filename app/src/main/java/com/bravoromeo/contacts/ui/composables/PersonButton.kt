package com.bravoromeo.contacts.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bravoromeo.contacts.R
import com.bravoromeo.contacts.repositories.database.entities.Person
import com.bravoromeo.contacts.ui.theme.ContactsTheme
import com.bravoromeo.contacts.viewmodel.ContactsViewModel
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewPersonButton(){
    ContactsTheme {
        Column(modifier = Modifier.padding(4.dp)) {
            PersonButton{}
        }
    }
}

@Composable
fun PersonButton(
    modifier: Modifier = Modifier,
    viewModel: ContactsViewModel? = null,
    personId: Long? = null,
    onClick: () -> Unit
){
    var person = mockPerson
    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        person = viewModel?.getPerson(personId ?: 0) ?: mockPerson
    }
    androidx.compose.material3.Button(
        onClick={ viewModel?.deleteFromAppointmentCreationPersons(person.personId) },
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp,
            pressedElevation = 0.dp,
            focusedElevation = 2.dp,
            hoveredElevation = 2.dp,
            disabledElevation = 0.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text=person.personFullName ?: stringResource(id=R.string.unknown),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier=modifier
                    .padding(end = 2.dp)
            )
            Icon(
                imageVector=Icons.Rounded.Close,
                contentDescription="",
                tint=MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private val mockPerson = Person(
    personId = 0,
    personFullName = "John Doe"
)