package d_composeingSuspendingFunction

import kotlinx.coroutines.*
import kotlin.system.*

/**
 * doSomeThingUsefulOne 과 doSomethingUsefulTwo 둘 사이에 의존성이 없고.
 * 두 작업을 동시에 실행하여 좀더 빠르게 답변을 얻고자 한다면. async가 도움이 될 것입니다
 * 개념적으로 async 는 Launch와 같습니다. 다른 모든 코루틴과 동시에 작동하는 가벼운쓰레드를 시작합니다.
 * 이 둘의 차이점은 Launch 가 Job을 전달 하고 결과 값을 전달 하지 않 는 반면에.
 * Async 는 Defferd 르 반환 합니다. 이것은 나중에 결과 값을 전달 해 주겠다는 약속을 나타내는 경량 논 블로킹 퓨처 입니다.
 * .await()를 이용하여 최종 결과물을 얻을 수도 있으며,  Deferred 또한 Job의 일종리기 때문에 취소도 가능합니다.   
 */
fun main() = runBlocking<Unit> {
    //sampleStart
    val time = measureTimeMillis {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
//sampleEnd
}

// 코드 겹침. 파일내용은 aSequentialSuspendingFunction을 참고.
//suspend fun doSomethingUsefulOne1(): Int {
//    delay(1000L) // pretend we are doing something useful here
//    return 13
//}
//
//suspend fun doSomethingUsefulTwo1(): Int {
//    delay(1000L) // pretend we are doing something useful here, too
//    return 29
//}