package b0r1ngx.careers.roxiemobile

// Lib imports
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

// Used composable blocks imports
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

// Local imports
import b0r1ngx.careers.roxiemobile.ui.theme.TaxiTheme
import b0r1ngx.careers.roxiemobile.viewmodels.TaxiViewModel
import b0r1ngx.careers.roxiemobile.compose.TaxiNavHelper.*
import b0r1ngx.careers.roxiemobile.ui.screens.TaxiOrdersScreenBody
import b0r1ngx.careers.roxiemobile.ui.screens.TaxiOrderScreenBody

class MainActivity : ComponentActivity() {
    private val taxiViewModel: TaxiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // set UI
        setContent {
            TaxiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TaxiApp(taxiViewModel)
                }
            }
        }
        // Execute other logic, init SDKs, etc...
    }
}

@Composable
fun TaxiApp(taxiViewModel: TaxiViewModel) {
    // When using Navigation within Compose, routes are represented as strings.
    // You can think of these strings as being similar to URLs or deep links.
    // We'll use the name property of each TaxiNavHelper item as the route,
    // for example, TaxiNavHelper.Orders.name.
    val navController = rememberNavController()

    //  Use this in some cases (eg. to create and use NavBarMenu)
//    val allScreens = values().toList()
//    val backstackEntry = navController.currentBackStackEntryAsState()
//    val currentScreen = TaxiNavHelper.fromRoute(
//        backstackEntry.value?.destination?.route
//    )
    TaxiNavHost(navController, taxiViewModel)
}

@Composable
fun TaxiNavHost(
    navController: NavHostController,
    taxiViewModel: TaxiViewModel,
    innerPadding: PaddingValues = PaddingValues()
) = NavHost(
    navController = navController,
    startDestination = Orders.name,
    modifier = Modifier.padding(innerPadding)
) {
    composable(Orders.name) {
        TaxiOrdersScreenBody(taxiViewModel, navController)
    }
    composable(Order.name) {
        TaxiOrderScreenBody(taxiViewModel, navController)
    }
}

//@Composable
//fun ShowSnackbar(text: String) {
//    val scope = rememberCoroutineScope()
//    val snackbarHostState = remember { SnackbarHostState() }
//
//    scope.launch {
//        snackbarHostState.showSnackbar(text)
//    }
//
//    SnackbarHost(hostState = snackbarHostState)
//}

@Composable
fun CenterText(str: String) {
    Text(text = str, textAlign = TextAlign.Center)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TaxiTheme {
        CenterText("Android")
    }
}