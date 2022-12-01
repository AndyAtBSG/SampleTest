@file:OptIn(ExperimentalCoroutinesApi::class)

package com.appt.berealtest.signIn

import FileDirectory
import com.appt.berealtest.MockBeRealImageService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SignInViewModelTest {
    private val service = MockBeRealImageService()
    private val viewModel = SignInViewModel(service)

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun shouldNotDisplayErrorOrLoadingWhenScreenInitialises() {
        val expectedState = SignInUiState(
            isLoading = false,
            showError = false
        )

        assertEquals(expectedState, viewModel.uiState.value)
    }

    @Test
    fun shouldDisplayErrorWhenUserSignsInWithoutUsername() = runTest {
        viewModel.signIn("", "password") {}
        val expectedState = SignInUiState(
            isLoading = false,
            showError = true
        )

        assertEquals(expectedState, viewModel.uiState.value)
    }

    @Test
    fun shouldDisplayErrorWhenUserSignsInWithoutPassword() = runTest {
        viewModel.signIn("username", "") {}
        val expectedState = SignInUiState(
            isLoading = false,
            showError = true
        )

        assertEquals(expectedState, viewModel.uiState.value)
    }

    @Test
    fun shouldDisplayErrorWhenSignInRequestFails() {
        viewModel.signIn("username", "password") {}

        val loadingState = SignInUiState(
            isLoading = true,
            showError = false
        )
        assertEquals(loadingState, viewModel.uiState.value)

        service.whenSignInFails()
        val expectedState = SignInUiState(
            isLoading = false,
            showError = true
        )
        assertEquals(expectedState, viewModel.uiState.value)
    }

    @Test
    fun shouldNotifySignInSuccessWhenSignInRequestSucceeds() {
        var receivedDirectoryId = ""
        viewModel.signIn("username", "password") {
            receivedDirectoryId = it
        }

        service.whenSignInSucceeds(FileDirectory("123", "A Directory"))
        val expectedState = SignInUiState(
            isLoading = false,
            showError = false
        )

        assertEquals(expectedState, viewModel.uiState.value)
        assertEquals(receivedDirectoryId, "123")
    }
}
