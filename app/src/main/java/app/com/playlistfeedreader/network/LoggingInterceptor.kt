package app.com.playlistfeedreader.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

internal class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val t1 = System.nanoTime()
        Log.d("okhttp", String.format("Sending request %s on %s%n%s%n%s",
                request.url(), chain.connection(), request.headers(), request.body().toString()))


        val response = chain.proceed(request)
        val bodyNow = response.message().toString()
        val t2 = System.nanoTime()
        Log.d("okhttp", String.format("Received response for %s in %.1fms%n%s%n%s",
                response.request().url(), (t2 - t1) / 1e6, response.headers(), bodyNow))

        return response
    }
}