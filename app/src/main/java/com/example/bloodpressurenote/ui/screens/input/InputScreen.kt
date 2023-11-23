package com.example.bloodpressurenote.ui.screens.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
import com.example.bloodpressurenote.ui.components.ErrorTextArgs
import com.example.bloodpressurenote.ui.components.TextField
import com.example.bloodpressurenote.ui.components.TextFieldArgs

@Composable
fun InputScreen(
    modifier: Modifier = Modifier,
    viewModel: InputScreenViewModel = hiltViewModel(),
) {
    InputScreen(
        viewModel.inputUiState,
        viewModel::updateBloodPressure,
        viewModel::updateHeartRate,
        viewModel::updateNote,
        viewModel::updateDate,
        viewModel::saveItem,
        modifier = modifier,
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
    val focusRequester = remember { FocusRequester() }
    val bloodPressureDetails = uiState.bloodPressureDetails

    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Overview Screen" },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        TextField(
            TextFieldArgs(
                value = bloodPressureDetails.systolicBloodPressure,
                onValueChange = { updateBloodPressure(it, "systolic") },
                modifier = Modifier.focusRequester(focusRequester),
                label = stringResource(id = R.string.systolic_blood_pressure),
                keyboardType = KeyboardType.Number,
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                },
                maxLines = 1,
                singleLine = true,
            ),
            ErrorTextArgs(
                text = uiState.systolicBPErrorMessage?.getString(LocalContext.current) ?: "",
            ),
        )

        TextField(
            TextFieldArgs(
                value = bloodPressureDetails.diastolicBloodPressure,
                onValueChange = { updateBloodPressure(it, "diastolic") },
                label = stringResource(id = R.string.diastolic_blood_pressure),
                keyboardType = KeyboardType.Number,
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                },
                maxLines = 1,
                singleLine = true,
            ),
            ErrorTextArgs(
                text = uiState.diastolicBPErrorMessage?.getString(LocalContext.current) ?: "",
            ),
        )

        TextField(
            TextFieldArgs(
                value = bloodPressureDetails.heartRate,
                onValueChange = { updateHeartRate(it) },
                label = stringResource(id = R.string.heart_rate),
                keyboardType = KeyboardType.Number,
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                },
                maxLines = 1,
                singleLine = true,
            ),
            ErrorTextArgs(
                text = uiState.heartRateErrorMessage?.getString(LocalContext.current) ?: "",
            ),
        )

        TextField(
            TextFieldArgs(
                value = bloodPressureDetails.note,
                onValueChange = { updateNote(it) },
                label = stringResource(id = R.string.note),
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                },
                maxLines = 3,
                singleLine = false,
            ),
            ErrorTextArgs(
                text = uiState.noteErrorMessage?.getString(LocalContext.current) ?: "",
            ),
        )

        DatePickerComponent(bloodPressureDetails.date, onChangeValue = updateDate)

        Button(
            onClick = onSave,
            modifier = Modifier.padding(16.dp),
            enabled = uiState.enableSave,
        ) {
            Text(stringResource(id = R.string.save_button))
        }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}
