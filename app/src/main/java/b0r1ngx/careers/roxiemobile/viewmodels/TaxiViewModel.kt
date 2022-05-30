package b0r1ngx.careers.roxiemobile.viewmodels

// Lib imports

// Local imports
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import b0r1ngx.careers.roxiemobile.utils.DiskCache
import b0r1ngx.careers.roxiemobile.FILE_CACHE_TIME
import b0r1ngx.careers.roxiemobile.api.TaxiService
import b0r1ngx.careers.roxiemobile.data.*
import b0r1ngx.careers.roxiemobile.utils.toLocalDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDateTime

/**
 * The Taxi [ViewModel], provides functionality to fetch from api/services data for activities
 */
class TaxiViewModel : ViewModel() {
    val diskCache = DiskCache()
    // The internal MutableLiveData that stores the status, List<TaxiOrder>, Bitmap
    // of the most recent request
    private val _status = MutableLiveData<String>()
    private val _orders = MutableLiveData<List<RightTaxiOrder>>()
    private val _photo = MutableLiveData<Bitmap>()

    // The external immutable LiveData for the request status, List<TaxiOrder>, Bitmap
    val status: LiveData<String> = _status
    val orders: LiveData<List<RightTaxiOrder>> = _orders
    val photo: LiveData<Bitmap> = _photo

    /* State in a value in or pass to composable, where changes of the value would update the UI.
     * We store state in ViewModel, and pass it into composable,
     * in order to make the composable stateless and easier to test
     * Using MutableState is the most convenient (we could use LiveData, Flow)
     */
    var currentTaxiOrder by mutableStateOf<RightTaxiOrder?>(null)
        private set

    fun newTaxiOrder(taxiOrder: RightTaxiOrder) {
        currentTaxiOrder = taxiOrder
    }

    /**
     * Call getTaxiOrders() on init so we can display status immediately
     */
    init {
        getTaxiOrders()
    }

    /**
     * Gets Taxi orders  from the Roxie Mobile Retrofit service
     * and updates the [LiveData]
     */
    private fun getTaxiOrders() =
        viewModelScope.launch {
            Log.d("Test", "getTaxiOrders() START")
            try {
                // todo: order by ascending
                val orders = postProcessOrders(TaxiService.retrofitService.getOrders())
                orders.sortByDescending { it.orderTime }
                _orders.value = orders
                _status.value = "Successfully fetch ${orders.size} orders"
            } catch (e: Exception) {
                _status.value = "Can't get orders. Retry in 5 seconds."
                Log.d("Test", "Error at TaxiViewModel.getTaxiOrders(): ${e.printStackTrace()}")
            }
        }

    private fun postProcessOrders(orders: MutableList<TaxiOrder>): MutableList<RightTaxiOrder> {
        fun rightPrice(price: Price) =
            when(price.currency) {
                "RUB" -> RightPrice(price.amount.toFloat() / 100, Currency.RUB)
                "EUR" -> RightPrice(price.amount.toFloat() / 100, Currency.EUR)
                "GBP" -> RightPrice(price.amount.toFloat() / 100, Currency.GBP)
                "USD" -> RightPrice(price.amount.toFloat() / 100, Currency.USD)
                else -> RightPrice(price.amount.toFloat() / 100, Currency.RUB)
            }


        val answer = mutableListOf<RightTaxiOrder>()
        for (order in orders) {
            answer.add(
                RightTaxiOrder(
                    id = order.id,
                    startAddress = order.startAddress,
                    endAddress = order.endAddress,
                    price = rightPrice(order.price),
                    orderTime = order.orderTime.toLocalDateTime(),
                    vehicle = order.vehicle
                )
            )
        }
        return answer
    }

    fun getTaxiPhoto(externalCacheDir: File, fileName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("Test", "getTaxiPhoto() START")
            try {
                _photo.postValue(diskCache.getBitmap(externalCacheDir, FILE_CACHE_TIME, fileName))
            } catch (e: Exception) {
                Log.d("Test", "Error at TaxiViewModel.getTaxiPhoto: ${e.printStackTrace()}")
            }
        }
}