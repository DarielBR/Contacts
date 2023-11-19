package com.bravoromeo.contacts.repositories.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class Contact (
    @PrimaryKey
    @ColumnInfo(name = "contact_id")
    val contactId: String,
    @ColumnInfo(name = "person_id")
    val personId: Long,
    @ColumnInfo(name = "contact_description")
    val contactType: String,
    @ColumnInfo(name = "contact_notes")
    val contactNotes: String?
)