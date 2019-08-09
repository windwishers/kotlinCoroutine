package eCoroutineContextAndDispatchers

/**
 * Parental responsibilities
 *
 * 부모 코루틴은 모든 자식 코루틴이 종료 되는 것 을 항상 기다립니다.
 * 부모 코루틴은 모든 자식을 명시적으로 추적 할 필요도. job.join을 사용할 필요도 없습니다.
*/

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    //sampleStart
    // launch a coroutine to process some kind of incoming request
    val request = launch {
        repeat(3) { i -> // launch a few children jobs
            launch  {
                delay((i + 1) * 200L) // variable delay 200ms, 400ms, 600ms
                println("Coroutine $i is done")
            }
        }
        /** repeat 안에서의 3번 런치된 Job 3개는 추적 하지 않았지만 종료 할때 까지 이 코루틴이 알아서 기다림. */
        println("request: I'm done and I don't explicitly join my children that are still active")
    }
    request.join() // wait for completion of the request, including all its children
    println("Now processing of the request is complete")
//sampleEnd
}