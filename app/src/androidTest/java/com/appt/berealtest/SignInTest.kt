package com.appt.berealtest

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.appt.berealtest.ui.theme.BeRealTestTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class SignInTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldDisplaySignIn() {
        var receivedUsername = ""
        var receivedPassword = ""
        composeTestRule.setContent {
            BeRealTestTheme {
                SignIn(error = false, signIn = { username, password ->
                    receivedUsername = username
                    receivedPassword = password
                })
            }
        }

        composeTestRule.onNode(hasText("Username")).performTextInput("SomeUser")
        composeTestRule.onNode(hasText("Password")).performTextInput("SomePassword")
        composeTestRule.onNode(hasText("Sign In")).performClick()

        composeTestRule.onNode(hasText("Something went wrong")).assertDoesNotExist()
        assertEquals(receivedUsername, "SomeUser")
        assertEquals(receivedPassword, "SomePassword")
    }

    @Test
    fun shouldDisplayErrorWhenSignInFails() {
        composeTestRule.setContent {
            BeRealTestTheme {
                SignIn(error = true, signIn = { _, _ -> })
            }
        }

        composeTestRule.onNode(hasText("Something went wrong")).assertExists()
    }

}
