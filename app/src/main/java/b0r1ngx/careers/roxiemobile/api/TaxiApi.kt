package b0r1ngx.careers.roxiemobile.api

// Lib imports
import retrofit2.http.GET

// Local imports
import b0r1ngx.careers.roxiemobile.data.TaxiOrder

interface TaxiApi {
    @GET("careers/test/orders.json")
    suspend fun getOrders(): MutableList<TaxiOrder>
}