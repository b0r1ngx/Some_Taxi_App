package b0r1ngx.careers.roxiemobile.ui.screens

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import b0r1ngx.careers.roxiemobile.data.TaxiOrder
import b0r1ngx.careers.roxiemobile.data.Vehicle
import b0r1ngx.careers.roxiemobile.utils.DATE_FORMAT
import b0r1ngx.careers.roxiemobile.viewmodels.TaxiViewModel

@Composable
fun TaxiOrderScreenBody(taxiViewModel: TaxiViewModel, navController: NavHostController) =
    GetTaxiOrderInfo(taxiViewModel, navController)

@Composable
fun GetTaxiOrderInfo(taxiViewModel: TaxiViewModel, navController: NavHostController) {
    val taxiOrder = taxiViewModel.currentTaxiOrder
    if (taxiOrder != null) {
        val taxiPhoto by taxiViewModel.photo.observeAsState()

        taxiPhoto?.let {
            val vehicle = taxiOrder.vehicle

            Column {
                ImageFromBitmap(
                    bitmap = it,
                    description = "vehicle: {model}, driver: {driverName} (reg: {reg})"
                )
                OrderInfoBody(taxiOrder, vehicle)
            }
        }
    } else {
        // i hope and bet it cannot be go here, but if it is
        // also say to user, cannot load order
        navController.navigateUp()
        Toast.makeText(LocalContext.current, "Can't find order", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun OrderInfoBody(taxiOrder: TaxiOrder, vehicle: Vehicle) {
    val isSameCity = taxiOrder.startAddress.city == taxiOrder.endAddress.city
    val cityText =
        if (isSameCity) taxiOrder.startAddress.city
        else "${taxiOrder.startAddress.city} -> ${taxiOrder.endAddress.city}"

    Column(horizontalAlignment = CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Text(text = "Информация о транспорте и водителе")
        Text(text = vehicle.modelName + "   " + vehicle.regNumber)
        Text(text = vehicle.driverName, style = MaterialTheme.typography.labelSmall)

        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Поездка")
        Text(text = cityText, style = MaterialTheme.typography.bodyLarge)
        Text(
            text = "${taxiOrder.startAddress.address} -> ${taxiOrder.endAddress.address}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = DATE_FORMAT.format(taxiOrder.orderTime),
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Стоимость поездки")
        Text(
            text = "${taxiOrder.price.amount} ${taxiOrder.price.currency.symbol}",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun ImageFromBitmap(bitmap: Bitmap, description: String) =
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentScale = ContentScale.Crop,
        contentDescription = description,
        modifier = Modifier
            .fillMaxWidth()
    )
