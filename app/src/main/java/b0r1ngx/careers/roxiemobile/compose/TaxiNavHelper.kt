package b0r1ngx.careers.roxiemobile.compose

import androidx.compose.runtime.Composable

/* The [TaxiNavHelper]
 * All screens (2) are built using composables. The two screens are declared in this file.
 * We map these screens to navigation destinations, with Orders as the start destination.
 * Also move the composables away from TaxiNavHelper and into a NavHost.
 */

enum class TaxiNavHelper {
    Orders, Order;

//  Use this in some cases (eg. to create and use NavBarMenu)
//    companion object {
//        fun fromRoute(route: String?): TaxiNavHelper =
//            when(route?.substringBefore("/")) {
//                Orders.name -> Orders
//                Order.name -> Order
//                null -> Orders // navigate to start screen
//                else -> throw IllegalArgumentException("Route $route is not")
//            }
//    }
}