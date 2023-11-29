package com.bravoromeo.contacts.viewmodel.models

import android.annotation.SuppressLint
import com.bravoromeo.contacts.repositories.database.entities.Person
import com.bravoromeo.contacts.repositories.database.entities.PersonWithContacts
import java.time.LocalDateTime
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
    val appointmentCreationStart: LocalDateTime = LocalDateTime.MIN,
    val appointmentCreationEnd: LocalDateTime = LocalDateTime.MIN,
    val appointmentCreationNote: String = "",
    val appointmentCreationPersons: List<Person> = emptyList(),
    val isNewAppointmentCreation: Boolean = true
)