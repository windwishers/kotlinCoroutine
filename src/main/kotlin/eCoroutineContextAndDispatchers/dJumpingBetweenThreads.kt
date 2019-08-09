package eCoroutineContextAndDispatchers

/**
 * Jumping between threads 쓰레드 간 전환.
 *
 *  이 파일에서는 몇가지 새로운 기술을 보여 줍니다.
 *  하나는 명시적으로 컨텍스트를 지정하여  runBlocking 을 사용하는 것이고.
 *  다른 하나는 withContext 함수를 사용 하여 같은 코루틴을 유지한체로 컨텍스트만 변경 하는 것 입니다.
 */

import kotlinx.coroutines.*

//fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun main() {
//sampleStart
    newSingleThreadContext("Ctx1").use { ctx1 ->
        newSingleThreadContext("Ctx2").use { ctx2 ->
            runBlocking(ctx1) {
                log("Started in ctx1")
                withContext(ctx2) {
                    log("Working in ctx2")
                }
                log("Back to ctx1")
            }
        }
    }
//sampleEnd
}