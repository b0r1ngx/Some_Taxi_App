package b0r1ngx.careers.roxiemobile.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Address (
    val city: String,
    val address: String
)
