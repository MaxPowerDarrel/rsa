package io.darrel.rsa

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class RSAFunctionsKtTest {

    @ParameterizedTest
    @MethodSource("totientPairs")
    fun eulerTotientTest(x: Pair<Long, Long>) {
        assertEquals(x.second, phi(x.first))
    }

    companion object {
        @JvmStatic
        fun totientPairs(): List<Pair<Long, Long>> {
            return listOf(
                1L to 1L,
                2L to 1L,
                3L to 2L,
                4L to 2L,
                5L to 4L,
                6L to 2L,
                7L to 6L,
                8L to 4L,
                9L to 6L,
                10L to 4L
            )
        }
    }

    @Test
    fun testPrimeFinding() {
        val primes = findAllPrimesToN(1000L, true)
        assertEquals(168, primes.size)
    }
}
