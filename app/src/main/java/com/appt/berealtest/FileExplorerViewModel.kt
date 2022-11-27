import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.appt.berealtest.services.BeRealImageService
import com.appt.berealtest.services.SignInResponse

class FileExplorerViewModel(
    private val imageService: BeRealImageService
) : ViewModel() {
    val signedIn = mutableStateOf(false)
    val loading = mutableStateOf(false)
    val error = mutableStateOf(false)
    val rootName = mutableStateOf<String?>(null)
    val directories = mutableListOf<FileDirectory>()
    val images = mutableListOf<ImageFile>()


    suspend fun signIn(userName: String, password: String) {
        error.value = false
        loading.value = true

        when (val response = imageService.signIn(userName, password)) {
            SignInResponse.Fail -> handleSignInFail()
            is SignInResponse.Success -> handleSignInSuccess(response)
        }
        loading.value = false
    }

    private fun handleSignInFail() {
        signedIn.value = false
        error.value = true
        rootName.value = null
        directories.clear()
        images.clear()
    }

    private fun handleSignInSuccess(response: SignInResponse.Success) {
        signedIn.value = true
        rootName.value = response.rootItem.name

        directories.add(
            FileDirectory(
                id = response.rootItem.id,
                name = response.rootItem.name
            )
        )
    }
}
