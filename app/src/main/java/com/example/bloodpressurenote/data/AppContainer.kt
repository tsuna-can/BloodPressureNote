package com.example.bloodpressurenote.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val bloodPressureRecordsRepository: BloodPressureRecordsRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val bloodPressureRecordsRepository: BloodPressureRecordsRepository by lazy {
        OfflineBloodPressureRecordsRepository(
            BloodPressureNoteDatabase.getDatabase(context).bloodPressureRecordDao()
        )
    }
}