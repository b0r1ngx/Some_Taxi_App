package b0r1ngx.careers.roxiemobile.utils

import android.util.Log
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

const val SERVER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX"
val DATE_FORMAT = DateTimeFormatter.ofPattern("HH:mm, dd-MM-yyyy")

// Переводим время в зависимости от того где находится устройство пользователя
fun String.toLocalDateTime(): LocalDateTime =
    LocalDateTime.parse(
        this,
        DateTimeFormatter.ofPattern(SERVER_DATE_FORMAT)
    )

//class LocalDateTimeAdapter {
//    @ToJson
//    fun toJson(value: LocalDateTime)=
//        value.toString()
//
//
//    @FromJson
//    fun fromJson(value: String) =
//        value.toLocalDateTime()
//
//    companion object {
//
//    }
//}

//class LocalDateTimeAdapter {
//    @ToJson
//    fun toJson(value: LocalDateTime): String {
//        return FORMATTER.format(value)
//    }
//
//    @FromJson
//    fun fromJson(value: String): Date {
//        Log.d("Test", "$value")
//        Log.d("Test", "${FORMATTER.parse(value)}")
//        return FORMATTER.parse(value)
//    }
//
//    companion object {
//        val FORMATTER = SimpleDateFormat(SERVER_DATE_FORMAT)
//    }
//}