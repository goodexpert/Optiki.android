package com.example.apps.optiki

import android.app.Application

// appComponent lives in the Application class to share its lifecycle
class AppApplication : Application() {
    // Reference to the application graph that is used across the whole app
    val appComponent = DaggerAppComponent.create()
}