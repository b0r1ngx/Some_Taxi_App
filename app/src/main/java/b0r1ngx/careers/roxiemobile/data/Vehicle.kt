package b0r1ngx.careers.roxiemobile.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Vehicle(
    val regNumber: String,
    val modelName: String,

    @Json(name = "photo")
    val photoFileName: String,
    val driverName: String
)
