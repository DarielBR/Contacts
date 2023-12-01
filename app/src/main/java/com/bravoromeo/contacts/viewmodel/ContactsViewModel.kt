package com.bravoromeo.contacts.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bravoromeo.contacts.R
import com.bravoromeo.contacts.repositories.database.DatabaseRepository
import com.bravoromeo.contacts.repositories.database.entities.Appointment
import com.bravoromeo.contacts.repositories.database.entities.AppointmentWithPersons
import com.bravoromeo.contacts.repositories.database.entities.Contact
import com.bravoromeo.contacts.repositories.database.entities.Person
import com.bravoromeo.contacts.repositories.database.entities.PersonAppointmentCrossRef
import com.bravoromeo.contacts.repositories.database.entities.PersonWithContacts
import com.bravoromeo.contacts.repositories.intents.IntentsRepository
import com.bravoromeo.contacts.repositories.jsonparse.JsonParsingRepository
import com.bravoromeo.contacts.ui.composables.ContactType
import com.bravoromeo.contacts.viewmodel.models.ContactsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@SuppressLint("NewApi")
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

    fun addToAppointmentCreationPersons(personId: Long){
        viewModelScope.launch {
            val currentList = contactsState.appointmentCreationPersons.toMutableList()
            currentList.add(personId)
            contactsState = contactsState.copy(appointmentCreationPersons = currentList.toList())
        }
    }

    fun deleteFromAppointmentCreationPersons(personId: Long){
        viewModelScope.launch {
            val currentList = contactsState.appointmentCreationPersons.toMutableList()
            currentList.remove(personId)
            contactsState = contactsState.copy(appointmentCreationPersons = currentList.toList())
        }
    }

    fun setCreationState(newValue: Boolean){
        viewModelScope.launch {
            contactsState = contactsState.copy(isNewCreation = newValue)
        }
    }
    
    fun setAppointmentCreationState(newValue: Boolean){
        viewModelScope.launch { 
            contactsState = contactsState.copy(isNewAppointmentCreation = newValue)
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

    fun onAppointmentCreationNameChange(newValue: String){
        contactsState = contactsState.copy(appointmentCreationName = newValue)
    }

    fun onAppointmentCreationStartChange(newValue: LocalDateTime){
        contactsState = contactsState.copy(appointmentCreationStart = newValue)
    }

    fun onAppointmentCreationStartTimeChange(newValue: LocalTime){
        contactsState = contactsState.copy(appointmentCreationStartTime = newValue)
    }

    fun onAppointmentCreationEndChange(newValue: LocalDateTime){
        contactsState = contactsState.copy(appointmentCreationEnd = newValue)
    }

    fun onAppointmentCreationEndTimeChange(newValue: LocalTime){
        contactsState = contactsState.copy(appointmentCreationEndTime = newValue)
    }

    fun onAppointmentCreationNoteChange(newValue: String){
        contactsState = contactsState.copy(appointmentCreationNote = newValue)
    }

    fun clearStateCreationFields(){
        contactsState = contactsState.copy(
            personCreationName = "",
            personCreationAddress = "",
            contactCreationMobileId = "",
            contactCreationFixedId = "",
            contactCreationEmailId = "",
            
            appointmentCreationName = "",
            appointmentCreationStart = LocalDateTime.MIN,
            appointmentCreationEnd = LocalDateTime.MIN,
            appointmentCreationNote = "",
            appointmentCreationPersons = emptyList()
        )
    }

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

    suspend fun getPerson(personId: Long): Person {
        return viewModelScope.async { databaseRepository.getPerson(personId) }.await()
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

    //Functionality for new Appointments module
    suspend fun insertAppointment(){
        val appointment = Appointment(
            appointmentId = 0,
            appointmentName = contactsState.appointmentCreationName,
            dateStart = contactsState.appointmentCreationStart,
            dateEnd = contactsState.appointmentCreationEnd,
            appointmentNote = contactsState.appointmentCreationNote
        )
        viewModelScope.launch {
            databaseRepository.insertAppointment(appointment)
        }
    }

    suspend fun insertAppointmentWithPerson(){
        val appointment = Appointment(
            appointmentId = 0,
            appointmentName = contactsState.appointmentCreationName,
            dateStart = contactsState.appointmentCreationStart,
            dateEnd = contactsState.appointmentCreationEnd,
            appointmentNote = contactsState.appointmentCreationNote
        )
        val appointmentId = viewModelScope.async(Dispatchers.IO) { databaseRepository.insertAppointment(appointment) }

        val personsList = contactsState.appointmentCreationPersons
        personsList.forEach {personId ->
            val appointmentCrossRef = PersonAppointmentCrossRef(
                appointmentId = appointmentId.await(),
                personId = personId
            )
            if (contactsState.isNewAppointmentCreation)
                databaseRepository.insertAppointmentWithPerson(appointmentCrossRef)
            else{
                databaseRepository.updateAppointmentWithPersons(appointmentCrossRef)
                setAppointmentCreationState(true)
            }
        }
    }

    suspend fun deleteAppointment(appointment: Appointment){
        viewModelScope.launch {
            databaseRepository.deleteAppointment(appointment)
        }
    }

    suspend fun updateAppointment(appointment: Appointment){
        viewModelScope.launch {
            databaseRepository.updateAppointment(appointment)
        }
    }

    suspend fun getAllAppointments(): List<AppointmentWithPersons>{
        return viewModelScope.async { databaseRepository.getAllAppointments() }.await()
    }

    suspend fun getAppointmentsByDate(date: LocalDate): Int{
        val appointmentsList: List<Appointment> =
            viewModelScope.async { databaseRepository.getAppointmentsByDate(date) }.await()
        return appointmentsList.size
    }
}