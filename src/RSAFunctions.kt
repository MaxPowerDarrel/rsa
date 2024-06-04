package io.darrel.rsa

import java.math.BigInteger
import kotlin.random.Random

tailrec fun gcd(x: Long, y: Long): Long = if (x == 0L) y
else gcd(y % x, x)

fun lcm(x: Long, y: Long): Long = (x * y) / gcd(x, y)

fun modInverse(a: Long, m: Long): Long = (1..m).firstOrNull { ((a % m) * (it % m) % m) == 1L } ?: 1

fun findAllPrimesToN(n: Long): Set<Long> {
    val primes: MutableSet<Long> = mutableSetOf(2L)
    for (i in 2..n) {
        if (primes.none { i % it == 0L }) primes.add(i)
    }
    return primes.toSet()
}

fun obtainTwoPrimes(): Pair<Long, Long> {
    val primes = findAllPrimesToN(10_000)
    val primeList = primes.toMutableList()
    val random = Random.Default
    val prime1 = primeList[random.nextInt(primeList.size)]
    primeList.remove(prime1)
    val prime2 = primeList[random.nextInt(primeList.size)]
    return prime1 to prime2
}

fun generateKeyPair(): Pair<PublicKey, PrivateKey> {
    val (p, q) = obtainTwoPrimes()
    val n = p * q
    val phi = lcm((p - 1), (q - 1))
    val e = (2..phi).first { gcd(it, phi) == 1L } // or use 65,537
    val d = modInverse(e, phi)
    return PublicKey(e, n) to PrivateKey(d, n)
}


fun encrypt(message: Long, publicKey: PublicKey): BigInteger =
    (BigInteger.valueOf(message).modPow(BigInteger.valueOf(publicKey.e), BigInteger.valueOf(publicKey.n)))

fun decrypt(message: BigInteger, privateKey: PrivateKey): BigInteger =
    message.modPow(BigInteger.valueOf(privateKey.d), BigInteger.valueOf(privateKey.n))

data class PrivateKey(val d: Long, val n: Long)
data class PublicKey(val e: Long, val n: Long)
