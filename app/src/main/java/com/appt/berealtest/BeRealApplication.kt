package com.appt.berealtest

import android.app.Application
import com.appt.berealtest.services.NetworkBeRealImageService

class BeRealApplication : Application() {
    val imageService = NetworkBeRealImageService()

    override fun onCreate() {
        super.onCreate()
    }
}
