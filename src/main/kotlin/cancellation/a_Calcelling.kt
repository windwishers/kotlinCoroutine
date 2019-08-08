package cancellation

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Cancelling coroutine execution

/**
 * 오래 실행 되는 응용프로그램에서는 백 그라운드 코루틴에 대한 세밀한 제어가 필요할 수 있습니다.
 * 예를 들어 사용자가 시작한 페이지를 닫았을 때 결과가 더이상 필요하지 않고, 작업을 취소 할 수 있습니다.
 * Launch 함수는 반환된 Job 을 이용하여 실행 중인 코루틴을 Cancel 할 수 있습니다.
 */
fun main() = runBlocking {
//sampleStart
    val job = launch {
        repeat(1000) { i ->
            println("job: I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancel() // 작업 취소.
    job.join() // 잡이 끝나길 기다린다.
    /** 메인이 실행 되자 마자 job.cancel을 하면 아무엇도 출력 되지 않습니다.
     * 바로 취소 되기 때문입니다. cancel 과 join 이 결합 된 cancelAndJoin 확장 함수가 있습니다.
     * */
    println("main: Now I can quit.")
//sampleEnd
}
