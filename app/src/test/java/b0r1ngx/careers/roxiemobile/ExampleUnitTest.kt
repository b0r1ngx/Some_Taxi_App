package b0r1ngx.careers.roxiemobile

import b0r1ngx.careers.roxiemobile.api.TaxiService
import b0r1ngx.careers.roxiemobile.api.moshi
import b0r1ngx.careers.roxiemobile.data.Price
import b0r1ngx.careers.roxiemobile.data.TaxiOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.json.JSONObject
import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDateTime

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun moshiTimeConvert() {
        val jsonTimeObject = JSONObject("{\n" +
                "        \"orderTime\": \"2015-08-27T16:36:56+03:00\"\n" +
                "    }")
        val timeString = jsonTimeObject.toString()
        val jsonAdapter = moshi.adapter(LocalDateTime::class.java)
        val time = jsonAdapter.fromJson(timeString)
        println(time)
        assertEquals(4, 2 + 2)
    }

    @Test
    fun moshiPriceConvert() {
        val priceJson = JSONObject("{\n" +
                "        \"price\": {\n" +
                "            \"amount\": 45000,\n" +
                "            \"currency\": \"RUB\"\n" +
                "        }\n" +
                "    }")

        val priceJsonString = priceJson.toString()
        val jsonAdapter = moshi.adapter(Price::class.java)
        val price = jsonAdapter.fromJson(priceJsonString)
        println(price)
        assertEquals(4, 2 + 2)
    }

    @Test
    fun moshiJsonConvert() {
        val jsonObject = JSONObject("{\n" +
                "        \"id\": 465,\n" +
                "        \"startAddress\": {\n" +
                "            \"city\": \"Санкт-Петербург\",\n" +
                "            \"address\": \"Пр. Ленина, д. 1\"\n" +
                "        },\n" +
                "        \"endAddress\": {\n" +
                "            \"city\": \"Санкт-Петербург\",\n" +
                "            \"address\": \"Пр. Невский, д. 126\"\n" +
                "        },\n" +
                "        \"price\": {\n" +
                "            \"amount\": 45000,\n" +
                "            \"currency\": \"RUB\"\n" +
                "        },\n" +
                "        \"orderTime\": \"2015-08-27T16:36:56+03:00\",\n" +
                "        \"vehicle\": {\n" +
                "            \"regNumber\": \"х555мт98\",\n" +
                "            \"modelName\": \"Nissan Almera\",\n" +
                "            \"photo\": \"01.jpg\",\n" +
                "            \"driverName\": \"Иванов Иван Иванович\"\n" +
                "        }\n" +
                "    }")

        println(jsonObject)

        val adapter = moshi.adapter(TaxiOrder::class.java)
        val convert = adapter.fromJson(jsonObject.toString())
        println(convert)
        assertEquals(4, 2 + 2)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun request() = runTest {
        try {
            val r = TaxiService.retrofitService.getOrders()
            println(r)
        } catch (e: Exception) {
            println("request() test error: ${e.printStackTrace()}")
        }
    }
}