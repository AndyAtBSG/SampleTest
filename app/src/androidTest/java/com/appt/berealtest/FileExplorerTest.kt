package com.appt.berealtest

import FileDirectory
import ImageFile
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.appt.berealtest.ui.theme.BeRealTestTheme
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
    
    @Test
    fun shouldDisplayDirectories() {
        givenAFileExplorer(mockDirectories, emptyList())

        mockDirectories.forEach {
            composeTestRule.onNodeWithText(it.name).assertExists()
        }
    }

    @Test
    fun shouldDisplayImages() {
        givenAFileExplorer(emptyList(), mockImages)

        mockImages.forEach {
            composeTestRule.onNodeWithText(it.name).assertExists()
        }
    }

    @Test
    fun shouldNotifyWhenDirectoryClicked() {
        givenAFileExplorer(mockDirectories, emptyList())
        composeTestRule.onNodeWithText(mockDirectories[0].name).performClick()
        assertEquals(mockDirectories[0].id, receivedDirectoryId)
    }

    private fun givenAFileExplorer(directories: List<FileDirectory>, images: List<ImageFile>) {
        composeTestRule.setContent {
            BeRealTestTheme {
                FileExplorer(directories, images) {
                    receivedDirectoryId = it
                }
            }
        }
    }
}
