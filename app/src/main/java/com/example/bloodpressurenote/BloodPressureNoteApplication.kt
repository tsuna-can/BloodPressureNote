package com.example.bloodpressurenote

import android.app.Application
import com.example.bloodpressurenote.data.AppContainer
import com.example.bloodpressurenote.data.AppDataContainer

class BloodPressureNoteApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
