package com.appt.berealtest.services

import FileDirectory
import ImageFile

var count = 1

class NetworkBeRealImageService : BeRealImageService {
    private var username = ""
    private var password = ""

    override suspend fun signIn(username: String, password: String): SignInResponse {
        this.username = username
        this.password = password

        println("Signing in: $username - $password")
        return SignInResponse.Success(
            FileDirectory("123", "Some Folder")
        )
    }

    override suspend fun getDirectory(
        directoryId: String
    ): GetDirectoryResponse {
        println("Getting Directory: $directoryId")

        if (count++ % 3 == 0) {
            return GetDirectoryResponse.Fail
        }

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
