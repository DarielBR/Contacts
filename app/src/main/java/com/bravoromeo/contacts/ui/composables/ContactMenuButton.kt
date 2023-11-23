package com.bravoromeo.contacts.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bravoromeo.contacts.R
import com.bravoromeo.contacts.ui.theme.ContactsTheme

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMenuButtons(){
    ContactsTheme {
        Surface{ ContactMenuButton(){} }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactMenuButton(
    modifier: Modifier = Modifier,
    iconResource: Int = R.drawable.delete,
    textResource: Int = R.string.ui_delete,
    onClick: () -> Unit
){
    var openDialog by remember { mutableStateOf(false) }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        onClick = onClick,
    ){
        Row(
            verticalAlignment=Alignment.CenterVertically,
            horizontalArrangement=Arrangement.Center,
            modifier=modifier
                .padding(start = 16.dp, end = 16.dp)

        ) {
            Text(text=stringResource(id = textResource))
            Icon(painter=painterResource(id=iconResource), contentDescription="")
        }
    }
}
