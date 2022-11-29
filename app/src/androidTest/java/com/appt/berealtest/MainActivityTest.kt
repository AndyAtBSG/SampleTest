package com.appt.berealtest

import FileDirectory
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

        composeTestRule.onNodeWithText("Username").performTextInput("someUser")
        composeTestRule.onNodeWithText("Password").performTextInput("somePassword")
        composeTestRule.onNodeWithText("Sign In").performClick()
        imageService.thenSignInIsCalled("someUser", "somePassword")

        imageService.whenSignInSucceeds(FileDirectory("1", "SomeDirectory"))

        composeTestRule.onNodeWithText("SomeDirectory")
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

    @Test
    fun shouldRequestFoldersWhenDirectorySelected() {
        composeTestRule.setContent {
            MainActivityContent(viewModel)
        }

        composeTestRule.onNodeWithText("Username").performTextInput("someUser")
        composeTestRule.onNodeWithText("Password").performTextInput("somePassword")
        composeTestRule.onNodeWithText("Sign In").performClick()
        imageService.whenSignInSucceeds(
            FileDirectory("id-1", "someDirectory")
        )

        composeTestRule.onNodeWithText("someDirectory").performClick()
        imageService.thenGetDirectoryIsCalled(
            "someUser",
            "somePassword",
            "id-1"
        )
    }

    @Test
    fun shouldDisplayImageWhenImageSelected() {
        composeTestRule.setContent {
            MainActivityContent(viewModel)
        }

        imageService.whenSignInSucceeds(FileDirectory("1", "Folder"))

        composeTestRule.onNodeWithText("Explorer").assertDoesNotExist()
        composeTestRule.onNodeWithText("Something went wrong").assertExists()

    }
}
