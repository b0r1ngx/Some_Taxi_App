package b0r1ngx.careers.roxiemobile.ui.screens

// Used composable blocks imports
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

// Local files
import b0r1ngx.careers.roxiemobile.compose.TaxiNavHelper
import b0r1ngx.careers.roxiemobile.data.TaxiOrder
import b0r1ngx.careers.roxiemobile.utils.DATE_FORMAT
import b0r1ngx.careers.roxiemobile.viewmodels.TaxiViewModel

@Composable
fun TaxiOrdersScreenBody(taxiViewModel: TaxiViewModel, navController: NavHostController) =
    GetTaxiOrders(taxiViewModel, navController)

@Composable
fun GetTaxiOrders(taxiViewModel: TaxiViewModel, navController: NavHostController) {
    val taxiOrders by taxiViewModel.orders.observeAsState()
    val ordersStatus by taxiViewModel.status.observeAsState()

    taxiOrders?.let {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(it) { order ->
                TaxiOrderItem(order, taxiViewModel, navController)
            }
        }
    }

    // todo: its activates few times, when navigate to order and from order
    ordersStatus?.let {
        Toast.makeText(LocalContext.current, it, Toast.LENGTH_SHORT).show()
        // In most Material Design and new compose way, we can use Snackbar
        // Snackbar { Text(it) }
    }
}

// For Card and combinedClickable,
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TaxiOrderItem(
    taxiOrder: TaxiOrder,
    taxiViewModel: TaxiViewModel,
    navController: NavHostController
) {
    val extCacheDir = LocalContext.current.externalCacheDir!!

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    taxiViewModel.getTaxiPhoto(extCacheDir, taxiOrder.vehicle.photoFileName)
                    // in compose we can use the same constructions like in fragment navigation,
                    // but i just make it simple
                    taxiViewModel.newTaxiOrder(taxiOrder)
                    navController.navigate(TaxiNavHelper.Order.name)
                }, // navigate to order screen
                onLongClick = {} // reveal order info just on top of screen
            ),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        val isSameCity = taxiOrder.startAddress.city == taxiOrder.endAddress.city
        val cityText =
            if (isSameCity) taxiOrder.startAddress.city
            else "${taxiOrder.startAddress.city} -> ${taxiOrder.endAddress.city}"

        Row(modifier = Modifier.padding(horizontal = 8.dp)) {
            Column {
                Text(text = cityText, style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = "${taxiOrder.startAddress.address} -> ${taxiOrder.endAddress.address}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = DATE_FORMAT.format(taxiOrder.orderTime),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${taxiOrder.price.amount} ${taxiOrder.price.currency.symbol}",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}