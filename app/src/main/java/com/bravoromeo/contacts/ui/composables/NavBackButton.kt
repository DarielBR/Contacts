package com.bravoromeo.contacts.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bravoromeo.contacts.R
import com.bravoromeo.contacts.ui.theme.ContactsTheme

@Composable
fun NavBackButton(
    color: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
){
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = MaterialTheme.shapes.extraSmall,
        contentPadding = PaddingValues(start = 0.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                tint = color,
                contentDescription = null
            )
            Text(
                text = stringResource(id = R.string.navigation_go_back),
                color = color,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NavBackButtonPreview(){
    ContactsTheme {
        NavBackButton(
            color = MaterialTheme.colorScheme.onSurface,
            onClick = {}
        )
    }
}