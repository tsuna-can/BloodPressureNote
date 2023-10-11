package com.example.bloodpressurenote.ui.components.screens.InputScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bloodpressurenote.data.BloodPressureRecord
import com.example.bloodpressurenote.data.BloodPressureRecordsRepository

class InputScreenViewModel(private val bloodPressureRecordsRepository: BloodPressureRecordsRepository) :
    ViewModel() {

    var inputUiState by mutableStateOf(InputUiState())
        private set

    fun updateUiState(bloodPressureDetails: BloodPressureDetails) {
        inputUiState = InputUiState(
            bloodPressureDetails = bloodPressureDetails,
            enableSave = bloodPressureDetails.systolicBloodPressure.isNotBlank() && bloodPressureDetails.diastolicBloodPressure.isNotBlank()
        )
    }

    private fun validateInput(uiState: BloodPressureDetails = inputUiState.bloodPressureDetails): Boolean {
        return with(uiState) {
            systolicBloodPressure.toIntOrNull() != null
                    && diastolicBloodPressure.toIntOrNull() != null
                    && heartRate.toIntOrNull() != null
        }
    }

    suspend fun saveItem() {
        if (validateInput()) {
            bloodPressureRecordsRepository.insertItem(inputUiState.bloodPressureDetails.toBloodPressureRecord())
        }
    }

}

data class InputUiState(
    val bloodPressureDetails: BloodPressureDetails = BloodPressureDetails(),
    val isSystolicBloodPressureValid: Boolean = true,
    val isDiastolicBloodPressureValid: Boolean = true,
    val isHeartRateValid: Boolean = true,
    val enableSave: Boolean = false,
)

data class BloodPressureDetails(
    val id: Int = 0,
    val systolicBloodPressure: String = "",
    val diastolicBloodPressure: String = "",
    val heartRate: String = "",
    val note: String = "",
)

fun BloodPressureDetails.toBloodPressureRecord(): BloodPressureRecord = BloodPressureRecord(
    id = id,
    systolic_blood_pressure = systolicBloodPressure.toIntOrNull() ?: 0,
    diastolic_blood_pressure = diastolicBloodPressure.toIntOrNull() ?: 0,
    heart_rate = heartRate.toIntOrNull() ?: 0,
    note = note
)
