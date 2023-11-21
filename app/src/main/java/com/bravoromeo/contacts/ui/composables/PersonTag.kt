package com.bravoromeo.contacts.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bravoromeo.contacts.ui.theme.ContactsTheme
import com.bravoromeo.contacts.viewmodel.ContactsViewModel
import kotlin.random.Random

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewPersonTag(){
    ContactsTheme {
        PersonTag()
    }
}

@Composable
fun PersonTag(
    modifier: Modifier= Modifier,
    viewModel: ContactsViewModel? = null
){
    Surface {
        val color = getRandomColor()
        Card(
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = color.copy(alpha = 0.8f)
            ),
            modifier = modifier
                .size(150.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
            ){
                val name=
                    viewModel?.contactsState?.currentPerson?.personFullName?.uppercase() ?: "John"
                val initial=name[0].toString()
                Text(
                    text=initial,
                    color=MaterialTheme.colorScheme.surface,
                    textAlign=TextAlign.Center,
                    fontSize = 90.sp
                )
            }
        }
    }
}


private fun getRandomColor(): Color {
    val id = Random.nextInt(1,9)
    return listOfIconColors.colors.find { it.id == id }?.color ?: Color.White
}

data class UserIconColor(
    val color: Color,
    val id: Int
)

object listOfIconColors{
    val colors = listOf<UserIconColor>(
        UserIconColor(
            color = Color.Red.copy(alpha = 0.5f),
            id = 1
        ),
        UserIconColor(
            color = Color.Gray.copy(alpha = 0.5f),
            id = 2
        ),
        UserIconColor(
            color = Color.Yellow.copy(alpha = 0.5f),
            id = 3
        ),
        UserIconColor(
            color = Color.Green.copy(alpha = 0.5f),
            id = 4
        ),
        UserIconColor(
            color = Color.Blue.copy(alpha = 0.5f),
            id = 6
        ),
        UserIconColor(
            color = Color.Magenta.copy(alpha = 0.5f),
            id = 7
        ),
        UserIconColor(
            color = Color.Cyan.copy(alpha = 0.5f),
            id = 8
        )
    )
}