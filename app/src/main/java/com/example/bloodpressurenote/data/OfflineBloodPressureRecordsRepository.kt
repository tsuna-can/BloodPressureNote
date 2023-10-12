package com.example.bloodpressurenote.data

import kotlinx.coroutines.flow.Flow

class OfflineBloodPressureRecordsRepository(private val bloodPressureRecordDao: BloodPressureRecordDao) :
    BloodPressureRecordsRepository {
    override fun getAllItemsStream(): Flow<List<BloodPressureRecord>> =
        bloodPressureRecordDao.getAllItems()

    override fun getItemStream(id: Int): Flow<BloodPressureRecord?> =
        bloodPressureRecordDao.getItem(id)

    override suspend fun insertItem(bloodPressureRecord: BloodPressureRecord) =
        bloodPressureRecordDao.insert(bloodPressureRecord)

    override suspend fun deleteItem(bloodPressureRecord: BloodPressureRecord) =
        bloodPressureRecordDao.delete(bloodPressureRecord)

    override suspend fun updateItem(bloodPressureRecord: BloodPressureRecord) =
        bloodPressureRecordDao.update(bloodPressureRecord)
}
