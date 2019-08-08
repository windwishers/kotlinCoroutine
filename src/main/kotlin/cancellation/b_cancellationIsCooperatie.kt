package cancellation
import kotlinx.coroutines.*
// Cancellation is cooperative
//
/**
 * 코루틴 취소는 코-오퍼레이션 입니다. 코루틴 코드가 취소 할 수 있도록 협력해야합니다.
 * kotlinx.coroutines 안의 모든 서스팬드 펑션은 cancelable 입니다.
 * 이러한 함수들은 코루틴의 취소를 확인하고 취소 되었을 때 CancellationException 을 던집니다
 * 그러나 만약 계산 중이거나 취소를 체크 하지 않는다면 취소 되지 않습니다.
 */
fun main() = runBlocking {
    //sampleStart
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0

        while (i < 5) { // computation loop, just wastes CPU
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
//                kotlinx.coroutines.delay(1L)   // 딜레이 함수가 캔슬가능하기 때문에 주석을 해제하면 바로 취소됨.
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