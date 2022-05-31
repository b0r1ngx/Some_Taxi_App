package b0r1ngx.careers.roxiemobile.data

import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

// Generating MoshiAdapter in compile-time instead of runtime -
// ускорение Android Decoding Time (2x+ time) и по-итогу общего времени при получение данных
// (при выполнении интернет запросов)
@JsonClass(generateAdapter = true)
data class TaxiOrder(
    val id: Int,
    val startAddress: Address,
    val endAddress: Address,
    val price: Price,
    val orderTime: LocalDateTime,
    val vehicle: Vehicle
)
