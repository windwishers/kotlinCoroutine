package fExceptionHandeling

/**
 * Cancellation and exceptions.
 *
 * 쉬소는 익셉션과 긴밀하게 연관 되어 있습니다. 코루틴은 취소를 위해서 내부적으로  CancellationException 을 사용합니다.
 * 이러한 CancellationException은 모든 핸들러에서 무시되기 때문에 오직 추가 적인 디버그 정보가 필요 할때만 catch 블럭이 필요 합니다.
 * 코루틴을 job.cancel 을 사용하여 취소하고, 종료 되면, 부모 코루틴은 취소 되지 않습니다. 원인 없는 취소는 부모가 자신은 취소 하지 않 고 자식 코루틴을 일괄 취소하는 매커니즘입니다.
 */

import kotlinx.coroutines.*

fun main() = runBlocking {
    //sampleStart
    val job = launch {
        val child = launch {
            try {
                delay(Long.MAX_VALUE)
            } finally {
                println("Child is cancelled")
            }
        }
        yield()
        println("Cancelling child")
        child.cancel()
        child.join()
        yield()
        println("Parent is not cancelled")
    }
    job.join()
//sampleEnd
}