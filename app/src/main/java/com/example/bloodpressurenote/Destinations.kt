package com.example.bloodpressurenote

import androidx.compose.material.icons.Icons
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

// Screens to be displayed in the top RallyTabRow
val tabRowScreens = listOf(Input, Statistics)
