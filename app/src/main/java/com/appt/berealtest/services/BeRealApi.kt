package com.appt.berealtest.services

import com.squareup.moshi.Json
import retrofit2.Response
import retrofit2.http.*


data class RootItem(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "parentId") val parentId: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "isDir") val isDir: Boolean,
    @field:Json(name = "modificationDate") val modificationDate: String
)

data class GetMeResponse(
    @field:Json(name = "firstName") val firstName: String,
    @field:Json(name = "lastName") val lastName: String,
    @field:Json(name = "rootItem") val rootItem: RootItem
)

data class GetItemsResponse(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "parentId") val parentId: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "isDir") val isDir: Boolean,
    @field:Json(name = "size") val size: String?,
    @field:Json(name = "contentType") val contentType: String?,
    @field:Json(name = "modificationDate") val modificationDate: String,
)

data class PostItemBody(
    @field:Json(name = "name") val name: String,
)

interface BeRealApi {
    @GET("me")
    suspend fun getMe(@Header("Authorization") authorization: String): Response<GetMeResponse>

    @GET("items/{id}")
    suspend fun getItems(
        @Header("Authorization") authorization: String,
        @Path("id") id: String
    ): Response<List<GetItemsResponse>>

    @POST("items/{id}")
    suspend fun postItem(
        @Header("Authorization") authorization: String,
        @Header("Content-Type") contentType: String,
        @Path("id") id: String,
        @Body body: PostItemBody,
        @Header("Content-Disposition") contentDescription: String? = null
    ): Response<Unit>
}
