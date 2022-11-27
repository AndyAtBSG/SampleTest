package com.appt.berealtest

import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.appt.berealtest.ui.theme.BeRealTestTheme


@Composable
fun SignIn(error: Boolean, signIn: (userName: String, password: String) -> Unit) {
    var username by remember { mutableStateOf("") }

    Text(text = "Sign In")
    TextField(
        value = username,
        onValueChange = { username = it },
        label = { Text("Username") }
    )
}

@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    BeRealTestTheme {
        SignIn(false) { _, _ -> }
    }
}
