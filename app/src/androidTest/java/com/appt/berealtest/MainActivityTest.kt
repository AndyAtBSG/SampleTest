package com.appt.berealtest

import FileDirectory
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

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

        imageService.whenSignInFails()

        composeTestRule.onNodeWithText("Explorer").assertDoesNotExist()
        composeTestRule.onNodeWithText("Something went wrong").assertExists()
    }
}
