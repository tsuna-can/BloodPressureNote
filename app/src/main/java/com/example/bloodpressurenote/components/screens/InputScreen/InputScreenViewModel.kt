package com.example.bloodpressurenote.components.screens.InputScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.util.Date

class InputScreenViewModel : ViewModel() {

    var inputUiState by mutableStateOf(InputUiState())
        private set

    fun updateUiState(bloodPressureDetails: BloodPressureDetails) {
        val isSystolicBloodPressureValid =
            bloodPressureDetails.systolicBloodPressure.toIntOrNull() != null
        inputUiState =
            InputUiState(bloodPressureDetails = bloodPressureDetails)
    }

    fun validateInputAndGetErrorMessage(input: String): String {
        val result = input.toIntOrNull()
        return if (result != null) {
            ""
        } else {
            "Error"
        }
    }

}

data class InputUiState(
    val bloodPressureDetails: BloodPressureDetails = BloodPressureDetails(),
    val isSystolicBloodPressureValid: String = "",
)

data class BloodPressureDetails(
    val id: Int = 0,
    val systolicBloodPressure: String = "",
    val diastolicBloodPressure: String = "",
    val heartRate: String = "",
    val note: String = "",
    val date: Date = Date()
)