package io.darrel.rsa

import java.io.File

private const val PRIME_FILE_LOCATION = "/Users/darrel/IdeaProjects/rsa/resources/primes.txt"

tailrec fun gcd(x: Long, y: Long): Long = if (x == 0L) y
else gcd(y % x, x)

fun eulerTotient(x: Long): Long {
    tailrec fun accumulator(n: Long, acc: Long): Long = if (n >= x) acc
    else if (gcd(n, x) == 1L) accumulator(n + 1, acc + 1)
    else accumulator(n + 1, acc)
    return accumulator(2, 1)
}

fun phi(x: Long) = eulerTotient(x)

fun findAllPrimesToN(n: Long, writeToFile: Boolean = false): Set<Long> {
    val primes: MutableSet<Long> = mutableSetOf(2L)
    for (i in 2..n) {
        if (primes.filter { it * it <= i }.none { i % it == 0L }) primes.add(i)
    }
    if(writeToFile)
        writePrimesToFile(primes)
    return primes.toSet()
}

fun writePrimesToFile(primes: MutableSet<Long>, file: File = File(PRIME_FILE_LOCATION)) {
    file.writeText(primes.joinToString(separator="\n"))
}
