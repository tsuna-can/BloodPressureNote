package com.example.bloodpressurenote.ui.screens.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bloodpressurenote.R

@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier,
    viewModel: StatisticsScreenViewModel = hiltViewModel(),
) {
    val statisticsUiState by viewModel.statisticsUiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Overview Screen" },
    ) {
        Text(text = stringResource(id = R.string.average))
        Row(
            modifier = Modifier
                .padding(16.dp),
        ) {
            Text(text = stringResource(id = R.string.systolic_blood_pressure))
            Text(text = statisticsUiState.systolicBloodPressure.toString())
        }
        Row(
            modifier = Modifier
                .padding(16.dp),
        ) {
            Text(text = stringResource(id = R.string.diastolic_blood_pressure))
            Text(text = statisticsUiState.diastolicBloodPressure.toString())
        }
        Row(
            modifier = Modifier
                .padding(16.dp),
        ) {
            Text(text = stringResource(id = R.string.heart_rate))
            Text(text = statisticsUiState.heartRate.toString())
        }
    }
}
