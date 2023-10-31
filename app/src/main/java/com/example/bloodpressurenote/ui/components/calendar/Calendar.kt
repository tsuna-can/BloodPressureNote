package com.example.bloodpressurenote.ui.components.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bloodpressurenote.data.BloodPressureRecord
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
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.ZoneOffset
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

@Composable
fun Calendar(
    recordList: List<BloodPressureRecord>
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
        outDateStyle = OutDateStyle.EndOfGrid
    )
    val coroutineScope = rememberCoroutineScope()
    val visibleMonth = rememberFirstMostVisibleMonth(state)

    Column() {
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
                Day(day, record)
            },
            monthHeader = { DaysOfWeekTitle(daysOfWeek = daysOfWeek) }
        )
    }

}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        for ((index, dayOfWeek) in daysOfWeek.withIndex()) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                color = getDayOfWeekTextColor(index + 1)
            )
        }
    }
}

@Composable
fun Day(
    day: CalendarDay,
    bloodPressureRecord: BloodPressureRecord?
) {
    Column(
        modifier = Modifier
            .aspectRatio(0.7f),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = if (day.position == DayPosition.MonthDate) Color.Black else Color.Gray
        )
        if (bloodPressureRecord != null) {
            Text(text = bloodPressureRecord.systolicBloodPressure.toString())
            Text(text = bloodPressureRecord.diastolicBloodPressure.toString())
        }
    }
}

@Preview
@Composable
fun PreviewCalendarScreen() {
    val today = Date()

    Calendar(
        recordList = listOf(
            BloodPressureRecord(
                systolicBloodPressure = 120,
                diastolicBloodPressure = 80,
                createdAt = today,
                heartRate = 60,
                note = "memo"
            ),
            BloodPressureRecord(
                systolicBloodPressure = 110,
                diastolicBloodPressure = 60,
                createdAt =  Date(today.getTime() + (1000 * 60 * 60 * 24)),
                heartRate = 60,
                note = "memo"
            )
        )
    )
}