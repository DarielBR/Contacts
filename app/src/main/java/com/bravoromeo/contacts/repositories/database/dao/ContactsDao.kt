package com.bravoromeo.contacts.repositories.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.bravoromeo.contacts.repositories.database.entities.Contact
import com.bravoromeo.contacts.repositories.database.entities.Person
import com.bravoromeo.contacts.repositories.database.entities.PersonWithContacts

@Dao
interface ContactsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: Person)
    //suspend fun insertPerson(person: Person)
    @Insert
    suspend fun insertPersonAndContacts(person: Person, contacts: List<Contact>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)
    @Update
    suspend fun updatePerson(person: Person)
    @Update
    suspend fun updateContact(contact: Contact)
    @Delete
    suspend fun deletePerson(person: Person, contacts: List<Contact>)//it used to be varargs contacts: List<Contacts> but it produced an error on runtime
    @Delete
    suspend fun deleteContact(contact: Contact)
    @Transaction
    @Query("SELECT * FROM person GROUP BY person.person_id ORDER BY person.full_name")
    suspend fun getPersonsWithContacts(): List<PersonWithContacts>
    @Transaction
    @Query("SELECT * FROM person WHERE person_id = :personId")
    suspend fun getPersonWithContacts(personId: Long): List<PersonWithContacts>
    @Transaction
    @Query("SELECT * FROM person, contact WHERE person.full_name LIKE :searchValue OR contact.contact_id LIKE :searchValue GROUP BY person.person_id ORDER BY person.full_name")
    suspend fun getPersonsWithContactsBySearchValue(searchValue: String): List<PersonWithContacts>
    @Query("SELECT * FROM person GROUP BY person.person_id ORDER BY person.full_name")
    suspend fun getAllPersons(): List<Person>
    @Query("SELECT * FROM person WHERE person_id = :personId")
    suspend fun getPerson(personId: Long): Person
    @Query("SELECT * FROM person WHERE person.full_name == :value")
    suspend fun getPersonByName(value: String): Person
}