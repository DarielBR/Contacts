package com.bravoromeo.contacts.ui.composables

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bravoromeo.contacts.repositories.database.entities.Appointment
import com.bravoromeo.contacts.ui.theme.ContactsTheme
import com.bravoromeo.contacts.viewmodel.ContactsViewModel
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
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
    currentDate: LocalDate = LocalDate.now()
){
    Surface(

        modifier =modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            Row(
                modifier =modifier
                    .weight(2f)
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

                            }
                        }
                        /*Divider(
                            thickness = Dp.Hairline,
                            color = MaterialTheme.colorScheme.surfaceVariant
                        )*/
                        val coroutineScope = rememberCoroutineScope()
                        var appointmentList: List<Appointment> = emptyList()
                        coroutineScope.launch {
                            appointmentList = viewModel?.getAppointmentsByDate(currentDate) ?: emptyList()
                        }
                        appointmentList.forEach{appointment ->
                            val appointmentDuration = Duration.between(appointment.dateStart, appointment.dateEnd)
                            Box(
                                modifier=modifier.fillMaxSize()
                            ){
                                AppointmentView(
                                    startPadding=30,
                                    topMultiplier=appointmentDuration.toHours()
                                ) {/*TODO*/}
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
    appointment: Appointment? = null,
    startPadding: Int = 0,//Must go
    topMultiplier: Long = 1,
    onClick: () -> Unit

){
    val hour = appointment?.dateStart?.hour ?: 0
    val minutes =appointment?.dateStart?.minute ?: 0
    val topPadding = 40 * (hour+minutes/60)
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
                start=startPadding.dp,
                top=topPadding.dp
            )
            .fillMaxWidth()
            .height((40 * topMultiplier).toFloat().dp)
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