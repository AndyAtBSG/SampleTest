package com.appt.berealtest.fileExplorer

import FileDirectory
import ImageFile
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.appt.berealtest.BeRealApplication
import com.appt.berealtest.services.BeRealImageService
import com.appt.berealtest.services.GetDirectoryResponse

data class FileExplorerUiState(
    val isLoading: Boolean = false,
    val subDirectories: List<FileDirectory> = emptyList(),
    val images: List<ImageFile> = emptyList(),
)

class FileExplorerViewModel(
    private val imageService: BeRealImageService,
) : ViewModel() {
    var uiState by mutableStateOf(FileExplorerUiState())

    suspend fun openDirectory(directoryId: String, signOut: () -> Unit) {
        when (val response = imageService.getDirectory(directoryId)) {
            GetDirectoryResponse.Fail -> signOut()
            is GetDirectoryResponse.Success -> handleGetDirectorySuccess(response)
        }
    }

    private fun handleGetDirectorySuccess(response: GetDirectoryResponse.Success) {
        uiState = uiState.copy(
            subDirectories = response.directories,
            images = response.images
        )
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val imageService = (this[APPLICATION_KEY] as BeRealApplication).imageService
                FileExplorerViewModel(imageService)
            }
        }
    }
}
