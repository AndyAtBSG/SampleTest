package com.appt.berealtest.services

import FileDirectory

class NetworkBeRealImageService : BeRealImageService {
    override suspend fun signIn(userName: String, password: String): SignInResponse {
        return SignInResponse.Success(
            FileDirectory("123", "Some Folder")
        )
    }
}
