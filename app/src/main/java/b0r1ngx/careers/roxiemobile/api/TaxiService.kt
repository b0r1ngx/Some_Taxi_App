package b0r1ngx.careers.roxiemobile.api

// Lib imports
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import retrofit2.Retrofit
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.URL

// import b0r1ngx.careers.roxiemobile.BuildConfig

const val BASE_URL = "https://www.roxiemobile.ru"
const val PHOTO_PATH = "/careers/test/images/"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
//    .add(LocalDateTimeAdapter)
    .build()

//private val httpCacheDirectory = // path to external memory
//private val cache = Cache(
//    directory = httpCacheDirectory,
//    maxSize = 10L * 1024 * 1024
//)
//    .addInterceptor(provideCacheInterceptorWhenOffline())
//    .addInterceptor(provideCacheInterceptorWhenOnline())
//    .cache(cache)

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(provideLoggingInterceptor())
    .build()

//private fun provideCacheInterceptorWhenOnline(): Interceptor =
//    Interceptor { }


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