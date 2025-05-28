package io.darrel.rsa

import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals


class RSAFunctionsKtTest {

    @Test
    fun lcmTest() {
        assertEquals(BigInteger.valueOf(6), lcm(BigInteger.valueOf(2), BigInteger.valueOf(6)))
        assertEquals(BigInteger.valueOf(60), lcm(BigInteger.valueOf(5), BigInteger.valueOf(12)))
    }

    @Test
    fun encryptionTest() {
        val keyPair = generateKeyPair()
        val message = BigInteger.valueOf(13)
        val encryptedMessage = encrypt(message, keyPair.first)
        val decryptedMessage = decrypt(encryptedMessage, keyPair.second)
        assertEquals(message, decryptedMessage)
    }

    @Test
    fun encryptionStringTest() {
        val keyPair = generateKeyPair()
        val message = "Hi Darrel"
        val encryptedMessage = encrypt(message, keyPair.first)
        val decryptedMessage = decrypt(encryptedMessage, keyPair.second)
        assertEquals(message, decryptedMessage)
    }
}
