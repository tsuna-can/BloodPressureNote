package com.example.bloodpressurenote.di

import com.example.bloodpressurenote.data.BloodPressureRecordsRepository
import com.example.bloodpressurenote.data.OfflineBloodPressureRecordsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsBloodPressureRecordsRepository(
        bloodPressureRecordsRepository: OfflineBloodPressureRecordsRepository,
    ): BloodPressureRecordsRepository
}
