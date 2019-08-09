package eCoroutineContextAndDispatchers

/**
 * Job In the Context  컨텍스트 속의 잡.
 *
 * 코루틴 Job 은 콘텍스트의 일부입니다.
 * 그리고 coroutineContext[Job] 을 이용하여 검색 할 수있습니다.
 *
 * 디버그 옵션을 넣으면 이렇게 보입니다.
 * My job is "coroutine#1":BlockingCoroutine{Active}@768debd
 * Note : isActive 는 단지 coroutineContext[Job]?.isActive == true 의 단축 식 입니다.
 */

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    //sampleStart
    println("My job is ${coroutineContext[Job]}")
//sampleEnd
}