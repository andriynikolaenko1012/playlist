package app.com.playlistfeedreader

import android.annotation.SuppressLint
import android.app.Application
import app.com.playlistfeedreader.network.APIService
import app.com.playlistfeedreader.network.RetrofitFactory
import com.github.salomonbrys.kodein.*
import org.jetbrains.anko.ctx


class App : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        bind<APIService>() with provider { RetrofitFactory.getBuilder(ctx.getString(R.string.api_base_url)).create(
            APIService::class.java) }
    }

    @SuppressLint("BatteryLife")
    override fun onCreate() {
        super.onCreate()

    }
}