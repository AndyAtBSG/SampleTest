@file:OptIn(ExperimentalCoroutinesApi::class)

package com.appt.berealtest.signIn

import com.appt.berealtest.MockBeRealImageService
import com.appt.berealtest.models.FileDirectory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
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

        assertEquals(expectedState, viewModel.uiState)
    }

    @Test
    fun shouldDisplayErrorWhenUserSignsInWithoutUsername() = runTest {
        viewModel.signIn("", "password") {}
        val expectedState = SignInUiState(
            isLoading = false,
            showError = true
        )

        assertEquals(expectedState, viewModel.uiState)
    }

    @Test
    fun shouldDisplayErrorWhenUserSignsInWithoutPassword() = runTest {
        viewModel.signIn("username", "") {}
        val expectedState = SignInUiState(
            isLoading = false,
            showError = true
        )

        assertEquals(expectedState, viewModel.uiState)
    }

    @Test
    fun shouldDisplayErrorWhenSignInRequestFails() {
        viewModel.signIn("username", "password") {}

        val loadingState = SignInUiState(
            isLoading = true,
            showError = false
        )
        assertEquals(loadingState, viewModel.uiState)

        service.whenSignInFails()
        val expectedState = SignInUiState(
            isLoading = false,
            showError = true
        )
        assertEquals(expectedState, viewModel.uiState)
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

        assertEquals(expectedState, viewModel.uiState)
        assertEquals(receivedDirectoryId, "123")
    }

    @Test
    fun willHideErrorAfter3Seconds() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        viewModel.signIn("", "") {}

        val expectedState = SignInUiState(
            isLoading = false,
            showError = false
        )

        advanceTimeBy(3001)

        assertEquals(expectedState, viewModel.uiState)
    }
}
