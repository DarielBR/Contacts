package com.bravoromeo.contacts.repositories.intents

import android.content.Context
import android.content.Intent
import android.net.Uri
import javax.inject.Inject

class IntentsRepository @Inject constructor(
    private val context: Context
){
    fun callIntent(number: String){
        val callIntent = Intent(
            Intent.ACTION_DIAL,
            Uri.parse("tel:" + number)
        )
        callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(callIntent)
    }

    fun sendSMS(number: String){
        val smsIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("sms$number")
        )
        context.startActivity(Intent.createChooser(smsIntent, "").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }
    fun sendMail(mail: String){
        val mailIntent = Intent(Intent.ACTION_SEND)
        mailIntent.setType("text/html")
        mailIntent.putExtra(Intent.EXTRA_EMAIL, mail)

        context.startActivity(Intent.createChooser(mailIntent, "").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }
}