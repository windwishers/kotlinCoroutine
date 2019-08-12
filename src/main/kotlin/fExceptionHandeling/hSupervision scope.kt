package fExceptionHandeling

/**
 * supervision scope
 * coroutineScope  대신에 같은 목적으로  SupervisionScope 를 사용 할 수 도 있습니다.
 * 슈퍼비젼스코프의 경우 자신이 실패한 경우에만 모든 자식을 취소하며. coroutineScope 와 동일하게 모든 자식 코루틴이 종료 되기를 기다립니다.
 *
 *
 */

import kotlin.coroutines.*
import kotlinx.coroutines.*

fun main() = runBlocking {
    try {
        supervisorScope {
            val child = launch {
                try {
                    println("Child is sleeping")
                    delay(Long.MAX_VALUE)
                } finally {
                    println("Child is cancelled")
                }
            }
            // Give our child a chance to execute and print using yield
            yield()
            println("Throwing exception from scope")
            throw AssertionError()
        }
    } catch(e: AssertionError) {
        println("Caught assertion error")
    }
}