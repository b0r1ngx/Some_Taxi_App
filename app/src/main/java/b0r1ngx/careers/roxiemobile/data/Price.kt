package b0r1ngx.careers.roxiemobile.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Price (
    val amount: Int,
    val currency: String
    // need to make currency to Currency as RUB to Currency.RUB, and etc...
)

enum class Currency(val symbol: String) {
    RUB("₽"),
    EUR("€"),
    GBP("£"),
    USD("$")
}
