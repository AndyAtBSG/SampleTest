package com.appt.berealtest

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.appt.berealtest.ui.theme.BeRealTestTheme


@Composable
fun SignIn(error: Boolean, signIn: (userName: String, password: String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column {


        Text(text = stringResource(R.string.signInTitle))
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(stringResource(R.string.username)) }
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.password)) }
        )
        Button(onClick = { signIn(username, password) }) {
            Text(text = stringResource(R.string.signIn))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    BeRealTestTheme {
        SignIn(false) { _, _ -> }
    }
}
