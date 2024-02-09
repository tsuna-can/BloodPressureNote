package com.example.bloodpressurenote.di

import android.content.Context
import androidx.room.Room
import com.example.bloodpressurenote.data.BloodPressureNoteDatabase
import com.example.bloodpressurenote.data.dao.BloodPressureRecordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): BloodPressureNoteDatabase {
        return Room.databaseBuilder(
            appContext,
            BloodPressureNoteDatabase::class.java,
            "blood_pressure_note_database.db",
        ).build()
    }

    @Provides
    fun provideBloodPressureRecordDao(database: BloodPressureNoteDatabase): BloodPressureRecordDao {
        return database.bloodPressureRecordDao()
    }
}
