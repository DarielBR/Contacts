package com.bravoromeo.contacts.repositories.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class PersonWithContacts(
    @Embedded
    val contact: Person,
    @Relation(
        parentColumn = "person_id",
        entityColumn = "person_id"
    )
    val contacts: List<Contact>
)