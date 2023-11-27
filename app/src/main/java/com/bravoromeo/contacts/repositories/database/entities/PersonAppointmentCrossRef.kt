package com.bravoromeo.contacts.repositories.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    tableName = "person_appointment",
    primaryKeys = ["personId", "appointmentId"]
)
data class PersonAppointmentCrossRef (
    val personId: Long,
    val appointmentId: Long
)

data class AppointmentWithPersons(
    @Embedded
    val appointment: Appointment,
    @Relation(
        parentColumn = "appointmentId",
        entityColumn = "personId",
        associateBy = Junction(PersonAppointmentCrossRef::class)
    )
    val persons: List<Person>
)