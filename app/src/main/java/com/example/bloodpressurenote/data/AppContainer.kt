package com.example.bloodpressurenote.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val bloodPressureRecordsRepository: BloodPressureRecordsRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val bloodPressureRecordsRepository: BloodPressureRecordsRepository by lazy {
        OfflineBloodPressureRecordsRepository(
            BloodPressureNoteDatabase.getDatabase(context).bloodPressureRecordDao()
        )
    }
}