package com.appt.berealtest.services

import FileDirectory
import ImageFile

class NetworkBeRealImageService(
    private val apiService: BeRealApi,
    private val base64EncoderService: Base64EncoderService
) : BeRealImageService {
    private var auth = ""

    override suspend fun signIn(username: String, password: String): SignInResponse {
        val credentials = "$username:$password"
        val base64 = base64EncoderService.encode(credentials)
        auth = "Basic $base64"


        return try {
            val response = apiService.getMe(auth)
            val data = response.body()!!
            SignInResponse.Success(
                FileDirectory(data.rootItem.id, data.rootItem.name)
            )
        } catch (exception: Exception) {
            SignInResponse.Fail
        }
    }

    override suspend fun getDirectory(
        directoryId: String
    ): GetDirectoryResponse {
        return try {
            val response = apiService.getItems(auth, directoryId)

            val data = response.body()!!

            val directories = data.filter { it.isDir }.map { FileDirectory(it.id, it.name) }
            val images = data.filter { !it.isDir }.map { ImageFile(it.id, it.name) }

            GetDirectoryResponse.Success(directories, images)
        } catch (exception: Exception) {
            GetDirectoryResponse.Fail
        }
    }
}
