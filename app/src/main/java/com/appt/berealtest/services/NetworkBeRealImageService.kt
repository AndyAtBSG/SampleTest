package com.appt.berealtest.services

import FileDirectory

class NetworkBeRealImageService : BeRealImageService {
    override suspend fun signIn(username: String, password: String): SignInResponse {
        println("Signing in: $username - $password")
        return SignInResponse.Success(
            FileDirectory("123", "Some Folder")
        )
    }
}
