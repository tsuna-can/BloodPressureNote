package com.example.bloodpressurenote.ui.screens.input

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloodpressurenote.R
import com.example.bloodpressurenote.data.BloodPressureRecord
import com.example.bloodpressurenote.data.BloodPressureRecordsRepository
import com.example.bloodpressurenote.util.ResStringResource
import com.example.bloodpressurenote.util.StringResource
import com.example.bloodpressurenote.util.validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class InputScreenViewModel @Inject constructor(
    private val bloodPressureRecordsRepository: BloodPressureRecordsRepository,
) : ViewModel() {

    var inputUiState by mutableStateOf(InputUiState())
        private set

    fun updateBloodPressure(value: String, bloodPressureType: String) {
        val validationResult =
            validator(value = value, maxLength = 3, isNumeric = true)
        inputUiState =
            when (bloodPressureType) {
                "systolic" -> {
                    inputUiState.copy(
                        bloodPressureDetails = inputUiState.bloodPressureDetails.copy(
                            systolicBloodPressure = value,
                        ),
                        systolicBPErrorMessage = validationResult,
                        enableSave = enableSave(),
                    )
                }

                "diastolic" -> {
                    inputUiState.copy(
                        bloodPressureDetails = inputUiState.bloodPressureDetails.copy(
                            diastolicBloodPressure = value,
                        ),
                        diastolicBPErrorMessage = validationResult,
                        enableSave = enableSave(),
                    )
                }

                else -> inputUiState
            }
    }

    fun updateHeartRate(value: String) {
        inputUiState =
            inputUiState.copy(
                bloodPressureDetails = inputUiState.bloodPressureDetails.copy(heartRate = value),
                heartRateErrorMessage = validator(
                    value = value,
                    maxLength = 3,
                    allowBlank = true,
                    isNumeric = true,
                ),
                enableSave = enableSave(),
            )
    }

    fun updateNote(value: String) {
        inputUiState =
            inputUiState.copy(
                bloodPressureDetails = inputUiState.bloodPressureDetails.copy(note = value),
                noteErrorMessage = validator(value = value, maxLength = 100, allowBlank = true),
                enableSave = enableSave(),
            )
    }

    fun updateDate(value: Long?) {
        inputUiState =
            inputUiState.copy(
                bloodPressureDetails = inputUiState.bloodPressureDetails.copy(
                    date = value ?: Date().time,
                ),
                enableSave = enableSave(),
            )
    }

    fun saveItem() {
        viewModelScope.launch {
            bloodPressureRecordsRepository.insertItem(
                inputUiState.bloodPressureDetails.toBloodPressureRecord(),
            )
            refreshViewModel()
            showSnackbar(ResStringResource.create(R.string.save_success))
        }
    }

    private fun enableSave(): Boolean {
        return with(inputUiState) {
            systolicBPErrorMessage == null &&
                diastolicBPErrorMessage == null &&
                heartRateErrorMessage == null &&
                noteErrorMessage == null &&
                bloodPressureDetails.systolicBloodPressure.isNotBlank() &&
                bloodPressureDetails.diastolicBloodPressure.isNotBlank()
        }
    }

    private fun showSnackbar(message: StringResource) {
        val newEvents = inputUiState.events + Event.ShowSnackbar(message)
        inputUiState = inputUiState.copy(events = newEvents)
    }

    private fun refreshViewModel() {
        inputUiState = InputUiState()
    }

    fun consumeEvent(event: Event) {
        val newEvents = inputUiState.events.filterNot { it == event }
        inputUiState = inputUiState.copy(events = newEvents)
    }
}

data class InputUiState(
    val bloodPressureDetails: BloodPressureDetails = BloodPressureDetails(),
    val systolicBPErrorMessage: StringResource? = null,
    val diastolicBPErrorMessage: StringResource? = null,
    val heartRateErrorMessage: StringResource? = null,
    val noteErrorMessage: StringResource? = null,
    val enableSave: Boolean = false,
    val events: List<Event> = emptyList(),
)

data class BloodPressureDetails(
    val id: Int = 0,
    val systolicBloodPressure: String = "",
    val diastolicBloodPressure: String = "",
    val heartRate: String = "",
    val note: String = "",
    val date: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).times(1000),
)

sealed interface Event {
    data class ShowSnackbar(val message: StringResource) : Event
}

fun BloodPressureDetails.toBloodPressureRecord(): BloodPressureRecord = BloodPressureRecord(
    id = id,
    systolicBloodPressure = systolicBloodPressure.toIntOrNull() ?: 0,
    diastolicBloodPressure = diastolicBloodPressure.toIntOrNull() ?: 0,
    heartRate = heartRate.toIntOrNull() ?: 0,
    note = note,
    createdAt = Date(date),
)
