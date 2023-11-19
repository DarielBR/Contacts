package com.bravoromeo.contacts.repositories.database.dbclass

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bravoromeo.contacts.repositories.database.dao.ContactsDao
import com.bravoromeo.contacts.repositories.database.entities.Contact
import com.bravoromeo.contacts.repositories.database.entities.Person

@Database(entities = [Person::class, Contact::class], version = 1, exportSchema = true)
abstract  class ContactsDatabase: RoomDatabase() {
    abstract val contactsDao: ContactsDao
}