package com.appt.berealtest

import FileDirectory
import ImageFile
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.appt.berealtest.services.BeRealImageService
import com.appt.berealtest.services.GetDirectoryResponse
import com.appt.berealtest.services.NetworkBeRealImageService
import com.appt.berealtest.services.SignInResponse
import kotlinx.coroutines.launch

data class FileExplorerUiState(
    val signedIn: Boolean = false,
    val loading: Boolean = false,
    val error: Boolean = false,
    val rootName: String? = null,
    val directories: List<FileDirectory> = emptyList(),
    val images: List<ImageFile> = emptyList(),
    val selectedImage: ImageFile? = null
)

class FileExplorerViewModel(
    private val imageService: BeRealImageService
) : ViewModel() {
    // Changes to these values will not trigger state updates
    //PERSIST IN NETWORK
    private var username = ""
    private var password = ""

    val uiState = mutableStateOf(FileExplorerUiState())

    fun signIn(username: String, password: String) {
        uiState.value = uiState.value.copy(
            error = false,
            loading = true
        )

        viewModelScope.launch {
            makeSignInRequest(username, password)
        }
    }

    fun openDirectory(directoryId: String) {
        viewModelScope.launch {
            when (val response = imageService.getDirectory(username, password, directoryId)) {
                GetDirectoryResponse.Fail -> signOut()
                is GetDirectoryResponse.Success -> handleGetDirectorySuccess(response)
            }
        }
    }

    private suspend fun makeSignInRequest(username: String, password: String) {
        this.username = username
        this.password = password

        when (val response = imageService.signIn(username, password)) {
            SignInResponse.Fail -> signOut()
            is SignInResponse.Success -> handleSignInSuccess(response)
        }
    }

    private fun signOut() {
        username = ""
        password = ""

        uiState.value = FileExplorerUiState(
            error = true
        )
    }

    private fun handleSignInSuccess(response: SignInResponse.Success) {
        uiState.value = uiState.value.copy(
            signedIn = true,
            rootName = response.rootItem.name,
            directories = listOf(response.rootItem),
            loading = false
        )
    }

    private fun handleGetDirectorySuccess(response: GetDirectoryResponse.Success) {
        println("ANDROB01 - Updating ${response.directories.joinToString { it.name }} - ${response.images.joinToString { it.name }}")
        uiState.value = uiState.value.copy(
            directories = response.directories,
            images = response.images
        )
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                FileExplorerViewModel(
                    NetworkBeRealImageService()
                )
            }
        }
    }
}
