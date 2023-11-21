package com.bravoromeo.contacts.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bravoromeo.contacts.R
import com.bravoromeo.contacts.repositories.database.entities.Contact
import com.bravoromeo.contacts.ui.theme.ContactsTheme

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewContactElementDetail(){
    ContactsTheme {
        Surface{ ContactElementDetail() }
    }
}

@Composable
fun ContactElementDetail(
    modifier: Modifier = Modifier,
    contact: String = "+34 611 516051",
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    type: String = "FIXED"
){
    val iconResource = when(type){
        "MOBILE" -> R.drawable.cell_phone
        "EMAIL"-> R.drawable.email
        "FIXED"-> R.drawable.fixed_phone
        "ADDRESS" -> R.drawable.address
        else -> R.drawable.person
    }
    val typeResource = when(type){
        "MOBILE" -> R.string.ui_value_mobile
        "EMAIL"-> R.string.ui_value_email
        "FIXED"-> R.string.ui_value_fixed
        "ADDRESS" -> R.string.ui_value_address
        else -> R.string.ui_value_full_name
    }
    Row(
        modifier =modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Icon(
            painter=painterResource(id=iconResource),
            contentDescription="",
            tint = color,
            modifier = modifier
                .padding(start = 18.dp, top = 10.dp, end = 8.dp)
        )
        Column{
            Text(
                text=contact,
                color=color,
                fontSize = 20.sp,
                modifier = modifier
                    .padding(bottom = 2.dp, start = 8.dp, end = 8.dp, top = 8.dp)
            )
            Text(
                text=stringResource(id=typeResource),
                color=color,
                fontSize = 16.sp,
                modifier = modifier
                    .padding(bottom = 8.dp, start = 8.dp, end = 8.dp, top = 2.dp)
            )
        }
    }
}