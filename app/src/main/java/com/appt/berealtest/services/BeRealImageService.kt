package com.appt.berealtest.services

import FileDirectory

sealed class SignInResponse {
    object Fail : SignInResponse()

    data class Success(
        val rootItem: FileDirectory
    ) : SignInResponse()
}

interface BeRealImageService {
    suspend fun signIn(username: String, password: String): SignInResponse
}
