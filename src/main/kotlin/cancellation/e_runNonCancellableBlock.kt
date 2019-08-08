package cancellation
import kotlinx.coroutines.*

/**
 * Run non-cancellable block
 * ----
 *  finally 블록에서 서스팬드 함수의 기능을 사용 하려고 하면 CancellationException 이 발생합니다.
 *  이유는 이 코드를 실행하는 코루틴이 취소 되었기 때문입니다.
 *  일반적으로 파일을 닫거나 작업을 취소하거나 모든 종류의 통신을 닫는 등의 모든 통신체널을 닫는 작업은
 *  차단되거나 서스팬드 되지 않기 때문에 문제가 되지 않습니다.
 *  그러나 드문 경우 취소된 코루틴에서 서스팬딩 되어야하는경우.
 *  아래의 예제와 같이 withContext 와 NonCallable 컨텍스르르 이용하여 해당 코드를 withContext(NonCancellable) 로 래핑 할 수 있습니다.
 *  ## withContext 는 그 블럭의 코루틴 컨텍스트를 바꾸는 것이고 NonCancellable 은 isActive  가  무조건 true 인 컨텍스트입니다.
 *  즉 이전 컨텍스트는 취소 되었음으로 사용 할  수 없으며. NonCancellable 로 전환 되었음으로 해당 컨텍스트 내에서는 취소 되지 않습니다.
 */
fun main() = runBlocking {
    //sampleStart
    val job = launch {
        try {
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            withContext(NonCancellable) {
                println("job: I'm running finally")
                delay(1000L)
                println("job: And I've just delayed for 1 sec because I'm non-cancellable")
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
//sampleEnd
}