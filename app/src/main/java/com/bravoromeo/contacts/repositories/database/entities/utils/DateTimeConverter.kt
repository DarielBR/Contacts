package com.bravoromeo.contacts.repositories.database.entities.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.time.LocalDateTime


class DateTimeConverter {
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun timestampToDate(timestamp: String?): LocalDateTime?{
        return timestamp?.let { LocalDateTime.parse(it) }
    }
    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String?{
        return date?.toString()
    }
}