package fExceptionHandeling

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Cancellation exceptions are transparent and unwrapped by default:
 * cancellationException 은 투명하고 래핑되지 않습니다.
 * # 글로벌 익셉션 핸들러에서 IO 익셉션만 잡히는 것을 볼 수있습니다.
 */

import kotlinx.coroutines.*
import java.io.*

fun main() = runBlocking {
    //sampleStart
    val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught original $exception")
    }
    val job = GlobalScope.launch(handler) {
        val inner = launch {
            launch {
                launch {
                    throw IOException()
                }
            }
        }
        try {
            inner.join()
        } catch (e: CancellationException) {
            println("Rethrowing CancellationException with original cause")
            throw e
        }
    }
    job.join()
//sampleEnd
}