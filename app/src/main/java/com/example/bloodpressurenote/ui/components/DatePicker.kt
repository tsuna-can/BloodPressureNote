package com.example.bloodpressurenote.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bloodpressurenote.R
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    selectedDateMillis: Long,
    modifier: Modifier = Modifier,
    onChangeValue: (Long?) -> Unit = {},
) {
    var showPicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDateMillis ?: Instant.now().toEpochMilli(),
    )
    if (showPicker) {
        Material3DatePicker(
            datePickerState = datePickerState,
            closePicker = { showPicker = false },
            onChangeValue = onChangeValue,
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                shape = RoundedCornerShape(5.dp),
            )
            .padding(16.dp)
            .fillMaxWidth()
            .clickable { showPicker = true },
    ) {
        Icon(
            imageVector = Icons.Default.CalendarMonth,
            contentDescription = stringResource(id = R.string.calendar),
        )

        Text(
            text = dateFormatter(datePickerState.selectedDateMillis),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            modifier = Modifier.padding(start = 8.dp),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalMaterial3Api
@Composable
fun Material3DatePicker(
    datePickerState: DatePickerState,
    closePicker: () -> Unit,
    modifier: Modifier = Modifier,
    onChangeValue: (Long?) -> Unit = {},
) {
    DatePickerDialog(
        onDismissRequest = {
            closePicker()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onChangeValue(datePickerState.selectedDateMillis)
                    datePickerState.setSelection(datePickerState.selectedDateMillis)
                    closePicker()
                },
            ) {
                Text(stringResource(id = R.string.ok))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    closePicker()
                },
            ) {
                Text(stringResource(id = R.string.cancel))
            }
        },
        modifier = modifier,
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false,
        )
    }
}

fun dateFormatter(milliseconds: Long?): String {
    milliseconds.let {
        val instant = Instant.ofEpochMilli(it!!)
        val localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        return localDateTime.format(formatter)
    }
}

@Suppress("UnusedPrivateMember")
@Composable
@Preview
private fun DatePickerComponentPreview() {
    DatePickerComponent(selectedDateMillis = Instant.now().toEpochMilli())
}
