package com.appt.berealtest

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.appt.berealtest.ui.theme.BeRealTestTheme


@Composable
fun SignIn(error: Boolean, signIn: (userName: String, password: String) -> Unit) {
    Text(text = "Sign In")
}

@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    BeRealTestTheme {
        SignIn(false) { _, _ -> }
    }
}
