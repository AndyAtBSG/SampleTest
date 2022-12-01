package com.appt.berealtest.services

import FileDirectory

class NetworkBeRealImageService(
    private val apiService: BeRealApi,
    private val base64EncoderService: Base64EncoderService
) : BeRealImageService {
    private var username = ""
    private var password = ""

    override suspend fun signIn(username: String, password: String): SignInResponse {
        val credentials = "aUser:aPassword"
        val base64 = base64EncoderService.encode(credentials)
        val auth = "Basic $base64"

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

        return GetDirectoryResponse.Success(
            emptyList(),
            emptyList()
        )
    }
}
