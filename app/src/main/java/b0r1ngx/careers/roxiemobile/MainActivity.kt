package b0r1ngx.careers.roxiemobile

// Lib imports
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Used composable blocks imports
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

// Local imports
import b0r1ngx.careers.roxiemobile.compose.TaxiNavHelper.Order
import b0r1ngx.careers.roxiemobile.compose.TaxiNavHelper.Orders
import b0r1ngx.careers.roxiemobile.ui.screens.TaxiOrderScreenBody
import b0r1ngx.careers.roxiemobile.ui.screens.TaxiOrdersScreenBody
import b0r1ngx.careers.roxiemobile.ui.theme.TaxiTheme
import b0r1ngx.careers.roxiemobile.viewmodels.TaxiViewModel

// Get max available VM memory, exceeding this amount will throw an
// OutOfMemory exception. Stored in kilobytes as LruCache takes an
// int in its constructor.
//val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
//
//// Use 1/8th of the available memory for this memory cache.
//val cacheSize = maxMemory / 8

const val FILE_CACHE_TIME = 600

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
    }
}

@Composable
fun TaxiApp(taxiViewModel: TaxiViewModel) {
    val navController = rememberNavController()
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
