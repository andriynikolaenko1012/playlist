package app.com.playlistfeedreader.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    private val basicOkHttpClientBuilder: OkHttpClient.Builder
        get() = OkHttpClient.Builder()
                .addInterceptor(LoggingInterceptor())
                .connectTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)

    fun getBuilder(baseUrl: String): Retrofit {
        return Retrofit.Builder()
                .client(basicOkHttpClientBuilder.build())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}
