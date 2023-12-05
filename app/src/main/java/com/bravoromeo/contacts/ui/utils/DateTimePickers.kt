package com.bravoromeo.contacts.ui.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    context: Context? = null,
    color: Color = MaterialTheme.colorScheme.onSurface,
    onPick: (LocalDate) -> Unit
){
    val calendar = Calendar.getInstance()
    var pickedDate by remember { mutableStateOf(java.util.Calendar.getInstance()) }
    val datePicker = DatePickerDialog(
        context!!,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            pickedDate = java.util.Calendar.getInstance().apply {
                set(Calendar.YEAR, selectedYear)
                set(Calendar.MONTH, selectedMonth)
                set(Calendar.DAY_OF_MONTH, selectedDayOfMonth)
            }
            onPick.invoke(pickedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
        },
        calendar[Calendar.YEAR],
        calendar[Calendar.MONTH],
        calendar[Calendar.DAY_OF_MONTH]
    )

    Row {
        Text(
            text= pickedDate.toInstant().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)),
            textAlign = TextAlign.Right,
            color=color,
            modifier=modifier
                .clickable {
                    datePicker.show()
                }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    context: Context? = null,
    color: Color = MaterialTheme.colorScheme.onSurface,
    onPick: (LocalTime) -> Unit
){
    val calendar = Calendar.getInstance()
    var pickedTime by remember { mutableStateOf(java.util.Calendar.getInstance()) }
    val timePicker = TimePickerDialog(
        context!!,
        { _, selectedHour: Int, selectedMinute: Int ->
            pickedTime = java.util.Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, selectedHour)
                set(Calendar.MINUTE, selectedMinute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND,0)
            }
            onPick.invoke(pickedTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime())
        },
        calendar[Calendar.HOUR_OF_DAY],
        calendar[Calendar.MINUTE],
        true
    )

    Row {
        Text(
            text= pickedTime.toInstant().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)),
            color=color,
            modifier=modifier
                .clickable {
                    timePicker.show()
                }
        )
    }
}