package com.bravoromeo.contacts.ui.composables

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.wear.compose.material3.ContentAlpha
import androidx.wear.compose.material3.LocalContentAlpha
import com.bravoromeo.contacts.R
import com.bravoromeo.contacts.ui.theme.ContactsTheme
import com.bravoromeo.contacts.ui.utils.DatePicker
import com.bravoromeo.contacts.ui.utils.TimePicker
import com.bravoromeo.contacts.viewmodel.ContactsViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppointmentCreationCard(){
    ContactsTheme {
        Surface {
            AppointmentCreationCard(){}
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentCreationCard(
    modifier: Modifier = Modifier,
    viewModel: ContactsViewModel? = null,
    onClickCancel: () -> Unit
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ){
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.disabled
        ) {
            Surface(
                shape=MaterialTheme.shapes.small,
                shadowElevation=5.dp,
                tonalElevation=1.dp,
                modifier=modifier
                    .fillMaxWidth(0.95f)
                    .fillMaxHeight(0.8f)
                    .padding(8.dp)
            ) {
                Column(
                    modifier=modifier
                        .padding(top=12.dp, bottom=12.dp, start=8.dp, end=8.dp)
                        .fillMaxSize()
                ) {
                    Row(
                        modifier=modifier
                            .fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value=viewModel?.contactsState?.appointmentCreationName ?: "",
                            onValueChange={ viewModel?.onAppointmentCreationNameChange(it) },
                            placeholder={
                                Text(
                                    text=stringResource(id=R.string.ui_appointment_title)
                                )
                            },
                            colors=TextFieldDefaults.outlinedTextFieldColors(
                                containerColor=MaterialTheme.colorScheme.surface,
                                focusedBorderColor=Color.Transparent,
                                unfocusedBorderColor=Color.Transparent,
                                placeholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                            ),
                            shape=MaterialTheme.shapes.small,
                            modifier=modifier
                                .padding(4.dp)
                                .fillMaxWidth()
                                .shadow(
                                    elevation=3.dp,
                                    shape=MaterialTheme.shapes.small
                                )
                        )
                    }
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                    ) {
                        Surface(
                            shape=MaterialTheme.shapes.small,
                            shadowElevation=5.dp,
                            tonalElevation=0.dp,
                            modifier=modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        ) {
                            Column(
                                modifier =modifier
                                    .padding(start=12.dp)
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier =modifier
                                        .padding(start=2.dp, top=8.dp, bottom=8.dp)
                                        .fillMaxWidth()
                                ){
                                    Text(
                                        text=stringResource(id=R.string.ui_date_time),
                                        color=MaterialTheme.colorScheme.onSurface.copy(alpha=0.8f),
                                        fontWeight = FontWeight.Light,
                                        fontSize = 4.em
                                    )
                                }
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier =modifier
                                        .padding(8.dp)
                                        .fillMaxWidth()
                                ) {
                                    val context = LocalContext.current
                                    Column{
                                        DatePicker(context=context) {
                                            viewModel?.onAppointmentCreationStartChange(it)
                                        }
                                    }
                                    Column{
                                        TimePicker(context=context) {
                                            viewModel?.onAppointmentCreationStartTimeChange(it)
                                        }
                                    }
                                }
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier =modifier
                                        .padding(8.dp)
                                        .fillMaxWidth()
                                ) {
                                    val context = LocalContext.current
                                    Column{
                                        DatePicker(context=context) {
                                            viewModel?.onAppointmentCreationEndChange(it)
                                        }
                                    }
                                    Column{
                                        TimePicker(context=context) {
                                            viewModel?.onAppointmentCreationEndTimeChange(it)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Row(
                        modifier = modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = modifier
                                .fillMaxSize()
                        ) {
                            Row(
                                modifier =modifier
                                    .weight(1f)
                                    .fillMaxSize()
                            ) {
                                OutlinedTextField(
                                    value=viewModel?.contactsState?.appointmentCreationNote ?: "",
                                    onValueChange={ viewModel?.onAppointmentCreationNoteChange(it) },
                                    placeholder={
                                        Text(
                                            text=stringResource(id=R.string.ui_appointment_note)
                                        )
                                    },
                                    singleLine = false,
                                    colors=TextFieldDefaults.outlinedTextFieldColors(
                                        containerColor=MaterialTheme.colorScheme.surface,
                                        focusedBorderColor=Color.Transparent,
                                        unfocusedBorderColor=Color.Transparent,
                                        placeholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                                    ),
                                    shape=MaterialTheme.shapes.small,
                                    modifier=modifier
                                        .padding(top = 4.dp, start = 4.dp, end = 4.dp, bottom = 8.dp)
                                        .fillMaxSize()
                                        .shadow(
                                            elevation=3.dp,
                                            shape=MaterialTheme.shapes.small
                                        )
                                )
                            }
                            Row(
                                modifier =modifier
                                    .padding(start = 4.dp, end = 4.dp)
                                    .weight(0.2f)
                                    .fillMaxSize()
                            ) {
                                val coroutineScope = rememberCoroutineScope()
                                androidx.compose.material3.Button(
                                    shape = MaterialTheme.shapes.small,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.tertiary
                                    ),
                                    onClick= onClickCancel,
                                    modifier =  modifier
                                        .padding(end = 2.dp)
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text=stringResource(id=R.string.ui_button_cancel_appointment),
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onTertiary
                                    )
                                }
                                androidx.compose.material3.Button(
                                    shape = MaterialTheme.shapes.small,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer
                                    ),
                                    onClick= {
                                        coroutineScope.launch{ viewModel?.insertAppointmentWithPerson() }
                                        onClickCancel.invoke()
                                    },
                                    modifier =  modifier
                                        .padding(start = 2.dp)
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text=stringResource(id=R.string.ui_button_save_appointment),
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}
