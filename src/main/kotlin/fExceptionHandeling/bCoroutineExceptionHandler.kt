package fExceptionHandeling

/**
 * CoroutineExceptionHandler
 *
 * 그러나 모든 예외를 콘솔에 인쇄하지않으려면 어떻게 해야할까요?
 *  CoroutineExceptionHandler 컨텍스트 요소는 커스텀 된 로깅이나 익셉션 처리를 위한 일반 catch 블록으로 사용 됩니다.
 * 이것은 Thread.uncaughtExceptionHandler 를 사용하는 것과 비슷합니다.
 *
 *  JVM 에서는, ServiceLoader를 통해  CoroutineExceptionHandler에 등록 된 모든 코루틴을 위한 글로벌 익셉션 핸들러를 재정의 할 수 있습니다.
 * 글로벌 익셉션 핸들러는 특정 핸들러가 더이상 등록 되어 있지 않을때 사용 되는 uncaughtExceptionPreHandler 와 유사합니다.
 * 안드로이드에서는 uncatchExceptionPreHandler 는 글로벌 코루틴 익셉션 핸들러로  설치 되어 있습니다.
 */

import kotlinx.coroutines.*

fun main() = runBlocking {
    //sampleStart
    val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }
    val job = GlobalScope.launch(handler) {
        throw AssertionError()
    }
    val deferred = GlobalScope.async(handler) {
        throw ArithmeticException() // Nothing will be printed, relying on user to call deferred.await()
    }
    joinAll(job, deferred)
//sampleEnd
}