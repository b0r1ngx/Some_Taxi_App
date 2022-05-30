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

private const val DISK_CACHE_SIZE = 10L * 1024 * 1024
private const val DISK_CACHE_DIR = "thumbnails"

//private var diskLruCache: DiskLruCache? = null
//private val diskCacheLock = ReentrantLock()
//private val diskCacheLockCondition: Condition = diskCacheLock.newCondition()
//private var diskCacheStarting = true

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
        // Initialize disk cache on background thread
//        val cacheDir = getDiskCacheDir(this, DISK_CACHE_DIR)
//        initDiskCache(cacheDir)
    }

//    private fun initDiskCache(vararg params: File) =
//        lifecycleScope.launch(Dispatchers.IO) {
//            diskCacheLock.withLock {
//                val cacheDir = params[0]
//                diskLruCache = diskLruCache.open(cacheDir, DISK_CACHE_SIZE)
//                diskCacheStarting = false // Finished initialization
//                diskCacheLockCondition.signalAll() // Wake any waiting threads
//            }
//        }
//
//    fun addBitmapToCache(key: String, bitmap: Bitmap) {
//        // Add to memory cache as before
////        if (getBitmapFromMemCache(key) == null) {
////            memoryCache.put(key, bitmap)
////        }
//
//        // Also add to disk cache
//        synchronized(diskCacheLock) {
//            diskLruCache?.apply {
//                if (!containsKey(key)) {
//                    put(key, bitmap)
//                }
//            }
//        }
//    }
//
//    fun getBitmapFromDiskCache(key: String): Bitmap? =
//        diskCacheLock.withLock {
//            // Wait while disk cache is started from background thread
//            while (diskCacheStarting) {
//                try {
//                    diskCacheLockCondition.await()
//                } catch (e: InterruptedException) {
//                    Log.d("Test", "getBitmapFromDiskCache(), ${e.printStackTrace()}")
//                    return null
//                }
//            }
//            return diskLruCache?.get(key)
//        }
//
//
//    // Creates a unique subdirectory of the designated app cache directory. Tries to use external
//    // but if not mounted, falls back on internal storage.
//    private fun getDiskCacheDir(context: Context, uniqueName: String): File {
//        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
//        // otherwise use internal cache dir
//        val cachePath =
//            if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
//                || !isExternalStorageRemovable()) {
//                context.externalCacheDir?.path
//            } else {
//                context.cacheDir.path
//            }
//
//        return File(cachePath + File.separator + uniqueName)
//    }
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TaxiTheme {

    }
}