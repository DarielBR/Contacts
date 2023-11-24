package com.bravoromeo.contacts.repositories.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "person")
data class Person (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "person_id")
    @SerializedName("id")
    val personId: Long = 0,
    @ColumnInfo(name = "full_name")
    @SerializedName("name")
    val personFullName: String? = "",
    @ColumnInfo(name = "contact_address")
    @SerializedName("address")
    val personAddress: String? = ""
){

    constructor(personFullName: String?, personAddress: String?): this(0, "", "")
}