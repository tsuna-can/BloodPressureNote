package com.example.bloodpressurenote.data

import com.example.bloodpressurenote.data.dao.BloodPressureRecordDao
import com.example.bloodpressurenote.data.entity.AverageEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class OfflineBloodPressureRecordsRepository @Inject constructor(
    private val bloodPressureRecordDao: BloodPressureRecordDao,
) : BloodPressureRecordsRepository {
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

    override fun getAverageRecord(): Flow<AverageEntity> {
        return bloodPressureRecordDao.getAverageRecord()
    }

    override fun getAllBloodPressure(): Flow<Pair<List<Int>, List<Int>>> {
        return bloodPressureRecordDao.getAllSystolicBloodPressure()
            .zip(bloodPressureRecordDao.getAllDiastolicBloodPressure()) { systolic, diastolic ->
                Pair(
                    systolic.map { it.systolicBloodPressure },
                    diastolic.map { it.diastolicBloodPressure },
                )
            }
    }
}
