package com.appt.berealtest

import FileDirectory
import ImageFile
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val imageService = MockBeRealImageService()
    private val viewModel = FileExplorerViewModel(imageService)

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MainActivityContent(viewModel)
        }
    }

    @Test
    fun shouldDisplayExplorerWhenSignInSucceeds() {
        composeTestRule.onNodeWithText("Username").performTextInput("someUser")
        composeTestRule.onNodeWithText("Password").performTextInput("somePassword")
        composeTestRule.onNodeWithText("Sign In").performClick()
        imageService.thenSignInIsCalled("someUser", "somePassword")

        imageService.whenSignInSucceeds(FileDirectory("1", "SomeDirectory"))

        composeTestRule.onNodeWithText("SomeDirectory")
    }

    @Test
    fun shouldDisplayErrorWhenSignInFails() {
        composeTestRule.onNodeWithText("Sign In").performClick()

        imageService.whenSignInFails()

        composeTestRule.onNodeWithText("Something went wrong").assertIsDisplayed()
    }

    @Test
    fun shouldRequestFoldersWhenDirectorySelected() {
        whenUserSignsIn()

        composeTestRule.onNodeWithText("someDirectory").performClick()
        imageService.thenGetDirectoryIsCalled(
            "someUser",
            "somePassword",
            "id-1"
        )

        imageService.whenGetDirectorySucceeds(
            listOf(
                FileDirectory("123", "NewDirectory")
            ),
            listOf(ImageFile("456", "NewImage"))
        )

        composeTestRule.onNodeWithText("NewDirectory").assertIsDisplayed()
        composeTestRule.onNodeWithText("NewImage").assertIsDisplayed()
        composeTestRule.onNodeWithText("someDirectory").assertDoesNotExist()
    }

    @Test
    fun shouldSignOutWhenGetDirectoryFails() {
        whenUserSignsIn()

        composeTestRule.onNodeWithText("someDirectory").performClick()
        imageService.thenGetDirectoryIsCalled(
            "someUser",
            "somePassword",
            "id-1"
        )

        imageService.whenGetDirectoryFails()
        composeTestRule.onNodeWithText("Something went wrong").assertIsDisplayed()
        composeTestRule.onNodeWithText("Username").assertIsDisplayed()
    }

    @Test
    fun shouldDisplayImageWhenImageSelected() {
        whenUserSignsIn()
        composeTestRule.onNodeWithText("someDirectory").performClick()
        imageService.whenGetDirectorySucceeds(
            emptyList(),
            listOf(ImageFile("123", "AnImage"))
        )
        composeTestRule.onNodeWithText("AnImage").performClick()
        composeTestRule.onNodeWithContentDescription("AnImage").assertIsDisplayed()
    }

    private fun whenUserSignsIn() {
        composeTestRule.onNodeWithText("Username").performTextInput("someUser")
        composeTestRule.onNodeWithText("Password").performTextInput("somePassword")
        composeTestRule.onNodeWithText("Sign In").performClick()
        imageService.whenSignInSucceeds(
            FileDirectory("id-1", "someDirectory")
        )
    }
}
