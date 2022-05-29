package b0r1ngx.careers.roxiemobile.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.toLocalDateTime(): LocalDateTime {
    val datePattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
    val format = DateTimeFormatter.ofPattern(datePattern)

    // Переводим время в зависимости от того где находится устройство пользователя
    return LocalDateTime.parse(this, format)
}