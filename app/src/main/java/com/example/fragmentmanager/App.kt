package com.example.fragmentmanager

import android.app.Application
import com.model.fragmentmanager.tools.FragmentManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        FragmentManager.init(this)
    }
}