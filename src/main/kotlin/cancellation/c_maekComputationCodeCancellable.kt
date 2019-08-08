package cancellation

import kotlinx.coroutines.*

/**
 *  계산 코드를 취소 가능하게 만드는 두가지 방법이 있습니다.
 *  첫번째는 취소를 체크하는 서스펜드 함수를 주기적으로 실행 시켜 주는 거입니다.
 *  yield 함수가 좋은 선택이 될 것입니다.
 *  다른 한가지 방법은 명시적으로 취소 상태를 체크하는 것입니다.
 *  다음과 같이 CoroutineScope.isActive 확장 함수로 체크가 가능합니다. 
 */
fun main() = runBlocking {
    //sampleStart
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (isActive) { // cancellable computation loop
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
//sampleEnd
}