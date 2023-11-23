package com.example.bloodpressurenote.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.ui.graphics.vector.ImageVector

interface Destination {
    val icon: ImageVector
    val route: String
}

object Input : Destination {
    override val icon = Icons.Filled.Edit
    override val route = "input"
}

object Statistics : Destination {
    override val icon = Icons.Filled.Equalizer
    override val route = "statistics"
}

object Calendar : Destination {
    override val icon = Icons.Filled.CalendarMonth
    override val route = "calendar"
}

// Screens to be displayed in the top RallyTabRow
val tabRowScreens = listOf(Input, Calendar, Statistics)
