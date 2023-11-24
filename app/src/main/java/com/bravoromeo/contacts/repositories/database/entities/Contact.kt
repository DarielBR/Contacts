package com.bravoromeo.contacts.repositories.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "contact")
data class Contact (
    @PrimaryKey
    @ColumnInfo(name = "contact_id")
    @SerializedName("id")
    val contactId: String,
    @ColumnInfo(name = "person_id")
    @SerializedName("person_id")
    val personId: Long,
    @ColumnInfo(name = "contact_description")
    @SerializedName("type")
    val contactType: String,
    @ColumnInfo(name = "contact_notes")
    @SerializedName("notes")
    val contactNotes: String?
)