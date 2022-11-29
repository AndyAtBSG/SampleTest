package com.appt.berealtest

import FileDirectory
import FileExplorerViewModel
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.appt.berealtest.services.BeRealImageService
import com.appt.berealtest.services.SignInResponse
import kotlinx.coroutines.CompletableDeferred
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MockBeRealImageService : BeRealImageService {
    lateinit var signInResult: CompletableDeferred<SignInResponse>
    var receivedUsername = ""
    var receivedPassword = ""

    override suspend fun signIn(username: String, password: String): SignInResponse {
        receivedUsername = username
        receivedPassword = password
        signInResult = CompletableDeferred()
        return signInResult.await()
    }

    fun thenSignInIsCalled(expectedUsername: String, expectedPassword: String) {
        assertEquals(expectedUsername, receivedUsername)
        assertEquals(expectedPassword, receivedPassword)
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
    val composeTestRule = createComposeRule()

    private val imageService = MockBeRealImageService()
    private val viewModel = FileExplorerViewModel(imageService)

    @Test
    fun shouldDisplayExplorerWhenSignInSucceeds() {
        composeTestRule.setContent {
            MainActivityContent(viewModel)
        }

        composeTestRule.onNodeWithText("Username").performTextInput("SomeUser")
        composeTestRule.onNodeWithText("Password").performTextInput("SomePassword")
        composeTestRule.onNodeWithText("Sign In").performClick()
        imageService.thenSignInIsCalled("SomeUser", "SomePassword")

        imageService.whenSignInSucceeds(FileDirectory("1", "Folder"))

        composeTestRule.onNodeWithText("Explorer").assertIsDisplayed()
    }

    @Test
    fun shouldDisplayErrorWhenSignInFails() {
        composeTestRule.setContent {
            MainActivityContent(viewModel)
        }

        composeTestRule.onNodeWithText("Sign In").performClick()

        imageService.whenSignInSucceeds(FileDirectory("1", "Folder"))

        composeTestRule.onNodeWithText("Explorer").assertIsDisplayed()
    }


}
