package com.appt.berealtest.services

import org.junit.Assert.assertEquals

class MockBase64Encoder(private val response: String) : Base64EncoderService {
    private lateinit var receivedData: String

    override fun encode(data: String): String {
        receivedData = data
        return response
    }

    fun thenDataIsEncoded(expectedData: String) {
        assertEquals(expectedData, receivedData)
    }
}
