package io.darrel.rsa

import java.math.BigInteger
import kotlin.random.Random

tailrec fun gcd(x: Long, y: Long): Long = if (x == 0L) y
else gcd(y % x, x)

fun lcm(x: Long, y: Long): Long = (x * y) / gcd(x, y)

fun modInverse(a: Long, m: Long): Long {
    var m0 = m
    var a0 = a
    var y = 0L
    var x = 1L

    if (m == 1L) return 0

    while (a0 > 1) {
        val q = a0 / m0
        var t = m0

        m0 = a0 % m0
        a0 = t
        t = y

        y = x - q * y
        x = t
    }

    return if (x < 0L) (x % m + m) % m else x
}

fun obtainTwoPrimes(): Pair<Long, Long> {
    val primesList =
        Thread.currentThread().contextClassLoader.getResourceAsStream("primes.txt")
            ?.bufferedReader()?.readLines()?.map { it.toLong() }?.toMutableList() ?: throw NullPointerException()

    return Pair(getRandomPrimeAndRemove(primesList), getRandomPrimeAndRemove(primesList))
}

private fun getRandomPrimeAndRemove(primes: MutableList<Long>): Long {
    val randomPrime = primes[Random.Default.nextInt(primes.size)]
    primes.remove(randomPrime)

    return randomPrime
}

fun generateKeyPair(): Pair<PublicKey, PrivateKey> {
    val (p, q) = obtainTwoPrimes()
    val n = p * q
    val phi = lcm((p - 1), (q - 1))
    val e = 65537L //(2..phi).first { gcd(it, phi) == 1L } // or use 65,537
    val d = modInverse(e, phi)
    return PublicKey(e, n) to PrivateKey(d, n)
}


fun encrypt(message: Long, publicKey: PublicKey): BigInteger =
    message.toBigInteger().modPow(publicKey.e.toBigInteger(), publicKey.n.toBigInteger())

fun decrypt(message: BigInteger, privateKey: PrivateKey): BigInteger =
    message.modPow(privateKey.d.toBigInteger(), privateKey.n.toBigInteger())

data class PrivateKey(val d: Long, val n: Long)
data class PublicKey(val e: Long, val n: Long)
