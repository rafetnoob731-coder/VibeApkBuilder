package com.vibe.apkbuilder

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VibeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
