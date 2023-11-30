package com.bravoromeo.contacts.repositories.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    tableName = "person_appointment",
    primaryKeys = ["person_id", "appointment_id"]
)
data class PersonAppointmentCrossRef (
    @ColumnInfo(name = "person_id")
    val personId: Long,
    @ColumnInfo(name = "appointment_id")
    val appointmentId: Long
)

data class AppointmentWithPersons(
    @Embedded
    val appointment: Appointment,
    @Relation(
        parentColumn = "appointment_id",
        entityColumn = "person_id",
        associateBy = Junction(PersonAppointmentCrossRef::class)
    )
    val persons: List<Person>
)