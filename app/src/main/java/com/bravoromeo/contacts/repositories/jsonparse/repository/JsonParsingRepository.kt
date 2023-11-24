package com.bravoromeo.contacts.repositories.jsonparse.repository

import android.content.Context
import android.os.Environment
import android.widget.Toast
import androidx.compose.ui.res.stringResource
import com.bravoromeo.contacts.R
import com.bravoromeo.contacts.repositories.database.entities.PersonWithContacts
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import javax.inject.Inject

class JsonParsingRepository @Inject constructor(
    private val context: Context
) {
    suspend fun readContactsFromJson(
        onSuccess: (Boolean) -> Unit
    ): List<PersonWithContacts>{
        var contactsList = emptyList<PersonWithContacts>()
        val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(filePath, "contacts.json").absolutePath
        try{
            runBlocking(Dispatchers.IO) {
                FileReader(File(file)).use { fileReader ->
                    val jsonArray=JsonParser.parseReader(fileReader).asJsonArray
                    val gson=Gson()
                    val personListType=object : TypeToken<List<PersonWithContacts>>() {}.type
                    contactsList=gson.fromJson(jsonArray, personListType)
                }
            }
            onSuccess.invoke(true)
        }
        catch (e: Exception){
            onSuccess.invoke(false)
        }
        return contactsList
    }

    suspend fun writeContactsToJsonFile(
        personsWithContacts: List<PersonWithContacts>,
        onSuccess: (Boolean) -> Unit
    ){
        val gsonParser = GsonBuilder().setPrettyPrinting().create()
        val jsonString = gsonParser.toJson(personsWithContacts)
        val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(filePath, "contacts.json").absolutePath
        runBlocking(Dispatchers.IO) {
            try {
                FileWriter(File(file)).use { writer ->
                    writer.write(jsonString)
                    onSuccess.invoke(true)
                }
            }
            catch (e: Exception){
                onSuccess.invoke(false)
            }
        }
    }
}