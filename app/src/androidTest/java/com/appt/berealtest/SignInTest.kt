package com.appt.berealtest

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.appt.berealtest.ui.theme.BeRealTestTheme
import org.junit.Rule
import org.junit.Test

class SignInTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldDisplaySignIn() {
        composeTestRule.setContent {
            BeRealTestTheme {
                SignIn(error = false, signIn = { _, _ -> })
            }
        }

        composeTestRule.onNodeWithText("Explorer").assertIsDisplayed()
    }

}
