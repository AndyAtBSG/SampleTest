package com.appt.berealtest

import com.appt.berealtest.models.FileDirectory
import com.appt.berealtest.models.ImageFile
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.appt.berealtest.fileExplorer.FileExplorerContent
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class FileExplorerTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockDirectories = listOf(
        FileDirectory("directoryId-0", "directoryName-0"),
        FileDirectory("directoryId-1", "directoryName-1"),
    )
    private val mockImages = listOf(
        ImageFile("imageId-0", "imageName-0"),
        ImageFile("imageId-1", "imageName-1")
    )

    private lateinit var receivedDirectoryId: String
    private lateinit var receivedImageId: String

    @Test
    fun shouldDisplayDirectories() {
        givenAFileExplorer()

        mockDirectories.forEach {
            composeTestRule.onNodeWithText(it.name).assertIsDisplayed()
        }
    }

    @Test
    fun shouldDisplayImages() {
        givenAFileExplorer()

        mockImages.forEach {
            composeTestRule.onNodeWithText(it.name).assertIsDisplayed()
        }
    }

    @Test
    fun shouldNotifyWhenDirectoryClicked() {
        givenAFileExplorer()
        composeTestRule.onNodeWithText(mockDirectories[0].name).performClick()
        assertEquals(mockDirectories[0].id, receivedDirectoryId)
    }

    @Test
    fun shouldNotifyWhenImageClicked() {
        givenAFileExplorer()
        composeTestRule.onNodeWithText(mockImages[0].name).performClick()
        assertEquals(mockImages[0].id, receivedImageId)
    }

    @Test
    fun shouldDisplayLoadingWhenIsLoadingTrue() {
        givenAFileExplorer(true)
        composeTestRule.onNodeWithTag(testTag = "LoadingIndicator").assertIsDisplayed()
    }

    @Test
    fun shouldNotDisplayLoadingWhenIsLoadingFalse() {
        givenAFileExplorer(false)
        composeTestRule.onNodeWithTag(testTag = "LoadingIndicator").assertDoesNotExist()
    }

    private fun givenAFileExplorer(isLoading: Boolean = false) {
        composeTestRule.setContent {
            FileExplorerContent(
                isLoading,
                mockDirectories,
                mockImages,
                { receivedDirectoryId = it },
                { receivedImageId = it },
                {}
            )
        }
    }
}
