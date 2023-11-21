package com.bravoromeo.contacts.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bravoromeo.contacts.repositories.database.DatabaseRepository
import com.bravoromeo.contacts.repositories.database.entities.Contact
import com.bravoromeo.contacts.repositories.database.entities.Person
import com.bravoromeo.contacts.ui.composables.ContactType
import com.bravoromeo.contacts.viewmodel.models.ContactsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ContactsViewModel @Inject constructor(private val databaseRepository: DatabaseRepository)
    : ViewModel() {
    var contactsState by mutableStateOf(ContactsState())
            private set

    init {
        clearSearchValue()
        viewModelScope.async { getAllPersons() }
    }

    fun getPersonsList(): List<Person>{
        return contactsState.personList.toList()
    }
    fun clearSearchValue(){
        contactsState = contactsState.copy(searchValue = "")
    }

    fun onPersonCreationNameChange(newName: String){
        contactsState = contactsState.copy(
            personCreationName = newName
        )
    }

    fun onPersonCreationAddressChange(newAddress: String){
        contactsState = contactsState.copy(
            personCreationAddress = newAddress
        )
    }
    fun onPersonCreationFieldChange(newValue: String, type: ContactType){
        contactsState=when(type){
            ContactType.PERSON -> { contactsState.copy(personCreationName = newValue) }
            ContactType.ADDRESS -> { contactsState.copy(personCreationAddress = newValue) }
            ContactType.MOBILE -> { contactsState.copy(contactCreationMobileId = newValue) }
            ContactType.FIXED -> { contactsState.copy(contactCreationFixedId = newValue) }
            ContactType.EMAIL -> { contactsState.copy(contactCreationEmailId = newValue) }
            else -> { contactsState.copy(personCreationName = newValue) }
        }
    }

    fun clearStateCreationFields(){
        contactsState = contactsState.copy(
            personCreationName = "",
            personCreationAddress = "",
            contactCreationMobileId = "",
            contactCreationFixedId = "",
            contactCreationEmailId = ""
        )
    }
    fun insertPerson() {
        val person = Person(
            personId = 0,
            personFullName = contactsState.personCreationName,
            personAddress = contactsState.personCreationAddress
        )
        viewModelScope.launch { databaseRepository.insertPerson(person) }
    }
    suspend fun insertPersonAndContacts(){
        val person = Person(
            personId = 0,
            personFullName = contactsState.personCreationName,
            personAddress = contactsState.personCreationAddress
        )
        viewModelScope.async { databaseRepository.insertPerson(person) }.await()
        val personId = viewModelScope.async {
            databaseRepository.getPersonByName(contactsState.personCreationName)
        }.await().personId

        val contactList = emptyList<Contact>().toMutableList()
        if (contactsState.contactCreationMobileId != ""){
            val contact = Contact(
                personId = personId,
                contactId = contactsState.contactCreationMobileId,
                contactType = ContactType.MOBILE.name,
                contactNotes = ""
            )
            contactList.add(contact)
        }
        if (contactsState.contactCreationFixedId != ""){
            val contact = Contact(
                personId = personId,
                contactId = contactsState.contactCreationMobileId,
                contactType = ContactType.FIXED.name,
                contactNotes = ""
            )
            contactList.add(contact)
        }
        if (contactsState.contactCreationEmailId != ""){
            val contact = Contact(
                personId = personId,
                contactId = contactsState.contactCreationMobileId,
                contactType = ContactType.EMAIL.name,
                contactNotes = ""
            )
            contactList.add(contact)
        }
        viewModelScope.launch {
            contactList.forEach { contact ->
                databaseRepository.insertContact(contact)
            }
            //databaseRepository.insertPersonAndContacts(person, contactList.toList())
        }
    }
    fun insertContact(contact: Contact) = viewModelScope.launch {
        databaseRepository.insertContact(contact)
    }

     suspend fun getAllPersons(){
        var personsList = emptyList<Person>()
        viewModelScope.async {
            personsList = databaseRepository.getAllPersons()
        }.await()
        contactsState = contactsState.copy(personList = personsList.toMutableList())
    }
}