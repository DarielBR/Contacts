package com.bravoromeo.contacts.viewmodel.models

import android.annotation.SuppressLint
import com.bravoromeo.contacts.repositories.database.entities.Appointment
import com.bravoromeo.contacts.repositories.database.entities.Person
import com.bravoromeo.contacts.repositories.database.entities.PersonWithContacts
import java.time.LocalDate
import java.time.LocalTime

@SuppressLint("NewApi")
data class ContactsState (
    val currentPerson: PersonWithContacts = PersonWithContacts(
        person = Person(
            personId = 0,
            personFullName = "",
            personAddress = ""
        ),
        contacts = emptyList()
    ),
    val personList: MutableList<PersonWithContacts> = emptyList<PersonWithContacts>().toMutableList(),
    val searchValue: String = "",
    val personCreationName: String = "",
    val personCreationAddress: String = "",
    val contactCreationMobileId: String =  "",
    val contactCreationFixedId: String =  "",
    val contactCreationEmailId: String =  "",
    val isNewCreation: Boolean = true,

    val appointmentCreationName: String = "",
    val appointmentCreationStart: LocalDate= LocalDate.MIN,
    val appointmentCreationStartTime: LocalTime = LocalTime.MIN,
    val appointmentCreationEnd: LocalDate = LocalDate.MIN,
    val appointmentCreationEndTime: LocalTime = LocalTime.MIN,
    val appointmentCreationNote: String = "",
    val appointmentCreationPersons: List<Long> = emptyList(),
    val isNewAppointmentCreation: Boolean = true,

    val currentDayDate: LocalDate = LocalDate.now(),
    val currentAppointment: Appointment = Appointment()
)