package com.bravoromeo.contacts.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bravoromeo.contacts.R
import com.bravoromeo.contacts.repositories.database.DatabaseRepository
import com.bravoromeo.contacts.repositories.database.entities.Contact
import com.bravoromeo.contacts.repositories.database.entities.Person
import com.bravoromeo.contacts.repositories.database.entities.PersonWithContacts
import com.bravoromeo.contacts.repositories.intents.IntentsRepository
import com.bravoromeo.contacts.repositories.jsonparse.JsonParsingRepository
import com.bravoromeo.contacts.ui.composables.ContactType
import com.bravoromeo.contacts.viewmodel.models.ContactsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val intentsRepository: IntentsRepository,
    private val jsonRepository: JsonParsingRepository,
    private val context: Context
): ViewModel() {
    var contactsState by mutableStateOf(ContactsState())
            private set

    init {
        clearSearchValue()
        viewModelScope.launch {
            setPersonList()
            //getPersonsList()
        }
    }

    fun getPersonsList(): List<PersonWithContacts>{
        return contactsState.personList.toList()
    }
    fun setPersonList(){
        viewModelScope.launch {
            val list = databaseRepository.getPersonsWithContacts()
            contactsState = contactsState.copy(personList = list.toMutableList())
        }
    }
    /*fun setCurrentPerson(personFullName: String){
        var person = Person()
        var personWithContacts = PersonWithContacts(Person(), emptyList())
        viewModelScope.launch {
            person = databaseRepository.getPersonByName(personFullName)
        }
        if (person.personId != 0.toLong()){
            viewModelScope.launch {
                personWithContacts=databaseRepository.getPersonWithContacts(personId=person.personId)
            }
        }
        contactsState = contactsState.copy(currentPerson = personWithContacts)
    }*/

    fun setCreationState(newValue: Boolean){
        viewModelScope.launch {
            contactsState = contactsState.copy(isNewCreation = newValue)
        }
    }

    fun setCurrentPerson(personId: Long){
        val personsList = contactsState.personList
        val person = personsList.find { it.person.personId == personId }
        viewModelScope.launch {
            contactsState = contactsState.copy(
                currentPerson = person ?: PersonWithContacts(Person(), emptyList())
            )
        }
    }

    fun onSearchValueChange(newValue: String){
        contactsState = contactsState.copy(searchValue = newValue)
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
    /*fun insertPerson() {
        val person = Person(
            personId = 0,
            personFullName = contactsState.personCreationName,
            personAddress = contactsState.personCreationAddress
        )
        viewModelScope.launch { databaseRepository.insertPerson(person) }
    }*/

    suspend fun deletePerson(){
        val person = contactsState.currentPerson.person
        val contacts = contactsState.currentPerson.contacts
        viewModelScope.launch {
            databaseRepository.deletePerson(person = person, contacts = contacts)
        }
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
                contactId = contactsState.contactCreationFixedId,
                contactType = ContactType.FIXED.name,
                contactNotes = ""
            )
            contactList.add(contact)
        }
        if (contactsState.contactCreationEmailId != ""){
            val contact = Contact(
                personId = personId,
                contactId = contactsState.contactCreationEmailId,
                contactType = ContactType.EMAIL.name,
                contactNotes = ""
            )
            contactList.add(contact)
        }
        viewModelScope.launch {
            contactList.forEach { contact ->
                databaseRepository.insertContact(contact)
            }
        }
    }
    suspend fun insertPersonAndContacts(personWithContacts: PersonWithContacts){
        viewModelScope.async { databaseRepository.insertPerson(personWithContacts.person) }.await()
        val personId = viewModelScope.async {
            databaseRepository.getPerson(personWithContacts.person.personId)
        }.await().personId
        viewModelScope.launch {
            personWithContacts.contacts.forEach { contact ->
                val newContact = Contact(
                    personId = personId,
                    contactId = contact.contactId,
                    contactType = contact.contactType,
                    contactNotes = contact.contactNotes
                )
                databaseRepository.insertContact(newContact)
            }
        }
    }


    suspend fun updatePersonWithContacts(){
        val person = Person(
            personId = contactsState.currentPerson.person.personId,
            personFullName = contactsState.personCreationName,
            personAddress = contactsState.personCreationAddress
        )
        viewModelScope.async { databaseRepository.updatePerson(person) }.await()
        val contactList = emptyList<Contact>().toMutableList()
        if (contactsState.contactCreationMobileId != ""){
            val contact = Contact(
                personId = contactsState.currentPerson.person.personId,
                contactId = contactsState.contactCreationMobileId,
                contactType = ContactType.MOBILE.name,
                contactNotes = ""
            )
            contactList.add(contact)
        }
        if (contactsState.contactCreationFixedId != ""){
            val contact = Contact(
                personId = contactsState.currentPerson.person.personId,
                contactId = contactsState.contactCreationFixedId,
                contactType = ContactType.FIXED.name,
                contactNotes = ""
            )
            contactList.add(contact)
        }
        if (contactsState.contactCreationEmailId != ""){
            val contact = Contact(
                personId = contactsState.currentPerson.person.personId,
                contactId = contactsState.contactCreationEmailId,
                contactType = ContactType.EMAIL.name,
                contactNotes = ""
            )
            contactList.add(contact)
            viewModelScope.launch {
                contactList.forEach { contact ->
                    if (contactExist(contact))
                        databaseRepository.updateContact(contact)
                    else
                        databaseRepository.insertContact(contact)
                }
            }
        }
    }

    private fun contactExist(contact: Contact): Boolean{
        var result = false
        val contacts = contactsState.currentPerson.contacts
        contacts.forEach { personContact ->
            if (personContact.contactType == contact.contactType) {
                result = true
            }
        }
        return result
    }
    /*fun insertContact(contact: Contact) = viewModelScope.launch {
        databaseRepository.insertContact(contact)
    }*/

    fun getCurrentMobile(): String?{
        var number: String?
        contactsState.currentPerson.contacts.forEach {contact ->
            if (contact.contactType == ContactType.MOBILE.name){
                number = contact.contactId
                return number
            }
        }
        return null
    }

    fun getCurrentMail(): String?{
        var mail: String?
        contactsState.currentPerson.contacts.forEach {contact ->
            if (contact.contactType == ContactType.EMAIL.name){
                mail = contact.contactId
                return mail
            }
        }
        return null
    }
    fun dialUp(){
        val number = getCurrentMobile()
        if (number != null) { intentsRepository.callIntent(number) }
    }

    fun sendSMS(){
        val number = getCurrentMobile()
        number?.let { intentsRepository.sendSMS(number) }
    }

    fun sentMail(){
        val mail = getCurrentMail()
        mail?.let { intentsRepository.sendMail(mail) }
    }

    suspend fun importContacts(){
        var success = false
        setPersonList()
        val importedList =
            viewModelScope.async { jsonRepository.readContactsFromJson { isSuccessful ->
                success = isSuccessful
            } }.await()
        if (success){
            viewModelScope.async{
                importedList.forEach { imported ->
                    if (contactsState.personList.find { it.person.personId == imported.person.personId } == null) {
                        insertPersonAndContacts(imported)
                    }
                }
            }.await()
            Toast.makeText(context, context.getString(R.string.toast_success_reading_file),Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(context, context.getString(R.string.toast_error_reading_file),Toast.LENGTH_LONG).show()
        }
    }

    suspend fun exportContacts(){
        var success = false
        setPersonList()
        viewModelScope.async {
            jsonRepository.writeContactsToJsonFile(contactsState.personList){isSuccessful ->
                success = isSuccessful
            }
        }.await()
        if (success) Toast.makeText(context, context.getString(R.string.toast_success_writing_file),Toast.LENGTH_LONG).show()
        else Toast.makeText(context, context.getString(R.string.toast_error_writing_file),Toast.LENGTH_LONG).show()
    }
    suspend fun exportContacts(personWithContacts: PersonWithContacts){
        var success = false
        val personList = mutableListOf<PersonWithContacts>()
        personList.add(personWithContacts)
        viewModelScope.async {
            jsonRepository.writeContactsToJsonFile(personList){isSuccessful ->
                success = isSuccessful
            }
        }.await()
        if (success) Toast.makeText(context, context.getString(R.string.toast_success_writing_file),Toast.LENGTH_LONG).show()
        else Toast.makeText(context, context.getString(R.string.toast_error_writing_file),Toast.LENGTH_LONG).show()
    }
}