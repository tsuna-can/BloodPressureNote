package com.example.bloodpressurenote.ui.screens.CalendarScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CalendarScreen(
    viewModel: CalendarScreenViewModel = hiltViewModel()
) {

    val homeUiState by viewModel.homeUiState.collectAsState()

    Text(text = homeUiState.recordList.toString())
}