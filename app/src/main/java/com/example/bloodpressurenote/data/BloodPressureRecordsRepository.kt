package com.example.bloodpressurenote.data

import com.example.bloodpressurenote.data.entity.AverageEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Item] from a given data source.
 */
interface BloodPressureRecordsRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllItemsStream(): Flow<List<BloodPressureRecord>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getItemStream(id: Int): Flow<BloodPressureRecord?>

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(bloodPressureRecord: BloodPressureRecord)

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(bloodPressureRecord: BloodPressureRecord)

    /**
     * Update item in the data source
     */
    suspend fun updateItem(bloodPressureRecord: BloodPressureRecord)

    fun getAverageRecord(): Flow<AverageEntity>

    fun getAllBloodPressure(): Flow<Pair<List<Int>, List<Int>>>
}
