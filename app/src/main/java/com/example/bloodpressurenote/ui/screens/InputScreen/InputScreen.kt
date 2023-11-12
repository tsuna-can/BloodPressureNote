package com.example.bloodpressurenote.ui.screens.InputScreen

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
    modifier: Modifier = Modifier,
    viewModel: InputScreenViewModel = hiltViewModel(),
) {
    val focusManager = LocalFocusManager.current
    val uiState = viewModel.inputUiState
    val bloodPressureDetails = viewModel.inputUiState.bloodPressureDetails

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Overview Screen" },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        TextField(
            label = stringResource(id = R.string.systolic_blood_pressure),
            value = bloodPressureDetails.systolicBloodPressure,
            onValueChange = {
                viewModel.updateSystolicBloodPressure(it)
            },
            keyboardType = KeyboardType.Number,
            isError = uiState.isSystolicBloodPressureValid != null,
            errorMessage = getErrorText(uiState.isSystolicBloodPressureValid),
            focusManager = focusManager,
        )

        TextField(
            label = stringResource(id = R.string.diastolic_blood_pressure),
            value = bloodPressureDetails.diastolicBloodPressure,
            onValueChange = {
                viewModel.updateDiastolicBloodPressure(it)
            },
            keyboardType = KeyboardType.Number,
            isError = uiState.isDiastolicBloodPressureValid != null,
            errorMessage = getErrorText(errorType = uiState.isDiastolicBloodPressureValid),
            focusManager = focusManager,
        )

        TextField(
            label = stringResource(id = R.string.heart_rate),
            value = bloodPressureDetails.heartRate,
            onValueChange = {
                viewModel.updateHeartRate(it)
            },
            keyboardType = KeyboardType.Number,
            isError = uiState.isHeartRateValid != null,
            errorMessage = getErrorText(errorType = uiState.isHeartRateValid),
            focusManager = focusManager
        )

        TextField(
            label = stringResource(id = R.string.note),
            value = bloodPressureDetails.note,
            onValueChange = {
                viewModel.updateNote(it)
            },
            maxLines = 3,
            singleLine = false,
            focusManager = focusManager,
            isError = uiState.isNoteValid != null,
            errorMessage = getErrorText(errorType = uiState.isNoteValid)
        )

        DatePickerComponent(bloodPressureDetails.date, onChangeValue = viewModel::updateDate)

        Button(
            onClick = { viewModel.saveItem() },
            modifier = Modifier.padding(16.dp),
            enabled = isEnableSave(uiState = uiState)
        ) {
            Text(stringResource(id = R.string.save_button))
        }
    }
}

@Composable
fun getErrorText(errorType: ErrorType?): String {
    return when (errorType) {
        ErrorType.NOT_NUMERIC -> stringResource(id = R.string.error_not_number)
        ErrorType.MORE_THAN_3_DIGITS -> stringResource(id = R.string.error_more_3_digits)
        ErrorType.MORE_THAN_100_DIGITS -> stringResource(id = R.string.error_more_100_digits)
        ErrorType.IS_BLANK -> stringResource(id = R.string.error_mandatory)
        else -> ""
    }
}

// TODO move this logic to ViewModel
@Composable
fun isEnableSave(uiState: InputUiState): Boolean {
    return uiState.isSystolicBloodPressureValid == null
            && uiState.isDiastolicBloodPressureValid == null
            && uiState.isHeartRateValid == null
            && uiState.isNoteValid == null
            && uiState.bloodPressureDetails.systolicBloodPressure.isNotBlank()
            && uiState.bloodPressureDetails.diastolicBloodPressure.isNotBlank()
}
