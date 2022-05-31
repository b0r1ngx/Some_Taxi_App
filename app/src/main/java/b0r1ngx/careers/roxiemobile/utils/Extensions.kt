package b0r1ngx.careers.roxiemobile.utils

import com.squareup.moshi.ToJson
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonDataException
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime
import b0r1ngx.careers.roxiemobile.data.*

const val SERVER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX"
val DATE_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm, dd-MM-yyyy")

// Переводим время в зависимости от того где находится устройство пользователя
fun String.toLocalDateTime(): LocalDateTime =
    LocalDateTime.parse(this,
        DateTimeFormatter.ofPattern(SERVER_DATE_FORMAT))

class LocalDateTimeAdapter {
    @ToJson fun toJson(value: LocalDateTime) =
        DATE_FORMAT.format(value)

    @FromJson fun fromJson(value: String) =
        value.toLocalDateTime()
}

class PriceAdapter {
    @ToJson fun toJson(price: Price) =
        PriceJson((price.amount * 100).toInt(), price.currency.name)

    @FromJson fun fromJson(priceJson: PriceJson): Price {
        val char = priceJson.currency[0]
        val amount = priceJson.amount.toFloat() / 100
        return when (char) {
            'R' -> Price(amount, Currency.RUB)
            'E' -> Price(amount, Currency.EUR)
            'G' -> Price(amount, Currency.GBP)
            'U' -> Price(amount, Currency.USD)
            else -> throw JsonDataException("PriceAdapter, unknown currency: $priceJson")
        }
    }
}