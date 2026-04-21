package com.example.tipcalculator

import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CoroutinesTest {

    private suspend fun fetchUserData(userId: Int): String {
        delay(1000)
        return if (userId > 0) "Data pengguna dengan ID: $userId" else "ID tidak valid"
    }

    @Test
    fun testFetchUserDataValid() = runTest {
        val result = fetchUserData(5)
        assertEquals("Data pengguna dengan ID: 5", result)
    }

    @Test
    fun testFetchUserDataInvalid() = runTest {
        val result = fetchUserData(-1)
        assertEquals("ID tidak valid", result)
    }

    @Test
    fun testFetchUserDataZero() = runTest {
        val result = fetchUserData(0)
        assertEquals("ID tidak valid", result)
    }
}