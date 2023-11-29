package com.example.bloodpressurenote.ui.components.calendar

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bloodpressurenote.R
import com.example.bloodpressurenote.data.BloodPressureRecord
import com.example.bloodpressurenote.ui.theme.Gray80
import com.example.bloodpressurenote.util.getDayOfWeekTextColor
import com.example.bloodpressurenote.util.rememberFirstMostVisibleMonth
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.ZoneOffset
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

@Composable
fun Calendar(
    recordList: ImmutableList<BloodPressureRecord>,
    modifier: Modifier = Modifier,
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) }
    val endMonth = remember { currentMonth.plusMonths(100) }
    val daysOfWeek = remember { daysOfWeek() }
    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first(),
        outDateStyle = OutDateStyle.EndOfGrid,
    )
    val coroutineScope = rememberCoroutineScope()
    val visibleMonth = rememberFirstMostVisibleMonth(state)
    var selection by remember { mutableStateOf<CalendarDay?>(null) }
    val recordsInSelectedDate = remember {
        Log.d("Calendar", "recordsInSelectedDate")
        derivedStateOf {
            val date = selection?.date
            if (date != null) {
                recordList.filter {
                    it.createdAt.toInstant().atZone(ZoneOffset.UTC).toLocalDate() == date
                }
            } else {
                emptyList()
            }
        }
    }

    Column(modifier = modifier) {
        CalendarTitle(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 12.dp),
            currentMonth = visibleMonth.yearMonth,
            goToPrevious = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                }
            },
            goToNext = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                }
            },
        )

        HorizontalCalendar(
            state = state,
            dayContent = { day ->
                val record = recordList.find {
                    it.createdAt.toInstant().atZone(ZoneOffset.UTC).toLocalDate() == day.date
                }
                Day(
                    day = day,
                    isSelected = selection == day,
                    bloodPressureRecord = record,
                ) { clicked ->
                    selection = clicked
                }
            },
            monthHeader = { DaysOfWeekTitle(daysOfWeek = daysOfWeek.toImmutableList()) },
        )

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(items = recordsInSelectedDate.value) { record ->
                SelectedDayRecord(bloodPressureRecord = record)
            }
        }
    }
}

@Composable
fun DaysOfWeekTitle(
    daysOfWeek: ImmutableList<DayOfWeek>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        for ((index, dayOfWeek) in daysOfWeek.withIndex()) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                color = getDayOfWeekTextColor(index + 1),
            )
        }
    }
}

@Composable
fun Day(
    day: CalendarDay,
    bloodPressureRecord: BloodPressureRecord?,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: (CalendarDay) -> Unit = {},
) {
    Column(
        modifier = modifier
            .aspectRatio(0.7f)
            .border(
                width = if (isSelected) 1.dp else 0.dp,
                color = if (isSelected) Gray80 else Color.Transparent,
            )
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) },
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = if (day.position == DayPosition.MonthDate) Color.Black else Color.Gray,
        )
        if (bloodPressureRecord != null) {
            Text(text = bloodPressureRecord.systolicBloodPressure.toString())
            Text(text = bloodPressureRecord.diastolicBloodPressure.toString())
        }
    }
}

@Composable
fun SelectedDayRecord(
    bloodPressureRecord: BloodPressureRecord,
    modifier: Modifier = Modifier,
) {
    val df = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Text(text = df.format(bloodPressureRecord.createdAt))
        Row {
            Text(
                text = stringResource(id = R.string.systolic_blood_pressure),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(.7f),
            )
            Text(
                text = bloodPressureRecord.systolicBloodPressure.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(.3f),
            )
        }
        Row {
            Text(
                text = stringResource(id = R.string.diastolic_blood_pressure),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(.7f),
            )
            Text(
                text = bloodPressureRecord.diastolicBloodPressure.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(.3f),
            )
        }
        Row {
            Text(
                text = stringResource(id = R.string.heart_rate),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(.7f),
            )
            Text(
                text = bloodPressureRecord.heartRate.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(.3f),
            )
        }
    }
}

@Suppress("UnusedPrivateMember")
@Preview
@Composable
private fun PreviewCalendar() {
    val today = Date()

    Calendar(
        recordList = listOf(
            BloodPressureRecord(
                systolicBloodPressure = 120,
                diastolicBloodPressure = 80,
                createdAt = today,
                heartRate = 60,
                note = "memo",
            ),
            BloodPressureRecord(
                systolicBloodPressure = 110,
                diastolicBloodPressure = 60,
                createdAt = Date(today.time + (1000 * 60 * 60 * 24)),
                heartRate = 60,
                note = "memo",
            ),
        ).toImmutableList(),
    )
}

@Suppress("UnusedPrivateMember")
@Preview
@Composable
private fun PreviewSelectedDayRecord() {
    val today = Date()

    SelectedDayRecord(
        bloodPressureRecord = BloodPressureRecord(
            systolicBloodPressure = 120,
            diastolicBloodPressure = 80,
            createdAt = today,
            heartRate = 60,
            note = "memo",
        ),
    )
}
