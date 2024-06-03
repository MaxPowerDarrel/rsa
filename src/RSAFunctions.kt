package io.darrel.rsa

import kotlin.random.Random

tailrec fun gcd(x: Long, y: Long): Long = if (x == 0L) y
else gcd(y % x, x)

fun eulerTotient(x: Long): Long {
    tailrec fun accumulator(n: Long, acc: Long): Long = if (n >= x) acc
    else if (gcd(n, x) == 1L) accumulator(n + 1, acc + 1)
    else accumulator(n + 1, acc)
    return accumulator(2, 1)
}

fun phi(x: Long) = eulerTotient(x)

fun findAllPrimesToN(n: Long): Set<Long> {
    val primes: MutableSet<Long> = mutableSetOf(2L)
    for (i in 2..n) {
        if (primes.filter { it * it <= i }.none { i % it == 0L }) primes.add(i)
    }
    return primes.toSet()
}

fun obtainTwoPrimes(): Pair<Long, Long> {
    val primes = findAllPrimesToN(10_000)
    val primeList = primes.toList()
    val random = Random.Default
    val prime1 = primeList[random.nextInt(primeList.size)]
    val prime2 = primeList[random.nextInt(primeList.size)]
    return prime1 to prime2
}

fun generateKey() {
    val (prime1, prime2) = obtainTwoPrimes()
    val n = prime1 * prime2
    val phiN = phi(n)
    val e = (2..phiN).first { gcd(it, phiN) == 1L }
    val d = (2..phiN).first { (it * e) % phiN == 1L }
//    return KeyPair(n, e, d)
}
