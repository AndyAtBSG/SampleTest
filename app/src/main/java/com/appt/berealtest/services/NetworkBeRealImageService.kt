package com.appt.berealtest.services

import com.appt.berealtest.models.FileDirectory
import com.appt.berealtest.models.ImageFile
import java.util.*

var count = 1

class NetworkBeRealImageService : BeRealImageService {
    private var username = ""
    private var password = ""

    override suspend fun signIn(username: String, password: String): SignInResponse {
        this.username = username
        this.password = password

        println("Signing in: $username - $password")

        if (username.isEmpty()) {
            return SignInResponse.Fail
        }
        return SignInResponse.Success(
            FileDirectory("123", "Some Folder")
        )
    }

    override suspend fun getDirectory(
        directoryId: String
    ): GetDirectoryResponse {
        println("Getting Directory: $directoryId")

        if (count++ % 5 == 0) {
            return GetDirectoryResponse.Fail
        }

        return GetDirectoryResponse.Success(
            getDirectories(count),
            getImages(count)
        )
    }

    private fun getDirectories(quantity: Int) = (0..quantity).map {
        FileDirectory(UUID.randomUUID().toString(), "Directory $it")
    }

    private fun getImages(quantity: Int) = (0..quantity).map {
        ImageFile(UUID.randomUUID().toString(), "Image $it")
    }
}
