package com.appt.berealtest.signIn

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
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
    val uiState = viewModel.uiState

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
                .align(CenterHorizontally)
                .padding(0.dp, 24.dp),
        )

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(stringResource(R.string.username)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth(),
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.password)) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            keyboardActions = KeyboardActions(
                onDone = { signIn(username.text, password.text) }
            )
        )

        AnimatedVisibility(
            showError,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Text(
                text = stringResource(R.string.error),
                color = Color.Red
            )
        }

        Button(
            onClick = { signIn(username.text, password.text) },
            enabled = !isLoading,
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(0.dp, 24.dp)
        ) {
            Text(
                text = stringResource(R.string.signIn),
                style = MaterialTheme.typography.h5
            )
            AnimatedVisibility(isLoading) {
                CircularProgressIndicator(
                    color = Color.Green,
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
