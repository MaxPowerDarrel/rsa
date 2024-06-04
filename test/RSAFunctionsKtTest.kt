package io.darrel.rsa

import kotlin.test.Test
import kotlin.test.assertEquals


class RSAFunctionsKtTest {

    @Test
    fun lcmTest() {
        assertEquals(6L, lcm(2L, 6L))
        assertEquals(60L, lcm(5L, 12))
    }

    @Test
    fun modularInverseTest() {
        assertEquals(4, modInverse(3, 11))
        assertEquals(12, modInverse(10, 17))
    }

    @Test
    fun encryptionTest() {
        val keyPair = generateKeyPair()
        val message = 89L
        val encryptedMessage = encrypt(message, keyPair.first)
        val decryptedMessage = decrypt(encryptedMessage, keyPair.second)
        assertEquals(message, decryptedMessage.toLong())
    }
}
