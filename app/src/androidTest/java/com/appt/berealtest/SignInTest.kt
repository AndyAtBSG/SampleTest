package com.appt.berealtest

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.appt.berealtest.signIn.SignInContent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

class SignInTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private var receivedUsername: String? = null
    private var receivedPassword: String? = null

    @Test
    fun shouldNotifyWhenUserSignsIn() {
        givenASignInComponent()

        composeTestRule.onNode(hasText("Something went wrong")).assertDoesNotExist()
        composeTestRule.onNode(hasTestTag("LoadingIndicator")).assertDoesNotExist()

        composeTestRule.onNode(hasText("Username")).performTextInput("SomeUser")
        composeTestRule.onNode(hasText("Password")).performTextInput("SomePassword")
        composeTestRule.onNode(hasText("Sign In")).performClick()

        composeTestRule.onNode(hasText("Something went wrong")).assertDoesNotExist()
        assertEquals(receivedUsername, "SomeUser")
        assertEquals(receivedPassword, "SomePassword")
    }

    @Test
    fun shouldDisplayErrorWhenShowErrorIsTrue() {
        givenASignInComponent(showError = true)

        composeTestRule.onNode(hasText("Something went wrong")).assertIsDisplayed()
    }

    @Test
    fun shouldDisplayLoadingAndDisableSignInWhenIsLoadingIsTrue() {
        givenASignInComponent(isLoading = true)

        composeTestRule.onNode(hasTestTag("LoadingIndicator")).assertIsDisplayed()
        composeTestRule.onNode(hasText("Username")).performTextInput("SomeUser")
        composeTestRule.onNode(hasText("Password")).performTextInput("SomePassword")
        composeTestRule.onNode(hasText("Sign In")).performClick()

        assertNull(receivedUsername)
        assertNull(receivedPassword)
    }

    private fun givenASignInComponent(
        isLoading: Boolean = false,
        showError: Boolean = false
    ) {
        composeTestRule.setContent {
            SignInContent(
                isLoading = isLoading,
                showError = showError,
                signIn = { username, password ->
                    receivedUsername = username
                    receivedPassword = password
                })
        }
    }
}
