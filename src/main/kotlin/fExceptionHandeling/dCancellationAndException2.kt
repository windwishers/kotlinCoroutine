package fExceptionHandeling

/**
 * Cancellation and exceptions. 뒷부분
 *
 *  코루틴이 CancellationException 이 아닌 익셉션을 만나면. 익셉션과 함께 부모 코루틴을 취소 합니다.
 *  이동작은 overridden 할 수 없으며. CoroutineExceptionHandler 구현에 의존하지 않는 구조적 동시성에 대한 안정적 코루틴 계층을 제공하는데 사용 됩니다. 원래 익셉션은 모든 자식 코루틴이 종료 되면, 부모에 의해서 처리 됩니다.
 *
 * note :  이 예제에서, CoroutineExceptionHandler 가 항항 GlobalScope 에 작성 되는 것도 이러한 이유 때문입니다. 기존에 Runblocking 에서 시작 된 코루틴에 예외 핸들러를 설치 하는 것은, 자식이 예외로 종료 되면 항상 취소 되기 때문에 무의미 합니다.
 *
 *
 *
 */

import kotlinx.coroutines.*

fun main() = runBlocking {
    //sampleStart
    val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }
    val job = GlobalScope.launch(handler) {
        launch { // the first child
            try {
                delay(Long.MAX_VALUE)
            } finally {
                withContext(NonCancellable) {
//                    println("Children are cancelled, but exception is not handled until all children terminate")
                    println("자식 코루틴은 취소 되었습니다. 하지만 모든 자식 코루틴이 종료 될때 까지 익셉션은 catch 되지 않습니다. ")
                    delay(100)
                    println("The first child finished its non cancellable block")
                }
            }
        }
        launch { // the second child
            delay(10)
            println("Second child throws an exception")
            throw ArithmeticException()
        }
    }
    job.join()
//sampleEnd
}