package b0r1ngx.careers.roxiemobile.api

// Lib imports
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import b0r1ngx.careers.roxiemobile.utils.LocalDateTimeAdapter
import b0r1ngx.careers.roxiemobile.utils.PriceAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.URL

const val BASE_URL = "https://www.roxiemobile.ru"
const val PHOTO_PATH = "/careers/test/images/"

val moshi: Moshi = Moshi.Builder()
    .add(PriceAdapter())
    .add(LocalDateTimeAdapter())
    .add(KotlinJsonAdapterFactory())
    .build()

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(provideLoggingInterceptor())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(okHttpClient)
    .baseUrl(BASE_URL)
    .build()

private val r = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(BASE_URL)
    .build()


private fun provideLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
        level = if (true) { // BuildConfig.DEBUG
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

object TaxiService {
    val retrofitService: TaxiApi by lazy {
        retrofit.create(TaxiApi::class.java)
    }

    /*
     * Allow to fetch photo from server as Bitmap
     */
    fun getPhotoBitmap(fileName: String): Bitmap =
        BitmapFactory.decodeStream(
            URL(BASE_URL + PHOTO_PATH + fileName)
                .openStream())
}