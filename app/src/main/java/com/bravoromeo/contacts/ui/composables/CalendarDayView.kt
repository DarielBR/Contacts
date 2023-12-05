package com.bravoromeo.contacts.ui.composables

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bravoromeo.contacts.navigation.AppScreens
import com.bravoromeo.contacts.repositories.database.entities.Appointment
import com.bravoromeo.contacts.ui.theme.ContactsTheme
import com.bravoromeo.contacts.viewmodel.ContactsViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, name = "LightMode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "DarkMode")
@Composable
fun PreviewCalendarDayView(){
    ContactsTheme {
        CalendarDayView()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarDayView(
    modifier: Modifier = Modifier,
    viewModel: ContactsViewModel? = null,
    navHostController: NavHostController? = null
){
    val currentDate = viewModel?.contactsState?.currentDayDate ?: LocalDate.now()
    var appointmentList: List<Appointment> by remember { mutableStateOf(emptyList()) }
    LaunchedEffect(true){
        appointmentList = viewModel!!.getAppointmentsByDate(currentDate)
    }
    Surface(
        modifier =modifier
            .fillMaxSize()
            .padding(top=8.dp, start=8.dp, end=8.dp, bottom=100.dp)
    ) {
        Column(
            modifier = modifier
                //.padding(bottom = 80.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier =modifier
                    .weight(1.4f)
                    .fillMaxSize()
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = modifier
                        .fillMaxSize()
                ){
                    Text(
                        text=currentDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)),
                        style=MaterialTheme.typography.headlineSmall,
                        modifier=modifier
                            .padding(start=12.dp, top=50.dp)
                    )
                }
            }
            Row(
                modifier =modifier
                    .weight(8f)
                    .fillMaxSize()
            ) {
                val hoursList = (0..23).toList()
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                ){
                    items(hoursList){hour ->
                        Divider(
                            thickness = Dp.Hairline,
                            color = MaterialTheme.colorScheme.surfaceVariant
                        )
                        Row{
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.End,
                                modifier=modifier
                                    .weight(2f)
                                    .fillMaxWidth()
                                    .height(40.dp)
                            ) {
                                Text(
                                    text=
                                    LocalTime.of(hour, 0).format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)),
                                    modifier = modifier
                                )
                            }
                            Column(
                                modifier=modifier
                                    .weight(8f)
                                    .fillMaxWidth()
                            ) {
                                val appointmentsInHour = appointmentList.filter {
                                    it.dateStart.hour == hour
                                }
                                appointmentsInHour.forEach { appointment ->
                                    AppointmentView(
                                        modifier = Modifier,
                                        appointment = appointment,
                                        onClick = {appointment ->
                                            viewModel?.setCurrentAppointment(appointment)
                                            viewModel?.setAppointmentCreationState(false)
                                            viewModel?.onAppointmentCreationNameChange(appointment.appointmentName)
                                            viewModel?.onAppointmentCreationStartChange(appointment.dateStart.toLocalDate())
                                            viewModel?.onAppointmentCreationStartTimeChange(appointment.dateStart.toLocalTime())
                                            viewModel?.onAppointmentCreationEndChange(appointment.dateEnd.toLocalDate())
                                            viewModel?.onAppointmentCreationEndTimeChange(appointment.dateEnd.toLocalTime())
                                            viewModel?.onAppointmentCreationNoteChange(appointment.appointmentNote)
                                            navHostController?.navigate(AppScreens.AppointmentCreationCard.route)
                                        }
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppointmentView(
    modifier: Modifier = Modifier,
    appointment: Appointment = mockAppointment,
    onClick: (Appointment) -> Unit

){
    val hour = appointment.dateStart.hour
    val minutes =appointment.dateStart.minute
    val topPadding = 40 * (hour + minutes/60)
    Card(
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        modifier =modifier
            .padding(
                start=4.dp,
                top=2.dp,
                end=4.dp,
                bottom=2.dp
            )
            .fillMaxWidth()
            .clickable { onClick.invoke(appointment) }
    ) {
        Text(
            text=appointment?.appointmentName ?: "Mock Appointment",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text=appointment?.appointmentName ?: "This is faux note of a faux appointment",
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

private val mockAppointment = Appointment(
    appointmentId = 0,
    appointmentName = "Mock Appointment",
    dateStart = LocalDateTime.now(),
    dateEnd = LocalDateTime.now(),//.plusHours(1),
    appointmentNote = "This is a mock appointment"
)