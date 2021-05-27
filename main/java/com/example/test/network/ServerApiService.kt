import com.example.test.model.Post
import com.example.test.network.*
import com.example.test.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


/**
 * Add a httpclient logger for debugging purpose
 * object.
 */
fun getLoggerIntercepter(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */

private val serverListRetrofit = Retrofit.Builder()
    .client(
        OkHttpClient.Builder()
            .addInterceptor(getLoggerIntercepter())
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    )
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.APP_BASE_URL)
    .build()

interface ServerApiService {

    @GET("api/temp")
    suspend fun getTemp(): NetworkTempContainer

    @GET("api/downdetector")
    suspend fun getDowndetector(): NetworkDowndetectorContainer

    @GET("api/getservers")
    suspend fun getServers(): NetworkGetServersContainer

    @GET("api/temphistory")
    suspend fun getTempHistory(): NetworkTempHistoryContainer

    @POST("api/wol")
    suspend fun startServer(
        @Body post: Post
    ): Response<Post>


}

object TMDBApi {
    val SERVER_LIST_RETROFIT_SERVICE: ServerApiService by lazy {
        serverListRetrofit.create(
            ServerApiService::class.java
        )
    }
}