package b0r1ngx.careers.roxiemobile.data

import com.squareup.moshi.JsonClass

// todo: make currency to Currency as RUB to Currency.RUB, and etc...
@JsonClass(generateAdapter = true)
data class Price (
    val amount: Int,
    val currency: String
)

data class RightPrice (
    val amount: Float,
    val currency: Currency
)

enum class Currency(val symbol: String) {
    RUB("₽"),
    EUR("€"),
    GBP("£"),
    USD("$")
}
