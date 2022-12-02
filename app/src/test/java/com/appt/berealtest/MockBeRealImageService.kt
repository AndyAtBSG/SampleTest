package com.appt.berealtest

import com.appt.berealtest.models.FileDirectory
import com.appt.berealtest.services.BeRealImageService
import com.appt.berealtest.services.CreateDirectoryResponse
import com.appt.berealtest.services.GetDirectoryResponse
import com.appt.berealtest.services.SignInResponse
import kotlinx.coroutines.CompletableDeferred

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

    override suspend fun createDirectory(
        parentDirectoryId: String,
        directoryName: String
    ): CreateDirectoryResponse {
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
}
