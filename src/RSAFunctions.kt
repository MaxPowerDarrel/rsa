package io.darrel.rsa

import java.math.BigInteger
import java.util.*

fun lcm(x: BigInteger, y: BigInteger): BigInteger = (x * y) / (x.gcd(y))

fun obtainTwoPrimes(bitLength: Int = 2048): Pair<BigInteger, BigInteger> {
    val random = Random(System.currentTimeMillis())
    val first = BigInteger.probablePrime(bitLength, random)
    var second = BigInteger.probablePrime(bitLength, random)
    while (first == second) {
        second = BigInteger.probablePrime(bitLength, random)
    }
    return first to second
}

fun generateKeyPair(): Pair<MaxPublicKey, MaxPrivateKey> {
    val (p, q) = obtainTwoPrimes()
    val n = p * q
    val phi = lcm((p - BigInteger.ONE), (q - BigInteger.ONE))
    val e = BigInteger.valueOf(65537L)
    val d = e.modInverse(phi)
    return MaxPublicKey(e, n) to MaxPrivateKey(d, n)
}

fun encrypt(message: String, publicKey: MaxPublicKey): ByteArray =
    encrypt(BigInteger(message.toByteArray()), publicKey).toByteArray()

fun encrypt(message: BigInteger, publicKey: MaxPublicKey): BigInteger =
    message.modPow(publicKey.e, publicKey.n)

fun decrypt(message: ByteArray, privateKey: MaxPrivateKey): String =
    String(decrypt(BigInteger(message), privateKey).toByteArray())

fun decrypt(message: BigInteger, privateKey: MaxPrivateKey): BigInteger =
    message.modPow(privateKey.d, privateKey.n)

data class MaxPrivateKey(val d: BigInteger, val n: BigInteger)
data class MaxPublicKey(val e: BigInteger, val n: BigInteger)
