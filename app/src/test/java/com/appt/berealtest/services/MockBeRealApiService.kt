package com.appt.berealtest.services

import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import retrofit2.Response
import java.io.IOException

class MockBeRealApiService : BeRealApi {
    private var throwException = false
    private var returnError = false
    private var getMeResponse = GetMeResponse(
        firstName = "",
        lastName = "",
        rootItem = RootItem(
            id = "123",
            name = "aName",
            isDir = true,
            modificationDate = "",
            parentId = ""
        )
    )
    private lateinit var receivedAuth: String

    override suspend fun getMe(authorization: String): Response<GetMeResponse> {
        receivedAuth = authorization

        if (throwException) {
            throw IOException()
        }

        if (returnError) {
            return Response.error(400, ResponseBody.create(null, ""))
        }

        return Response.success(getMeResponse)
    }

    override suspend fun getItems(authorization: String, id: String): Response<List<GetItemsResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun postItem(
        authorization: String,
        contentType: String,
        id: String,
        body: PostItemBody,
        contentDescription: String?
    ): Response<Unit> {
        TODO("Not yet implemented")
    }

    fun whenServiceWillThrowException() {
        throwException = true
    }

    fun whenServiceWillReturnError() {
        returnError = true
    }

    fun whenServiceSucceeds(id: String, name: String) {
        getMeResponse = getMeResponse.copy(
            rootItem = getMeResponse.rootItem.copy(
                id = id,
                name = name
            )
        )
    }

    fun thenAuthorizationIsSet(expectedAuth: String) {
        assertEquals(expectedAuth, receivedAuth)
    }
}
