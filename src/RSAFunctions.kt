package io.darrel.rsa

import java.math.BigInteger
import java.util.*

fun lcm(x: BigInteger, y: BigInteger): BigInteger = (x * y) / (x.gcd(y))

fun obtainTwoPrimes(): Pair<BigInteger, BigInteger> {
    val random = Random(System.currentTimeMillis())
    val bitLength = 2048
    var first = BigInteger.ONE
    var second = BigInteger.ONE
    while (first == second) {
        first = BigInteger.probablePrime(bitLength, random)
        second = BigInteger.probablePrime(bitLength, random)
    }
    return first to second
}

fun generateKeyPair(): Pair<PublicKey, PrivateKey> {
    val (p, q) = obtainTwoPrimes()
    val n = p * q
    val phi = lcm((p - BigInteger.ONE), (q - BigInteger.ONE))
    val e = BigInteger.valueOf(65537L)
    val d = e.modInverse(phi)
    return PublicKey(e, n) to PrivateKey(d, n)
}


fun encrypt(message: BigInteger, publicKey: PublicKey): BigInteger =
    message.modPow(publicKey.e, publicKey.n)

fun decrypt(message: BigInteger, privateKey: PrivateKey): BigInteger =
    message.modPow(privateKey.d, privateKey.n)

data class PrivateKey(val d: BigInteger, val n: BigInteger)
data class PublicKey(val e: BigInteger, val n: BigInteger)
