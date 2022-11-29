package com.appt.berealtest

import FileDirectory
import ImageFile
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.appt.berealtest.ui.theme.BeRealTestTheme
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


    @Test
    fun shouldDisplayDirectories() {
        composeTestRule.setContent {
            BeRealTestTheme {
                FileExplorer(mockDirectories, emptyList())
            }
        }

        mockDirectories.forEach {
            composeTestRule.onNodeWithText(it.name).assertExists()
        }
    }

    @Test
    fun shouldDisplayImages() {
        composeTestRule.setContent {
            BeRealTestTheme {
                FileExplorer(emptyList(), mockImages)
            }
        }

        mockImages.forEach {
            composeTestRule.onNodeWithText(it.name).assertExists()
        }
    }
}
