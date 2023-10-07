package com.example.bloodpressurenote.components.screens.InputScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bloodpressurenote.R

@Composable
fun InputScreen(
    message: String,
    viewModel: InputScreenViewModel = viewModel(),
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Overview Screen" },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val bloodPressureDetails = viewModel.inputUiState.bloodPressureDetails
        val inputUiState = viewModel.inputUiState

        Text(text = message)

        OutlinedTextField(
            label = { Text(stringResource(id = R.string.systolic_blood_pressure)) },
            value = bloodPressureDetails.systolicBloodPressure,
            onValueChange = {
                viewModel.updateUiState(
                    bloodPressureDetails.copy(
                        systolicBloodPressure = it
                    )
                )
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            isError = inputUiState.isSystolicBloodPressureValid.isNotBlank()
        )

        OutlinedTextField(
            label = { Text(stringResource(id = R.string.diastolic_blood_pressure)) },
            value = bloodPressureDetails.diastolicBloodPressure,
            onValueChange = {
                viewModel.updateUiState(
                    bloodPressureDetails.copy(
                        diastolicBloodPressure = it
                    )
                )
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        OutlinedTextField(
            label = { Text(stringResource(id = R.string.heart_rate)) },
            value = bloodPressureDetails.heartRate,
            onValueChange = {
                viewModel.updateUiState(
                    bloodPressureDetails.copy(
                        heartRate = it
                    )
                )
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        OutlinedTextField(
            label = { Text(stringResource(id = R.string.note)) },
            value = bloodPressureDetails.note,
            onValueChange = {
                viewModel.updateUiState(
                    bloodPressureDetails.copy(
                        note = it
                    )
                )
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            maxLines = 3,
            singleLine = false,
        )

        Button(
            onClick = {},
            modifier = Modifier.padding(16.dp)
        ) {
            Text(stringResource(id = R.string.save_button))
        }
    }
}