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
import b0r1ngx.careers.roxiemobile.api.Taxi
import b0r1ngx.careers.roxiemobile.data.TaxiOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * The Taxi [ViewModel], provides functionality to fetch from api/services data for activities
 */
class TaxiViewModel : ViewModel() {
    // The internal MutableLiveData that stores the status, List<TaxiOrder>, Bitmap
    // of the most recent request
    private val _status = MutableLiveData<String>()
    private val _orders = MutableLiveData<List<TaxiOrder>>()
    private val _photo = MutableLiveData<Bitmap>()

    // The external immutable LiveData for the request status, List<TaxiOrder>, Bitmap
    val status: LiveData<String> = _status
    val orders: LiveData<List<TaxiOrder>> = _orders
    val photo: LiveData<Bitmap> = _photo

    /* State in a value in or pass to composable, where changes of the value would update the UI.
     * We store state in ViewModel, and pass it into composable,
     * in order to make the composable stateless and easier to test
     * Using MutableState is the most convenient (we could use LiveData, Flow)
     */
    var currentTaxiOrder by mutableStateOf<TaxiOrder?>(null)
        private set

    fun newTaxiOrder(taxiOrder: TaxiOrder) {
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
            try {
                val orders = Taxi.retrofitService.getOrders()
                _orders.value = orders
                _status.value = "Successfully fetch ${orders.size} orders"
            } catch (e: Exception) {
                _status.value = "Can't get orders. Retry in 5 seconds."
                Log.d("Test", "Error at TaxiViewModel.getTaxiOrders(): ${e.printStackTrace()}")
            }
        }

    fun getTaxiPhoto(fileName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _photo.postValue(Taxi.getPhoto(fileName))
            } catch (e: Exception) {
                Log.d("Test", "Error at TaxiViewModel.getTaxiPhoto: ${e.printStackTrace()}")
            }
        }
}