package com.example.bloodpressurenote.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bloodpressurenote.data.dao.BloodPressureRecordDao
import com.example.bloodpressurenote.util.Converters

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [BloodPressureRecord::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BloodPressureNoteDatabase : RoomDatabase() {

    abstract fun bloodPressureRecordDao(): BloodPressureRecordDao
}
