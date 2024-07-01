package com.android.taskstimer.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TasksTimerApp() {
    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = "Trigger in seconds", onValueChange = {},
//            modifier =,
//            enabled = false,
//            readOnly = false,
//            textStyle =,
//            label = { -> },
//            placeholder = { -> },
//            leadingIcon = { -> },
//            trailingIcon = { -> },
//            prefix = { -> },
//            suffix = { -> },
//            supportingText = { -> },
//            isError = false,
//            visualTransformation =,
//            keyboardOptions =,
//            keyboardActions =,
//            singleLine = false,
//            maxLines = 0,
//            minLines = 0,
//            interactionSource =,
//            shape =,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            ),
        )
        OutlinedTextField(value = "hello", onValueChange = {})
        TextField(modifier = Modifier.border(1.dp, Color.Black), value = "hello", onValueChange = {},
            colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent
            ),
            )
        Row {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Schedule")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Cancel")
            }
        }
    }
}