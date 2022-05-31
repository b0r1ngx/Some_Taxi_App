package b0r1ngx.careers.roxiemobile.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PriceJson(
    val amount: Int,
    val currency: String
)

@JsonClass(generateAdapter = true)
data class Price(
    val amount: Float,
    val currency: Currency
)

enum class Currency(val symbol: String) {
    RUB("₽"),
    EUR("€"),
    GBP("£"),
    USD("$")
}
