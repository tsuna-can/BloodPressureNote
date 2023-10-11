package com.example.bloodpressurenote.ui.components.screens.InputScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bloodpressurenote.R
import com.example.bloodpressurenote.ui.AppViewModelProvider
import com.example.bloodpressurenote.ui.components.TextField
import kotlinx.coroutines.launch

@Composable
fun InputScreen(
    viewModel: InputScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Overview Screen" },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val bloodPressureDetails = viewModel.inputUiState.bloodPressureDetails
        val inputUiState = viewModel.inputUiState

        TextField(
            label = stringResource(id = R.string.systolic_blood_pressure),
            value = bloodPressureDetails.systolicBloodPressure,
            onValueChange = {
                viewModel.updateUiState(
                    bloodPressureDetails.copy(
                        systolicBloodPressure = it
                    )
                )
            },
            isError = !inputUiState.isSystolicBloodPressureValid,
            keyboardType = KeyboardType.Number,
            errorMessage = stringResource(id = R.string.error_not_number),
            focusManager = focusManager,
        )

        TextField(
            label = stringResource(id = R.string.diastolic_blood_pressure),
            value = bloodPressureDetails.diastolicBloodPressure,
            onValueChange = {
                viewModel.updateUiState(
                    bloodPressureDetails.copy(
                        diastolicBloodPressure = it
                    )
                )
            },
            isError = !inputUiState.isDiastolicBloodPressureValid,
            keyboardType = KeyboardType.Number,
            errorMessage = stringResource(id = R.string.error_not_number),
            focusManager = focusManager,
        )

        TextField(
            label = stringResource(id = R.string.heart_rate),
            value = bloodPressureDetails.heartRate,
            onValueChange = {
                viewModel.updateUiState(
                    bloodPressureDetails.copy(
                        heartRate = it
                    )
                )
            },
            isError = !inputUiState.isHeartRateValid,
            keyboardType = KeyboardType.Number,
            errorMessage = stringResource(id = R.string.error_not_number),
            focusManager = focusManager
        )

        TextField(
            label = stringResource(id = R.string.note),
            value = bloodPressureDetails.note,
            onValueChange = {
                viewModel.updateUiState(
                    bloodPressureDetails.copy(
                        note = it
                    )
                )
            },
            maxLines = 3,
            singleLine = false,
            focusManager = focusManager
        )

        Button(
            onClick = {
                coroutineScope.launch {
                    viewModel.saveItem()
                }
            },
            modifier = Modifier
                .padding(16.dp),
            enabled = inputUiState.enableSave
        ) {
            Text(stringResource(id = R.string.save_button))
        }
    }
}
