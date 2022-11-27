import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.appt.berealtest.services.BeRealImageService
import com.appt.berealtest.services.SignInResponse

class FileExplorerViewModel(
    private val imageService: BeRealImageService
) : ViewModel() {
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


    suspend fun signIn(username: String, password: String) {
        _error.value = false
        _loading.value = true

        when (val response = imageService.signIn(username, password)) {
            SignInResponse.Fail -> handleSignInFail()
            is SignInResponse.Success -> handleSignInSuccess(response)
        }
        _loading.value = false
    }

    private fun handleSignInFail() {
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
                id = response.rootItem.id,
                name = response.rootItem.name
            )
        )
    }
}
