package com.example.bloodpressurenote.ui.screens.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bloodpressurenote.R
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.axis.vertical.VerticalAxis
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.dimensions.MutableDimensions
import com.patrykandpatrick.vico.core.entry.entriesOf
import com.patrykandpatrick.vico.core.entry.entryModelOf

@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier,
    viewModel: StatisticsScreenViewModel = hiltViewModel(),
) {
    val statisticsUiState by viewModel.statisticsUiState.collectAsStateWithLifecycle()
    val systolicBloodPressureArray = statisticsUiState.systolicBloodPressureList.toTypedArray()
    val diastolicBloodPressureArray = statisticsUiState.diastolicBloodPressureList.toTypedArray()
    val chartEntryModel = entryModelOf(
        entriesOf(*systolicBloodPressureArray),
        entriesOf(*diastolicBloodPressureArray),
    )

    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Text(text = stringResource(id = R.string.average))
        TableRow(
            label = stringResource(id = R.string.systolic_blood_pressure),
            value = statisticsUiState.systolicBloodPressure,
        )
        TableRow(
            label = stringResource(id = R.string.diastolic_blood_pressure),
            value = statisticsUiState.diastolicBloodPressure,
        )
        TableRow(
            label = stringResource(id = R.string.heart_rate),
            value = statisticsUiState.heartRate,
        )

        val labelTextComponent = TextComponent.Builder()
        labelTextComponent.color = MaterialTheme.colorScheme.onSurfaceVariant.toArgb()
        labelTextComponent.padding = MutableDimensions(horizontalDp = 4F, verticalDp = 0F)
        labelTextComponent.textSizeSp = 9F

        Chart(
            chart = lineChart(),
            model = chartEntryModel,
            startAxis = rememberStartAxis(
                title = stringResource(id = R.string.blood_pressure),
                titleComponent = TextComponent.Builder().build(),
                horizontalLabelPosition = VerticalAxis.HorizontalLabelPosition.Outside,
                label = labelTextComponent.build(),
            ),
            bottomAxis = rememberBottomAxis(
                title = stringResource(id = R.string.date),
                titleComponent = TextComponent.Builder().build(),
            ),
        )
    }
}

@Composable
fun TableRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(16.dp),
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(0.7f),
        )
        Text(
            text = value,
            modifier = Modifier.weight(0.3f),
        )
    }
}
