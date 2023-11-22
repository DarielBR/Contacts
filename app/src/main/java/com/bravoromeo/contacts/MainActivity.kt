package com.bravoromeo.contacts

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bravoromeo.contacts.navigation.AppNavigation
import com.bravoromeo.contacts.navigation.AppScreens
import com.bravoromeo.contacts.ui.theme.ContactsTheme
import com.bravoromeo.contacts.viewmodel.ContactsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: ContactsViewModel by viewModels()
        setContent {
            val navHostController = rememberNavController()
            ContactsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier=Modifier.fillMaxSize(),
                    color=MaterialTheme.colorScheme.background
                ) {
                    MainComposition(
                        viewModel=viewModel,
                        navHostController=navHostController
                    )
                }
            }
        }
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainComposition(
    modifier: Modifier = Modifier,
    viewModel: ContactsViewModel? = null,
    navHostController: NavHostController? = null
){
    viewModel?.setFloatingButtonVisibility(true)
    ContactsTheme {
        Scaffold(
            floatingActionButton = {
                FloatingButton(
                    navHostController=navHostController,
                    modifier=modifier,
                    visibility = viewModel?.getFloatingButtonVisibility() ?: true
                )
            },
            floatingActionButtonPosition = FabPosition.End,
            modifier = modifier.fillMaxSize()
        ) {
            if (navHostController != null) {
                if (viewModel != null) {
                    AppNavigation(
                        navHostController=navHostController,
                        viewModel=viewModel,
                        modifier = modifier
                    )
                }
            }
        }
    }
}

@Composable
fun FloatingButton(
    modifier: Modifier = Modifier,
    navHostController: NavHostController? = null,
    visibility: Boolean
){
    if (visibility){
        IconButton(
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            onClick={ navHostController?.navigate(AppScreens.ContactCreation.route) },
            modifier =modifier
                .clip(CircleShape)
                .size(50.dp)
        ) {
            Icon(painter=painterResource(id=R.drawable.add), contentDescription="")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewMainComposition(){
    ContactsTheme {
        MainComposition()
    }
}