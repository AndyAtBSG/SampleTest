package com.appt.berealtest.services

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header


data class RootItem(
    val id: String,
    val parentId: String,
    val name: String,
    val isDir: Boolean,
    val modificationDate: String
)

data class GetMeResponse(
    val firstName: String,
    val lastName: String,
    val rootItem: RootItem
)

interface BeRealApi {
    @GET("me")
    fun getMe(@Header("Authorization") authorization: String): Response<GetMeResponse>
}
