package com.appt.berealtest.services

import FileDirectory
import ImageFile

sealed class SignInResponse {
    object Fail : SignInResponse()

    data class Success(
        val rootItem: FileDirectory
    ) : SignInResponse()
}

sealed class GetDirectoryResponse {
    object Fail : GetDirectoryResponse()

    data class Success(
        val directories: List<FileDirectory>,
        val images: List<ImageFile>
    ) : GetDirectoryResponse()
}

sealed interface CreateDirectoryResponse {
    object Success : CreateDirectoryResponse

    object Fail : CreateDirectoryResponse
}

interface BeRealImageService {
    suspend fun signIn(username: String, password: String): SignInResponse

    suspend fun getDirectory(
        directoryId: String
    ): GetDirectoryResponse

    suspend fun createDirectory(
        parentDirectoryId: String,
        directoryName: String,
    ): CreateDirectoryResponse
}
