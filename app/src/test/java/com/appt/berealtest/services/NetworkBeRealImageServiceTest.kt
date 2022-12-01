package com.appt.berealtest.services

import FileDirectory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class NetworkBeRealImageServiceTest {
    private val base64String = "a1b2c3"
    private val base64EncoderService = MockBase64Encoder(base64String)
    private val api = MockBeRealApiService()
    private val service = NetworkBeRealImageService(api, base64EncoderService)

    @Test
    fun shouldReturnFailResponseWhenSignInThrowsException() = runTest {
        api.whenServiceWillThrowException()
        val response = service.signIn("auser", "apassword")
        assertEquals(SignInResponse.Fail, response)
    }

    @Test
    fun shouldReturnFailResponseWhenSignInReturnsError() = runTest {
        api.whenServiceWillReturnError()
        val response = service.signIn("auser", "apassword")
        assertEquals(SignInResponse.Fail, response)
    }

    @Test
    fun shouldReturnSuccessResponseWhenSignInSucceeds() = runTest {
        api.whenServiceSucceeds("someId", "someName")
        val response = service.signIn("auser", "apassword")
        val expectedResponse = SignInResponse.Success(FileDirectory("someId", "someName"))
        assertEquals(expectedResponse, response)
    }

    @Test
    fun shouldSendAuthenticationWhenSigningIn() = runTest {
        service.signIn("aUser", "aPassword")
        base64EncoderService.thenDataIsEncoded("aUser:aPassword")
        api.thenAuthorizationIsSet("Basic $base64String")
    }
}
