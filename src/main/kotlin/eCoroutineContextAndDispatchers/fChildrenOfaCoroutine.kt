package eCoroutineContextAndDispatchers

import kotlinx.coroutines.*

/**
 * Children of coroutine  코루틴의 피상속자.
 * 다른 코루틴의 코루틴스코프에서 코루틴이 시작 된 경우.
 * CoroutineScope.coroutineContext 를 통해 컨텍스트를 상속 받으며 새로운 코루틴의 작업은 보무 코루틴의 잡의 피상속자가 됩니다.
 * 부모 코루틴이 취소 되면 모든 자식 코루틴은 재귀적으로 취소 뒵니다.
 *
 * 그러나 , GlobalScope 을 사용하여 실행된 코루틴은 부모잡을 가지지 않으며, 따라서 독립적으로 실행 됩니다.
 * (즉 바깥 코루틴이 취소되어도 취소 되지 않습니다.)
 */

fun main() = runBlocking<Unit> {
    //sampleStart
    // launch a coroutine to process some kind of incoming request
    val request = launch {
        // it spawns two other jobs, one with GlobalScope
        GlobalScope.launch {
            println("job1: I run in GlobalScope and execute independently!")
            delay(1000)
            println("job1: I am not affected by cancellation of the request")
        }
        // and the other inherits the parent context
        launch {
            delay(100)
            println("job2: I am a child of the request coroutine")
            delay(1000)
            println("job2: I will not execute this line if my parent request is cancelled")
        }
    }
    delay(500)
    request.cancel() // cancel processing of the request
    delay(1000) // delay a second to see what happens
    println("main: Who has survived request cancellation?")
//sampleEnd
}