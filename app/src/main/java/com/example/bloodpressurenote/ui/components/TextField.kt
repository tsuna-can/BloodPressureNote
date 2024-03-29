package com.example.bloodpressurenote.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

data class TextFieldArgs(
    val value: String,
    val onValueChange: (String) -> Unit,
    val modifier: Modifier = Modifier,
    val label: String = "",
    val keyboardType: KeyboardType = KeyboardType.Text,
    val maxLines: Int = 1,
    val singleLine: Boolean = true,
    val keyboardActions: KeyboardActions = KeyboardActions.Default,
)

data class ErrorTextArgs(
    val text: String,
    val modifier: Modifier = Modifier,
)

@Composable
fun TextField(
    textFieldArgs: TextFieldArgs,
    errorTextArgs: ErrorTextArgs,
    modifier: Modifier = Modifier,
) {
    val isError = errorTextArgs.text.isNotEmpty()

    Column(
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = textFieldArgs.value,
            onValueChange = textFieldArgs.onValueChange,
            label = { Text(text = textFieldArgs.label) },
            modifier = textFieldArgs.modifier.fillMaxWidth(),
            isError = isError,
            keyboardOptions = KeyboardOptions(
                keyboardType = textFieldArgs.keyboardType,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = textFieldArgs.keyboardActions,
            maxLines = textFieldArgs.maxLines,
            singleLine = textFieldArgs.singleLine,
        )
        if (isError) {
            Text(errorTextArgs.text, modifier = errorTextArgs.modifier.padding(start = 16.dp))
        }
    }
}

// @Composable
// fun TextField(
//    label: String,
//    value: String,
//    onValueChange: (String) -> Unit,
//    focusManager: FocusManager,
//    modifier: Modifier = Modifier,
//    errorMessage: String = "",
//    keyboardType: KeyboardType = KeyboardType.Text,
//    maxLines: Int = 1,
//    singleLine: Boolean = true,
//    keyboardActions: KeyboardActions = KeyboardActions(
//        onNext = {
//            focusManager.moveFocus(FocusDirection.Down)
//        },
//    ),
// ) {
//    val isError = errorMessage.isNotEmpty()
//
//    Column(
//        modifier = modifier,
//    ) {
//        OutlinedTextField(
//            label = { Text(label) },
//            value = value,
//            onValueChange = onValueChange,
//            modifier = Modifier
//                .fillMaxWidth(),
//            isError = isError,
//            keyboardOptions = KeyboardOptions(
//                keyboardType = keyboardType,
//                imeAction = ImeAction.Next,
//            ),
//            keyboardActions = keyboardActions,
//            maxLines = maxLines,
//            singleLine = singleLine,
//        )
//        if (isError) {
//            Text(errorMessage, modifier = Modifier.padding(start = 16.dp))
//        }
//    }
// }
