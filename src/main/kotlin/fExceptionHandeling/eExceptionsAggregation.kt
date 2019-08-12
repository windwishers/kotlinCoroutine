package fExceptionHandeling

/**
 * Exceptions aggregation  익셉션 집계.
 *
 * 여러 자식 코루틴이 익셉션을 던지면 어떤 일이 일어나는가?
 * 일반적으로는 첫번째 익셉션이 우선 됩니다. 그래서 첫번째 에러가 핸들러에 노출 됩니다.
 * 하지만 이것은 익셉션을 잃어버릴 수 도 있다는 것을 의미하는데. 코루틴의 finally 블록에서 예외가 발생하는 경우가 예가 될 수 있습니다.
 *
 * # 뭐라는지 모르겠네요.
 * note : One of the solutions would have been to report each exception separately, but then Deferred.await should have had the same mechanism to avoid behavioural inconsistency and this would cause implementation details of a coroutines (whether it had delegated parts of its work to its children or not) to leak to its exception handler.
 *
 *
 */

import kotlinx.coroutines.*
import java.io.*

fun main() = runBlocking {
    val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception with suppressed ${exception.suppressed.contentToString()}")
    }
    val job = GlobalScope.launch(handler) {
        launch {
            try {
                delay(Long.MAX_VALUE)
            } finally {
                throw ArithmeticException()
            }
        }
        launch {
            delay(100)
            throw IOException()
        }
        delay(Long.MAX_VALUE)
    }
    job.join()
}
/**
 * note : 파이널 블록에서 발생하는 예외는 exception.supressed 로 확인 할 수 있습니다. (단 1.7+ 에서만)
 * */

