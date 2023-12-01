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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bravoromeo.contacts.R
import com.bravoromeo.contacts.ui.theme.ContactsTheme
import com.bravoromeo.contacts.viewmodel.ContactsViewModel
import kotlinx.coroutines.launch
import java.text.DateFormatSymbols
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewCalendarView(){
    ContactsTheme {
        CalendarViewScreen()
        Column{
            //Box(modifier=Modifier.size(50.dp)) { DayCard{} }
            //Box(modifier=Modifier.size(50.dp)) { DayCard(appointmentCount = 2, dayOfMonth = 7, onSelectedMonth = false) {} }
            //Box(modifier=Modifier.size(50.dp)) { DayCard(appointmentCount = 1, dayOfMonth = 14, isToday = true) {} }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarViewScreen(
    modifier: Modifier = Modifier
){
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
            .fillMaxSize()
    ){
        Column(
            modifier=modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Row(//Month navigation
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier =modifier
                    .fillMaxWidth()
                    .padding(bottom=8.dp)
            ){
                IconButton(
                    onClick={ selectedDate = selectedDate.minusMonths(1) }
                ) {
                    Icon(
                        imageVector=Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription=stringResource(id=R.string.ui_previous_month)
                    )
                }
                Text(
                    text = selectedDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
                    + " " + selectedDate.year,
                    style = MaterialTheme.typography.headlineSmall
                )
                IconButton(
                    onClick={ selectedDate = selectedDate.plusMonths(1) }
                ) {
                    Icon(
                        imageVector=Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription=stringResource(id=R.string.ui_next_month)
                    )
                }
            }
            Row(//Days of the week
                modifier =modifier
                    .fillMaxWidth()
                    .padding(vertical=8.dp)
            ) {
                /*val daysOfWeek = (1..7).map {day ->
                    LocalDate.now().withDayOfMonth(day).dayOfWeek.getDisplayName(
                        TextStyle.SHORT,
                        Locale.getDefault()
                    )
                }*/
                val daysOfWeek = DateFormatSymbols.getInstance().shortWeekdays.toList().drop(1)

                for (day in daysOfWeek){
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier =modifier
                            .weight(1f)
                            .padding(horizontal=4.dp)
                    ) {
                        Text(
                            text=day,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
            Row(//calendar representation
                modifier =modifier
                    .fillMaxWidth()
                    .padding(vertical=8.dp)
            ) {
                var calendarSheet=
                    getCalendarSheet(selectedDate.month, selectedDate.year, true)
                LaunchedEffect(key1=selectedDate){
                    calendarSheet=
                        getCalendarSheet(selectedDate.month, selectedDate.year, true)
                }
                Column(//sunday
                    modifier =modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    for(i in 0 until calendarSheet.size step 7){
                        DayCard(
                            day = calendarSheet[i],
                            isToday = calendarSheet[i] == LocalDate.now(),
                            onSelectedMonth = calendarSheet[i].month == selectedDate.month,
                            modifier = modifier
                                .weight(1f)
                        ){}
                    }
                }
                Column(//monday
                    modifier =modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    for(i in 1 until calendarSheet.size step 7){
                        DayCard(
                            day = calendarSheet[i],
                            isToday = calendarSheet[i] == LocalDate.now(),
                            onSelectedMonth = calendarSheet[i].month == selectedDate.month,
                            modifier = modifier
                                .weight(1f)
                        ){}
                    }
                }
                Column(//tuesday
                    modifier =modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    for(i in 2 until calendarSheet.size step 7){

                        DayCard(
                            day = calendarSheet[i],
                            isToday = calendarSheet[i] == LocalDate.now(),
                            onSelectedMonth = calendarSheet[i].month == selectedDate.month,
                            modifier = modifier
                                .weight(1f)
                        ){}
                    }
                }
                Column(//wednesday
                    modifier =modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    for(i in 3 until calendarSheet.size step 7){
                        DayCard(
                            day = calendarSheet[i],
                            isToday = calendarSheet[i] == LocalDate.now(),
                            onSelectedMonth = calendarSheet[i].month == selectedDate.month,
                            modifier = modifier
                                .weight(1f)
                        ){}
                    }
                }
                Column(//thursday
                    modifier =modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    for(i in 4 until calendarSheet.size step 7){
                        DayCard(
                            day = calendarSheet[i],
                            isToday = calendarSheet[i] == LocalDate.now(),
                            onSelectedMonth = calendarSheet[i].month == selectedDate.month,
                            modifier = modifier
                                .weight(1f)
                        ){}
                    }
                }
                Column(//friday
                    modifier =modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    for(i in 5 until calendarSheet.size step 7){
                        DayCard(
                            day = calendarSheet[i],
                            isToday = calendarSheet[i] == LocalDate.now(),
                            onSelectedMonth = calendarSheet[i].month == selectedDate.month,
                            modifier = modifier
                                .weight(1f)
                        ){}
                    }
                }
                Column(//saturday
                    modifier =modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    for(i in 6 until calendarSheet.size step 7){
                        DayCard(
                            day = calendarSheet[i],
                            isToday = calendarSheet[i] == LocalDate.now(),
                            onSelectedMonth = calendarSheet[i].month == selectedDate.month,
                            modifier = modifier
                                .weight(1f)
                        ){}
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayCard(
    modifier: Modifier = Modifier,
    day: LocalDate = LocalDate.now(),
    isToday: Boolean = false,
    onSelectedMonth: Boolean = true,
    viewModel: ContactsViewModel? = null,
    onClick: () -> Unit
){
    val coroutineScope = rememberCoroutineScope()
    var appointmentCount = 0
    coroutineScope.launch { appointmentCount = viewModel?.getAppointmentsByDate(day) ?: 0 }
    Surface(
        shape = MaterialTheme.shapes.small,
        shadowElevation = if(onSelectedMonth) 1.dp else 0.dp,
        tonalElevation = 0.dp,
        color = if (onSelectedMonth) MaterialTheme.colorScheme.surface
            else MaterialTheme.colorScheme.surfaceVariant,
        modifier =modifier
            //.aspectRatio(1f)
            .padding(1.dp)
            .fillMaxSize()
    )
    {
        Column(
            modifier =modifier
                .padding(1.dp)
                .fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier =modifier
                    .weight(0.40f)
                    .fillMaxSize()
            ) {
                Text(
                    text=day.dayOfMonth.toString(),
                    color = if (onSelectedMonth)MaterialTheme.colorScheme.onSurface
                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    modifier = modifier
                        .padding(start = 2.dp)
                )
                if (isToday){
                    Surface(
                        shape=CircleShape,
                        color=if (onSelectedMonth) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.primary.copy(alpha=0.5f),
                        modifier=modifier
                            .padding(top=2.dp, end=2.dp)
                            .size(10.dp)
                    ) {}
                }
            }
            Row(
                modifier =modifier
                    .weight(0.60f)
                    .fillMaxSize()
            ){
                if (appointmentCount > 0){
                    Surface(
                        shape=MaterialTheme.shapes.small,
                        color=if (onSelectedMonth) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.primaryContainer.copy(alpha=0.5f),
                        modifier=modifier
                            .padding(1.dp)
                            .fillMaxSize()
                    ) {
                        Row(
                            verticalAlignment=Alignment.CenterVertically,
                            horizontalArrangement=Arrangement.End,
                            modifier=modifier
                                .fillMaxSize()
                        ) {
                            Text(
                                text=appointmentCount.toString(),
                                color=if (onSelectedMonth) MaterialTheme.colorScheme.onPrimaryContainer
                                else MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha=0.5f),
                                fontWeight=FontWeight.SemiBold,
                                modifier=modifier
                                    .padding(end=2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun getCalendarSheet(month: Month, year: Int, firstDaySunday: Boolean): List<LocalDate>{
    val calendarSheet: MutableList<LocalDate> = emptyList<LocalDate>().toMutableList()
    val firstDayOfMonth = LocalDate.of(year, month, 1)
    var weekDayOfFirstDay = firstDayOfMonth.dayOfWeek.value
    //Moving Sunday to the first day of the week
    if (firstDaySunday){
        weekDayOfFirstDay+=1
        if (weekDayOfFirstDay == 8) weekDayOfFirstDay=1
    }
    //Previous month gap
    for(i in 1 until weekDayOfFirstDay){

        val currentDay = firstDayOfMonth.minusDays((weekDayOfFirstDay - i.toLong()))
        calendarSheet.add(currentDay)
    }
    //Current month
    val daysOfCurrentMonth = firstDayOfMonth.lengthOfMonth()
    for (i in 0 until  daysOfCurrentMonth){
        val currentDay = firstDayOfMonth.plusDays(i.toLong())
        calendarSheet.add(currentDay)
    }
    //Next Month gap
    val gapOfDays = 42 - calendarSheet.size
    for(i in 0 until  gapOfDays){
        val transitionDate = firstDayOfMonth.plusMonths(1)
        val currentDay = transitionDate.plusDays(i.toLong())
        calendarSheet.add(currentDay)
    }

    return calendarSheet.toList()
}