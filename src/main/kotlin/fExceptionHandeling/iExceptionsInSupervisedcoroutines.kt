package fExceptionHandeling

/**
 *  Exception in supervised coroutine
 *
 * 일반 잡과 수퍼비젼잡의 에러핸들링에서의 결정적인 차이점은.
 * 모든 자식 잡이 예외처리매커니즘을 통해서 자체적으로 예외를 처리해야한다는 것입니다.
 * 이러한 차이는 자식 잡의 에러가 부모에게 전파 되지 않는다는 사실에서 기인합니다.
 */

import kotlin.coroutines.*
import kotlinx.coroutines.*

fun main() = runBlocking {
    val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }
    supervisorScope {
        val child = launch(handler) {
            println("Child throws an exception")
            throw AssertionError()
        }
        println("Scope is completing")
    }
    println("Scope is completed")
}