package com.bravoromeo.contacts.ui.utils

import android.Manifest
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.bravoromeo.contacts.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPhonePermission(){
    val phonePermissionState = rememberPermissionState(
        permission = Manifest.permission.CALL_PHONE
    )
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner){
        val observer = LifecycleEventObserver{_, event ->
            when(event){
                Lifecycle.Event.ON_START -> phonePermissionState.launchPermissionRequest()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer = observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    when{
        phonePermissionState.status.isGranted -> {
            Text(text=stringResource(id=R.string.permission_phone_granted))
        }
        phonePermissionState.status.shouldShowRationale -> {
            Text(text=stringResource(id=R.string.permission_phome_rationale))
        }
        !phonePermissionState.status.isGranted && !phonePermissionState.status.shouldShowRationale ->{
            Text(text=stringResource(id=R.string.permission_phone_denied))
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestSMSPermission(){
    val smsPermissionState = rememberPermissionState(
        permission = Manifest.permission.SEND_SMS
    )
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner){
        val observer = LifecycleEventObserver{_, event ->
            when(event){
                Lifecycle.Event.ON_START -> smsPermissionState.launchPermissionRequest()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer = observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    when{
        smsPermissionState.status.isGranted -> {
            Text(text=stringResource(id=R.string.permission_phone_granted))
        }
        smsPermissionState.status.shouldShowRationale -> {
            Text(text=stringResource(id=R.string.permission_phome_rationale))
        }
        !smsPermissionState.status.isGranted && !smsPermissionState.status.shouldShowRationale ->{
            Text(text=stringResource(id=R.string.permission_phone_denied))
        }
    }
}