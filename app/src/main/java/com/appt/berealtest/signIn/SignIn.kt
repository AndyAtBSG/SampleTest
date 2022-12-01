package com.appt.berealtest.signIn

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appt.berealtest.R
import com.appt.berealtest.ui.theme.BeRealTestTheme

@Composable
fun SignIn(
    openRootDirectory: (directoryId: String) -> Unit,
    viewModel: SignInViewModel = viewModel(factory = SignInViewModel.Factory)
) {
    val uiState = viewModel.uiState.value

    SignInContent(
        isLoading = uiState.isLoading,
        showError = uiState.showError
    ) { username, password ->
        viewModel.signIn(username, password, openRootDirectory)
    }
}

@Composable
fun SignInContent(
    isLoading: Boolean,
    showError: Boolean,
    signIn: (username: String, password: String) -> Unit
) {
    val username = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }

    Column {

        Text(text = stringResource(R.string.signInTitle))

        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text(stringResource(R.string.username)) }
        )

        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text(stringResource(R.string.password)) }
        )

        Button(onClick = { signIn(username.value.text, password.value.text) }) {
            Text(text = stringResource(R.string.signIn))
        }

        if (showError) Text(text = stringResource(R.string.error))
    }
}

@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    BeRealTestTheme {
        SignInContent(
            isLoading = false,
            showError = false,
            signIn = { _, _ -> }
        )
    }
}
