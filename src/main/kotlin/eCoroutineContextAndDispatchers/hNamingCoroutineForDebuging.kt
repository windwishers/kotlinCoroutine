package eCoroutineContextAndDispatchers

/**
 * Naming coroutine for debugging
 *
 *  Automatically assigned ids are good when coroutines log often and you just need to correlate log records coming from the same coroutine
 *  그러나 특정한 백그라운드 작업을 하거나 특별한 요청을 할때에는, 디버깅을 하기 위한 명시적인 이름을 지정 하는 것이 좋습니다.
 *  콘텍스트의 요소  CoroutineName 은 디버깅 모드가 활성화 되었을때 코루틴 이름과 같이 쓰레드 이름에 포함 됩니다.
 *
 *
 *
 */

import kotlinx.coroutines.*
//fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun main() = runBlocking(CoroutineName("main")) {
    //sampleStart
    log("Started main coroutine")
    // run two background value computations
    val v1 = async(CoroutineName("v1coroutine")) {
        delay(500)
        log("Computing v1")
        252
    }
    val v2 = async(CoroutineName("v2coroutine")) {
        delay(1000)
        log("Computing v2")
        6
    }
    log("The answer for v1 / v2 = ${v1.await() / v2.await()}")
//sampleEnd
}

/*

[main] Started main coroutine
[main] Computing v1
[main] Computing v2
[main] The answer for v1 / v2 = 42

-Dkotlinx.coroutines.debug  // add JVM option

[main @main#1] Started main coroutine
[main @v1coroutine#2] Computing v1
[main @v2coroutine#3] Computing v2
[main @main#1] The answer for v1 / v2 = 42



 */