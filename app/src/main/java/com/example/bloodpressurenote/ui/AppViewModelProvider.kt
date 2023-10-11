package com.example.bloodpressurenote.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bloodpressurenote.BloodPressureNoteApplication
import com.example.bloodpressurenote.ui.components.screens.InputScreen.InputScreenViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
        initializer {
            InputScreenViewModel(
                bloodPressureNoteApplication().container.bloodPressureRecordsRepository
            )
        }

    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.bloodPressureNoteApplication(): BloodPressureNoteApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BloodPressureNoteApplication)
