package com.appt.berealtest.signIn

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.appt.berealtest.BeRealApplication
import com.appt.berealtest.services.BeRealImageService
import com.appt.berealtest.services.SignInResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class SignInUiState(
    val isLoading: Boolean = false,
    val showError: Boolean = false
)

class SignInViewModel(private val imageService: BeRealImageService) : ViewModel() {
    val uiState = mutableStateOf(SignInUiState())

    fun signIn(
        username: String,
        password: String,
        onSignInSuccess: (rootDirectoryId: String) -> Unit
    ) {
        if (username.isEmpty() || password.isEmpty()) {
            uiState.value = uiState.value.copy(
                showError = true
            )
            viewModelScope.launch {
                delay(3000)
                uiState.value = uiState.value.copy(
                    showError = false
                )
            }
        } else {
            makeSignInRequest(username, password, onSignInSuccess)
        }
    }

    private fun makeSignInRequest(
        username: String,
        password: String,
        onSignInSuccess: (rootDirectoryId: String) -> Unit
    ) {
        uiState.value = uiState.value.copy(
            showError = false,
            isLoading = true
        )

        viewModelScope.launch {
            when (val response = imageService.signIn(username, password)) {
                SignInResponse.Fail -> handleError()
                is SignInResponse.Success -> handleSuccess(onSignInSuccess, response)
            }
        }
    }

    private fun handleSuccess(
        onSignInSuccess: (rootDirectoryId: String) -> Unit,
        response: SignInResponse.Success
    ) {
        uiState.value = uiState.value.copy(
            isLoading = false
        )
        onSignInSuccess(response.rootItem.id)
    }

    private fun handleError() {
        uiState.value = uiState.value.copy(
            isLoading = false,
            showError = true
        )
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val imageService = (this[APPLICATION_KEY] as BeRealApplication).imageService
                SignInViewModel(imageService)
            }
        }
    }
}
