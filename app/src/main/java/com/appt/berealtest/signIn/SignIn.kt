package com.appt.berealtest.signIn

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
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
    val username = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }

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
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text(stringResource(R.string.username)) },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text(stringResource(R.string.password)) },
            modifier = Modifier.fillMaxWidth()
        )

        if (showError) {
            Text(
                text = stringResource(R.string.error),
                color = Color.Red
            )
        }

        Button(
            onClick = { signIn(username.value.text, password.value.text) },
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

@Preview(showBackground = true)
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
