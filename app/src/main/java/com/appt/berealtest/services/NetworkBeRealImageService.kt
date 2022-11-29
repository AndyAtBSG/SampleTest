package com.appt.berealtest.services

import FileDirectory
import ImageFile

class NetworkBeRealImageService : BeRealImageService {
    override suspend fun signIn(username: String, password: String): SignInResponse {
        println("Signing in: $username - $password")
        return SignInResponse.Success(
            FileDirectory("123", "Some Folder")
        )
    }

    override suspend fun getDirectory(
        username: String,
        password: String,
        directoryId: String
    ): GetDirectoryResponse {
        println("Getting Directory: $directoryId")

        return GetDirectoryResponse.Success(
            listOf(
                FileDirectory("1", "A Directory"),
            ),
            listOf(
                ImageFile("2", "An Image")
            )
        )
    }
}
