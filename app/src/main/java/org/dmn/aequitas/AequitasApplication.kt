package org.dmn.aequitas

import android.app.Application
import org.dmn.aequitas.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class AequitasApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@AequitasApplication)
            modules(
                AppModule,
            )
        }
    }
}