package com.example.bloodpressurenote.ui.screens.InputScreen

import android.provider.Settings.Global.getString
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bloodpressurenote.R
import com.example.bloodpressurenote.ui.components.DatePickerComponent
import com.example.bloodpressurenote.ui.components.TextField

@Composable
fun InputScreen(
    viewModel: InputScreenViewModel = hiltViewModel(),
) {
    InputScreen(
        viewModel.inputUiState,
        viewModel::updateBloodPressure,
        viewModel::updateHeartRate,
        viewModel::updateNote,
        viewModel::updateDate,
        viewModel::saveItem,
        modifier = Modifier,
    )
}

@Composable
fun InputScreen(
    uiState: InputUiState,
    updateBloodPressure: (String, String) -> Unit,
    updateHeartRate: (String) -> Unit,
    updateNote: (String) -> Unit,
    updateDate: (Long?) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val bloodPressureDetails = uiState.bloodPressureDetails

    val enableSave = uiState.systolicBPErrorMessage == null &&
        uiState.diastolicBPErrorMessage == null &&
        uiState.heartRateErrorMessage == null &&
        uiState.noteErrorMessage == null &&
        bloodPressureDetails.systolicBloodPressure.isNotBlank() &&
        bloodPressureDetails.diastolicBloodPressure.isNotBlank() // TODO move to viewmodel

    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Overview Screen" },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        TextField(
            label = stringResource(id = R.string.systolic_blood_pressure),
            value = bloodPressureDetails.systolicBloodPressure,
            onValueChange = { updateBloodPressure(it, "systolic") },
            keyboardType = KeyboardType.Number,
            errorMessage = uiState.systolicBPErrorMessage?.getString(LocalContext.current)
                ?: "",
            focusManager = focusManager,
        )

        TextField(
            label = stringResource(id = R.string.diastolic_blood_pressure),
            value = bloodPressureDetails.diastolicBloodPressure,
            onValueChange = { updateBloodPressure(it, "diastolic") },
            keyboardType = KeyboardType.Number,
            errorMessage = uiState.diastolicBPErrorMessage?.getString(LocalContext.current)
                ?: "",
            focusManager = focusManager,
        )

        TextField(
            label = stringResource(id = R.string.heart_rate),
            value = bloodPressureDetails.heartRate,
            onValueChange = { updateHeartRate(it) },
            keyboardType = KeyboardType.Number,
            errorMessage = uiState.heartRateErrorMessage?.getString(LocalContext.current) ?: "",
            focusManager = focusManager,
        )

        TextField(
            label = stringResource(id = R.string.note),
            value = bloodPressureDetails.note,
            onValueChange = {
                updateNote(it)
            },
            maxLines = 3,
            singleLine = false,
            focusManager = focusManager,
            errorMessage = uiState.noteErrorMessage?.getString(LocalContext.current) ?: "",
        )

        DatePickerComponent(bloodPressureDetails.date, onChangeValue = updateDate)

        Button(
            onClick = onSave,
            modifier = Modifier.padding(16.dp),
            enabled = enableSave,
        ) {
            Text(stringResource(id = R.string.save_button))
        }
    }
}
