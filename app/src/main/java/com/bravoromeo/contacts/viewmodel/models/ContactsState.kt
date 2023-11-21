package com.bravoromeo.contacts.viewmodel.models

import androidx.compose.runtime.Stable
import com.bravoromeo.contacts.repositories.database.entities.Contact
import com.bravoromeo.contacts.repositories.database.entities.Person

data class ContactsState (
    val currentPerson: Person = Person(
        personId = 0,
        personFullName = "",
        personAddress = ""
    ),
    val searchValue: String = "",
    val personCreationName: String = "",
    val personCreationAddress: String = "",
    val contactCreationMobileId: String =  "",
    val contactCreationFixedId: String =  "",
    val contactCreationEmailId: String =  "",
    val personList: MutableList<Person> = emptyList<Person>().toMutableList()
)