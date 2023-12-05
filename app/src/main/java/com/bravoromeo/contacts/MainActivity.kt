package com.bravoromeo.contacts

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bravoromeo.contacts.navigation.AppNavigation
import com.bravoromeo.contacts.navigation.AppScreens
import com.bravoromeo.contacts.ui.theme.ContactsTheme
import com.bravoromeo.contacts.ui.utils.ContactsBottomBar
import com.bravoromeo.contacts.ui.utils.RequestPhonePermission
import com.bravoromeo.contacts.ui.utils.RequestSMSPermission
import com.bravoromeo.contacts.viewmodel.ContactsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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
                    RequestPhonePermission()
                    RequestSMSPermission()
                    MainComposition(
                        viewModel=viewModel,
                        navHostController=navHostController
                    )
                }
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainComposition(
    modifier: Modifier = Modifier,
    viewModel: ContactsViewModel? = null,
    navHostController: NavHostController? = null
){
    var buttonVisibility by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navHostController!!.currentBackStackEntryAsState()

    when(navBackStackEntry?.destination?.route){
        "main_screen" -> buttonVisibility = true
        else -> buttonVisibility = false
    }

    var orientation by remember { mutableStateOf(Configuration.ORIENTATION_PORTRAIT) }
    val configuration = LocalConfiguration.current
    LaunchedEffect(key1=configuration){
        orientation = configuration.orientation
    }
    if (orientation == Configuration.ORIENTATION_PORTRAIT){

    }
    else{

    }

    ContactsTheme {
        Scaffold(
            floatingActionButton = {
                if(buttonVisibility){
                    FloatingButton(
                        navHostController=navHostController,
                        modifier=modifier
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            bottomBar = {
                if (orientation == Configuration.ORIENTATION_PORTRAIT){
                    ContactsBottomBar(
                        navHostController=navHostController
                    )
                }
            },
            modifier = modifier.fillMaxSize()
        ) {
            if (navHostController != null) {
                if (viewModel != null) {
                    AppNavigation(
                        navHostController=navHostController,
                        viewModel=viewModel,
                        modifier = modifier,
                        orientation = orientation
                    )
                }
            }
        }
    }
}

@Composable
fun FloatingButton(
    modifier: Modifier = Modifier,
    navHostController: NavHostController? = null
){
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewMainComposition(){
    ContactsTheme {
        MainComposition()
    }
}