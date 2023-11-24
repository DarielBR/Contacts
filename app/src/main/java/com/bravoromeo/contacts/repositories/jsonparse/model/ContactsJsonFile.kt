package com.bravoromeo.contacts.repositories.jsonparse.model

import com.bravoromeo.contacts.repositories.database.entities.PersonWithContacts
import com.google.gson.annotations.SerializedName

data class ContactsJsonFile (
    @SerializedName("contacts")
    val contacts: List<PersonWithContacts>
)