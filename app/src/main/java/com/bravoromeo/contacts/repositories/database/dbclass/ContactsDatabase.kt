package com.bravoromeo.contacts.repositories.database.dbclass

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bravoromeo.contacts.repositories.database.dao.ContactsDao
import com.bravoromeo.contacts.repositories.database.entities.Appointment
import com.bravoromeo.contacts.repositories.database.entities.Contact
import com.bravoromeo.contacts.repositories.database.entities.Person
import com.bravoromeo.contacts.repositories.database.entities.PersonAppointmentCrossRef
import com.bravoromeo.contacts.repositories.database.entities.utils.DateTimeConverter

@Database(entities = [Person::class, Contact::class, Appointment::class, PersonAppointmentCrossRef::class], version = 1, exportSchema = true)
@TypeConverters(DateTimeConverter::class)
abstract  class ContactsDatabase: RoomDatabase() {
    abstract val contactsDao: ContactsDao
}