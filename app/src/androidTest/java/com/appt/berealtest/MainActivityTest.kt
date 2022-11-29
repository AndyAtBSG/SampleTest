package com.appt.berealtest

import FileDirectory
import FileExplorerViewModel
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.appt.berealtest.services.BeRealImageService
import com.appt.berealtest.services.SignInResponse
import kotlinx.coroutines.CompletableDeferred
import org.junit.Rule
import org.junit.Test

class MockBeRealImageService : BeRealImageService {
    lateinit var signInResult: CompletableDeferred<SignInResponse>

    override suspend fun signIn(username: String, password: String): SignInResponse {
        signInResult = CompletableDeferred()
        return signInResult.await()
    }

    fun whenSignInSucceeds(rootItem: FileDirectory) {
        signInResult.complete(
            SignInResponse.Success(rootItem)
        )
    }

    fun whenSignInFails() {
        signInResult.completeExceptionally(RuntimeException())
    }
}

class MainActivityTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val imageService = MockBeRealImageService()
    private val viewModel = FileExplorerViewModel(imageService)

    @Test
    fun shouldDisplayExplorer() {
        composeTestRule.setContent {
            MainActivityContent(viewModel)
        }

        composeTestRule.onNodeWithText("Sign In").performClick()

        composeTestRule.onNodeWithText("Explorer").assertIsDisplayed()
    }

}
