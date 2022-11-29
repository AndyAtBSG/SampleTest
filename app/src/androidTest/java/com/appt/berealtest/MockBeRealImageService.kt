package com.appt.berealtest

import FileDirectory
import com.appt.berealtest.services.BeRealImageService
import com.appt.berealtest.services.SignInResponse
import kotlinx.coroutines.CompletableDeferred
import org.junit.Assert

class MockBeRealImageService : BeRealImageService {
    lateinit var signInResult: CompletableDeferred<SignInResponse>
    var receivedUsername = ""
    var receivedPassword = ""

    override suspend fun signIn(username: String, password: String): SignInResponse {
        receivedUsername = username
        receivedPassword = password
        signInResult = CompletableDeferred()
        return signInResult.await()
    }

    fun thenSignInIsCalled(expectedUsername: String, expectedPassword: String) {
        Assert.assertEquals(expectedUsername, receivedUsername)
        Assert.assertEquals(expectedPassword, receivedPassword)
    }

    fun whenSignInSucceeds(rootItem: FileDirectory) {
        signInResult.complete(
            SignInResponse.Success(rootItem)
        )
    }

    fun whenSignInFails() {
        signInResult.complete(SignInResponse.Fail)
    }
}
