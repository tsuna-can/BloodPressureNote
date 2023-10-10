package com.example.bloodpressurenote.ui.components.screens.InputScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.util.Date

class InputScreenViewModel : ViewModel() {

    var inputUiState by mutableStateOf(InputUiState())
        private set

    fun updateUiState(bloodPressureDetails: BloodPressureDetails) {
        val isSystolicValid = isInputValid(bloodPressureDetails.systolicBloodPressure)
        val isDiastolicValid = isInputValid(bloodPressureDetails.diastolicBloodPressure)
        val isHeartRateValid = isInputValid(bloodPressureDetails.heartRate)

        inputUiState = InputUiState(
            bloodPressureDetails = bloodPressureDetails,
            isSystolicBloodPressureValid = isSystolicValid,
            isDiastolicBloodPressureValid = isDiastolicValid,
            isHeartRateValid = isHeartRateValid,
            enableSave = bloodPressureDetails.systolicBloodPressure.isNotBlank() && bloodPressureDetails.diastolicBloodPressure.isNotBlank()
        )
    }

    private fun isInputValid(value: String): Boolean {
        return value.isEmpty() || value.toIntOrNull() != null
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
    val date: Date = Date()
)