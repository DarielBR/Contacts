package com.bravoromeo.contacts.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bravoromeo.contacts.R
import com.bravoromeo.contacts.ui.theme.ContactsTheme

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewIntentButton(){
    ContactsTheme {
        IntentButton{}
    }
}

@Composable
fun IntentButton(
    modifier: Modifier= Modifier,
    enabled: Boolean = true,
    iconResource: Int = R.drawable.fixed_phone,
    onClick: () -> Unit
){
    Button(
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        shape=MaterialTheme.shapes.extraLarge,
        onClick={ onClick.invoke() },
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp,
            hoveredElevation = 1.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp,
            focusedElevation = 1.dp
        ),
        modifier = modifier
            .width(80.dp)
            .padding(4.dp)
    ) {
        Icon(
            painter=painterResource(id= iconResource),
            contentDescription=""
        )
    }
}