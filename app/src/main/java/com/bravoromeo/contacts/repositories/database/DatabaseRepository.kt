package com.bravoromeo.contacts.repositories.database

import android.content.Context
import androidx.room.Room
import com.bravoromeo.contacts.repositories.database.dao.ContactsDao
import com.bravoromeo.contacts.repositories.database.dbclass.ContactsDatabase
import com.bravoromeo.contacts.repositories.database.entities.Contact
import com.bravoromeo.contacts.repositories.database.entities.Person
import com.bravoromeo.contacts.repositories.database.entities.PersonWithContacts
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class DatabaseRepository @Inject constructor(@ApplicationContext private val context: Context) {
    private val contactsDao: ContactsDao

    init {
        val database=
            Room.databaseBuilder(context, ContactsDatabase::class.java, "contacts_db").build()
        contactsDao=database.contactsDao
    }

    suspend fun insertPerson(person: Person) = runBlocking(Dispatchers.IO){
        async {
            contactsDao.insertPerson(person)
        }
    }

    suspend fun insertPersonAndContacts(person: Person, contacts: List<Contact>) =
        runBlocking(Dispatchers.IO) {
        async {
            contactsDao.insertPersonAndContacts(person, contacts)
        }
    }

    suspend fun insertContact(contact: Contact) = runBlocking(Dispatchers.IO) {
        async {
            contactsDao.insertContact(contact)
        }
    }

    suspend fun updatePerson(person: Person) = runBlocking(Dispatchers.IO) {
        async {
            contactsDao.updatePerson(person)
        }
    }

    suspend fun updateContact(contact: Contact) = runBlocking(Dispatchers.IO) {
        async {
            contactsDao.updateContact(contact)
        }
    }

    suspend fun deletePerson(person: Person, contacts: List<Contact>) =
        runBlocking(Dispatchers.IO) {
            async {
                contactsDao.deletePerson(person, contacts)
            }
        }

    suspend fun deleteContact(contact: Contact) = runBlocking(Dispatchers.IO) {
        async {
            contactsDao.deleteContact(contact)
        }
    }

    suspend fun getPersonsWithContacts(): List<PersonWithContacts> = runBlocking(Dispatchers.IO) {
        async {
            contactsDao.getPersonsWithContacts()
        }.await()
    }

    suspend fun getPersonWithContacts(personId: Long): List<PersonWithContacts> = runBlocking(Dispatchers.IO) {
        async {
            contactsDao.getPersonWithContacts(personId)
        }.await()
    }

    suspend fun getAllPersons(): List<Person> = runBlocking(Dispatchers.IO) {
        async {
            contactsDao.getAllPersons()
        }.await()
    }

    suspend fun getPerson(personId: Long): Person = runBlocking(Dispatchers.IO) {
        async {
            contactsDao.getPerson(personId)
        }.await()
    }
}