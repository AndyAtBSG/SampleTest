package com.appt.berealtest

import FileDirectory
import ImageFile
import com.appt.berealtest.services.BeRealImageService
import com.appt.berealtest.services.CreateDirectoryResponse
import com.appt.berealtest.services.GetDirectoryResponse
import com.appt.berealtest.services.SignInResponse
import kotlinx.coroutines.CompletableDeferred
import org.junit.Assert.assertEquals

class MockBeRealImageService : BeRealImageService {
    private lateinit var signInResult: CompletableDeferred<SignInResponse>
    private var signInUsername = ""
    private var signInPassword = ""

    private lateinit var getDirectoryResult: CompletableDeferred<GetDirectoryResponse>
    private var getDirectoryId = ""

    override suspend fun signIn(username: String, password: String): SignInResponse {
        signInUsername = username
        signInPassword = password
        signInResult = CompletableDeferred()
        return signInResult.await()
    }

    override suspend fun getDirectory(
        directoryId: String
    ): GetDirectoryResponse {
        getDirectoryId = directoryId
        getDirectoryResult = CompletableDeferred()
        return getDirectoryResult.await()
    }

    override suspend fun createDirectory(directoryId: String): CreateDirectoryResponse {
        TODO("Not yet implemented")
    }

    fun whenSignInSucceeds(rootItem: FileDirectory) {
        signInResult.complete(
            SignInResponse.Success(rootItem)
        )
    }

    fun whenSignInFails() {
        signInResult.complete(SignInResponse.Fail)
    }

    fun whenGetDirectorySucceeds(directories: List<FileDirectory>, images: List<ImageFile>) {
        getDirectoryResult.complete(
            GetDirectoryResponse.Success(directories, images)
        )
    }

    fun whenGetDirectoryFails() {
        getDirectoryResult.complete(
            GetDirectoryResponse.Fail
        )
    }

    fun thenSignInIsCalled(expectedUsername: String, expectedPassword: String) {
        assertEquals(expectedUsername, signInUsername)
        assertEquals(expectedPassword, signInPassword)
    }

    fun thenGetDirectoryIsCalled(
        expectedDirectoryId: String
    ) {
        assertEquals(expectedDirectoryId, getDirectoryId)
    }

}
