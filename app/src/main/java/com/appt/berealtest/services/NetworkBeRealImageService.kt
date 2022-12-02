package com.appt.berealtest.services

import FileDirectory
import ImageFile

class NetworkBeRealImageService(
    private val apiService: BeRealApi,
    private val base64EncoderService: Base64EncoderService
) : BeRealImageService {
    override suspend fun signIn(username: String, password: String): SignInResponse {
        val credentials = "$username:$password"
        val base64 = base64EncoderService.encode(credentials)
        BASE64_AUTH = "Basic $base64"

        return try {
            val response = apiService.getMe(BASE64_AUTH)

            if (response.isSuccessful) {
                val data = response.body()!!
                SignInResponse.Success(
                    FileDirectory(data.rootItem.id, data.rootItem.name)
                )
            } else {
                SignInResponse.Fail
            }
        } catch (exception: Exception) {
            SignInResponse.Fail
        }
    }

    override suspend fun getDirectory(
        directoryId: String
    ): GetDirectoryResponse {
        return try {
            val response = apiService.getItems(BASE64_AUTH, directoryId)

            if (response.isSuccessful) {
                val data = response.body()!!

                val directories = data.filter { it.isDir }.map { FileDirectory(it.id, it.name) }
                val images = data.filter { !it.isDir }.map { ImageFile(it.id, it.name) }

                GetDirectoryResponse.Success(directories, images)
            } else {
                GetDirectoryResponse.Fail
            }
        } catch (exception: Exception) {
            GetDirectoryResponse.Fail
        }
    }

    override suspend fun createDirectory(
        parentDirectoryId: String,
        directoryName: String
    ): CreateDirectoryResponse {
        return try {
            val response = apiService.postItem(
                BASE64_AUTH,
                "application/json",
                parentDirectoryId,
                PostItemBody(directoryName)
            )

            if (response.isSuccessful) {
                CreateDirectoryResponse.Success
            } else {
                CreateDirectoryResponse.Fail
            }
        } catch (exception: Exception) {
            CreateDirectoryResponse.Fail
        }
    }

    companion object {
        var BASE64_AUTH = ""
    }
}
