package com.appt.berealtest.services

import com.appt.berealtest.models.FileDirectory
import com.appt.berealtest.models.ImageFile

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


interface BeRealImageService {
    suspend fun signIn(username: String, password: String): SignInResponse

    suspend fun getDirectory(
        directoryId: String
    ): GetDirectoryResponse
}
