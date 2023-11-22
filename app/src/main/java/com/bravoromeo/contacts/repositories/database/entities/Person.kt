package com.bravoromeo.contacts.repositories.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person")
data class Person (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "person_id")
    val personId: Long = 0,
    @ColumnInfo(name = "full_name")
    val personFullName: String? = "",
    @ColumnInfo(name = "contact_address")
    val personAddress: String? = ""
){

    constructor(personFullName: String?, personAddress: String?): this(0, "", "")
}