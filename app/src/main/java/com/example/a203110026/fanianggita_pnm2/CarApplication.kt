package com.example.a203110026.fanianggita_pnm2

import android.app.Application
import timber.log.Timber

class CarApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
