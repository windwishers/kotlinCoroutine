package d_composeingSuspendingFunction

import kotlinx.coroutines.*
import kotlin.system.*

/**
 * Lazily Started async 지연 시작 되는 비동기.
 *
 * CoroutineStart.LAZY 값을 start  파라미터로 전달 하여 async 를 지였시킵니다.
 * 결과값이 필요 할 시점에 await 를 사용하거나. start 를 실행 킬 때 실행 됩니다.
 *
 * 개별 코루틴에서 start를 생략하고 await를 사용하는경우.  await가 실행을 시작하고 완료 될 때 까지 기다리게 되어
 * 순차적으로 실행 되게 되는데 이것은 의도된 사용 사례가 아닙니다.
 * # 하단에서 스타드 투줄을 지워보면 순차적으로 실행되어 2000MS 이상이 나오게 됩니다.
 * # 모르겠음.
 *  The use-case for async(start = CoroutineStart.LAZY) is a replacement for the standard lazy function in cases when computation of the value involves suspending functions.
 *
 * CoroutineStart : https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-start/
 * CoroutineStart.DEFAULT : 기본값 : 해당 콘텍스에 따라서 즉시 시작 됩니다.
 * CoroutineStart.LAZY : 시작이 필요 할때 까지 - await 나 명시적으로 start를 시작 했을 때 만 시작한다.
 * CoroutineStart.ATOMIC : 코루틴 컨텍스트에 따라 취소  되지 않는 방식으로 원자성을 가집니다.
 * CoroutineStart.UNDISPATCHED :  즉시 시작하여 현재 쓰레드의 첫번쨰 서스펜딩 포인트까지 실행 됩니다.,
 */
fun main() = runBlocking<Unit> {
    //sampleStart
    val time = measureTimeMillis {
        val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
        val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
        // some computation
        one.start() // start the first one
        two.start() // start the second one
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
//sampleEnd
}

//
//suspend fun doSomethingUsefulOne(): Int {
//    delay(1000L) // pretend we are doing something useful here
//    return 13
//}
//
//suspend fun doSomethingUsefulTwo(): Int {
//    delay(1000L) // pretend we are doing something useful here, too
//    return 29
//}