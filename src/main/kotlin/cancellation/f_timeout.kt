package cancellation

import kotlinx.coroutines.*

/**
 * 코루틴을 최소하는 가장 확실하고도 실질적인 이유는 실행 시간이 시간을 초과 했기 때문입니다.
 * 해당 작업에 해당 작업에 대하여 참조를 유지하고 별도의 코루틴을 시작하여 지연후 참조 된 작업을 취소 할 수도 있지만
 * (편리하게도) 미리 준비된 WithTimeout 기능을 사용 할 수 도 있습니다.
 */
//fun main() = runBlocking {
//    //sampleStart
//    withTimeout(1300L) {
//        repeat(1000) { i ->
//            println("I'm sleeping $i ...")
//            delay(500L)
//        }
//    }
////sampleEnd
//}
//  kotlinx.coroutines.TimeoutCancellationException: 이 발생합니다.

/**
 * withTimeOut 이 던지는 TimeoutCancellationException 은 CancellationException 의 서브 클래스입니다.
 * 우리는 이전에 콘솔에서 스텍트레이스가 출력 되는 것을 본적이 없습니다.
 * 그 이유는 코루틴이 내부적으로 CancellationException 을 코루틴 완료의 일반적인 이유로 간주 하기 때문입니다.
 * 그러나 상단의 예제에서는 메인 함수 내에서 직접 사용하였습니다. (그래서 콘솔에 노출된단 이야기)
 *
 *  Cancellation 은 익셉션의 일종이기 때문에, 모든 리소느는 일반적인 방법으로 close 됩니다.
 *  타임아웃에 대하여 특별한 엑션이 필요한 경우 try{....} catch(e: TimeoutCancellationException) 블록으로 코드를 감싸거나.
 *  익셉션을 던지는 대신에 리턴값이 필요한 경우에는 WithTimeOutOrNull 을 사용 할 수 있습니다.
 */
fun main() = runBlocking {
    //sampleStart
    val result = withTimeoutOrNull(1300L) {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
        "Done" // will get cancelled before it produces this result
    }
    println("Result is $result")
//sampleEnd
}