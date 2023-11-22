package com.bravoromeo.contacts.viewmodel.models

import com.bravoromeo.contacts.repositories.database.entities.Contact
import com.bravoromeo.contacts.repositories.database.entities.Person
import com.bravoromeo.contacts.repositories.database.entities.PersonWithContacts

data class ContactsState (
    val currentPerson: PersonWithContacts = PersonWithContacts(
        person = Person(
            personId = 0,
            personFullName = "",
            personAddress = ""
        ),
        contacts = emptyList<Contact>()
    ),
    val personList: MutableList<PersonWithContacts> = emptyList<PersonWithContacts>().toMutableList(),
    val searchValue: String = "",
    val personCreationName: String = "",
    val personCreationAddress: String = "",
    val contactCreationMobileId: String =  "",
    val contactCreationFixedId: String =  "",
    val contactCreationEmailId: String =  "",
    val isFloatingButtonVisible: Boolean = true
)