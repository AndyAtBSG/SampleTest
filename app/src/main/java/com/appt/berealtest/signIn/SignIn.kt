package com.appt.berealtest.signIn

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    var username by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
    ) {

        Text(
            text = stringResource(R.string.signInTitle),
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(0.dp, 24.dp),
        )

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(stringResource(R.string.username)) },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.password)) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        if (showError) {
            Text(
                text = stringResource(R.string.error),
                color = Color.Red
            )
        }

        Button(
            onClick = { signIn(username.text, password.text) },
            enabled = !isLoading,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(0.dp, 24.dp)
        ) {
            Text(text = stringResource(R.string.signIn))
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .testTag("LoadingIndicator")
                        .padding(16.dp, 0.dp, 0.dp, 0.dp)
                )
            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignInPreview() {
    BeRealTestTheme {
        SignInContent(
            isLoading = true,
            showError = true,
            signIn = { _, _ -> }
        )
    }
}
