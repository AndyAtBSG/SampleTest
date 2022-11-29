package com.appt.berealtest

import FileDirectory
import ImageFile
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.appt.berealtest.services.BeRealImageService
import com.appt.berealtest.services.NetworkBeRealImageService
import com.appt.berealtest.services.SignInResponse
import kotlinx.coroutines.launch

class FileExplorerViewModel(
    private val imageService: BeRealImageService
) : ViewModel() {
    // Changes to these values will not trigger state updates
    private var username = ""
    private var password = ""

    var signOff = mutableStateOf(false)
        private set

    private val _signedIn = mutableStateOf(false)
    val signedIn: Boolean
        get() = _signedIn.value

    private val _loading = mutableStateOf(false)
    val loading: Boolean
        get() = _loading.value

    private val _error = mutableStateOf(false)
    val error: Boolean
        get() = _error.value

    private val _rootName = mutableStateOf<String?>(null)
    val rootName: String?
        get() = _rootName.value

    private val _directories = mutableListOf<FileDirectory>()
    val directories: List<FileDirectory>
        get() = _directories

    private val _images = mutableListOf<ImageFile>()
    val images: List<ImageFile>
        get() = _images

    private val _selectedImage = mutableStateOf<ImageFile?>(null)
    val selectedImage: ImageFile?
        get() = _selectedImage.value


    fun signIn(username: String, password: String) {
        signOff.value = true

        _error.value = false
        _loading.value = true


        viewModelScope.launch {
            makeSignInRequest(username, password)
        }

    }

    fun openDirectory(directoryId: String) {

    }

    private suspend fun makeSignInRequest(username: String, password: String) {
        this.username = username
        this.password = password

        when (val response = imageService.signIn(username, password)) {
            SignInResponse.Fail -> handleSignInFail()
            is SignInResponse.Success -> handleSignInSuccess(response)
        }
        _loading.value = false
    }


    private fun handleSignInFail() {
        username = ""
        password = ""
        _signedIn.value = false
        _error.value = true
        _rootName.value = null
        _directories.clear()
        _images.clear()
    }

    private fun handleSignInSuccess(response: SignInResponse.Success) {
        _signedIn.value = true
        _rootName.value = response.rootItem.name

        _directories.add(
            FileDirectory(
                id = response.rootItem.id, name = response.rootItem.name
            )
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
