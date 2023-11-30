package com.bravoromeo.contacts.repositories.database.entities

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.bravoromeo.contacts.repositories.database.entities.utils.DateTimeConverter
import java.time.LocalDateTime
@SuppressLint("NewApi")
@Entity(tableName = "appointment")
data class Appointment (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "appointment_id")
    val appointmentId: Long = 0,
    @ColumnInfo(name = "appointment_name")
    val appointmentName: String = "",
    @ColumnInfo(name = "date_start")
    val dateStart: LocalDateTime = LocalDateTime.MIN,
    @ColumnInfo(name = "date_end")
    val dateEnd: LocalDateTime = LocalDateTime.MIN,
    @ColumnInfo(name = "appointment_note")
    val appointmentNote: String = ""
)