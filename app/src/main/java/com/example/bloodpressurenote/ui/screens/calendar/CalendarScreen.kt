package com.example.bloodpressurenote.ui.screens.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bloodpressurenote.ui.components.calendar.Calendar
import kotlinx.collections.immutable.toImmutableList

@Composable
fun CalendarScreen(
    viewModel: CalendarScreenViewModel = hiltViewModel(),
) {
    val homeUiState by viewModel.homeUiState.collectAsState()

    Calendar(homeUiState.recordList.toImmutableList())
}