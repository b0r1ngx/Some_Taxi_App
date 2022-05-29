package b0r1ngx.careers.roxiemobile.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavHostController
import b0r1ngx.careers.roxiemobile.data.TaxiOrder
import b0r1ngx.careers.roxiemobile.viewmodels.TaxiViewModel

@Composable
fun TaxiOrderScreenBody(taxiViewModel: TaxiViewModel, navController: NavHostController) =
//    Row {
        GetTaxiOrderInfo(taxiViewModel, navController)
//    }

@Composable
fun GetTaxiOrderInfo(taxiViewModel: TaxiViewModel, navController: NavHostController) {
    val taxiOrder = taxiViewModel.currentTaxiOrder
    if (taxiOrder != null) {
        val taxiPhoto by taxiViewModel.photo.observeAsState()

        taxiViewModel.getTaxiPhoto(taxiOrder.vehicle.photoFileName)

        taxiPhoto?.let {
            Row {
                ImageFromBitmap(
                    bitmap = it,
                    description = "vehicle: {model}, driver: {driverName} (reg: {reg})"
                )
                OrderInfoBody(taxiOrder)
            }
        }
    } else {
        // i hope and bet it cannot be go here, but if it is
        // also say to user, cannot load order
        navController.navigateUp()
    }
}

@Composable
fun OrderInfoBody(taxiOrder: TaxiOrder) {
    val vehicle = taxiOrder.vehicle
    val isSameCity = taxiOrder.startAddress.city == taxiOrder.endAddress.city
    val cityText =
        if (isSameCity) taxiOrder.startAddress.city
        else "${taxiOrder.startAddress.city} -> ${taxiOrder.endAddress.city}"

    Column {
        Text(text = vehicle.modelName)
        Text(text = vehicle.regNumber)
        Text(text = cityText, style = MaterialTheme.typography.bodyLarge)
        Text(
            text = "${taxiOrder.startAddress.address} -> ${taxiOrder.endAddress.address}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(text = taxiOrder.orderTime, style = MaterialTheme.typography.bodySmall)
        Text(text = vehicle.driverName)
        Text(
            text = "${taxiOrder.price.amount} ${taxiOrder.price.currency}",
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
