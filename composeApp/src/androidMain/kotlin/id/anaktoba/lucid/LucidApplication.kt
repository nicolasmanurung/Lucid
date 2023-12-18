package id.anaktoba.lucid

import android.app.Application
import di.initKoin
import id.anaktoba.lucid.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent

class LucidApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger()
            androidContext(this@LucidApplication)
            modules(appModule)
        }
    }
}