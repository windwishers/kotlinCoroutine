package eCoroutineContextAndDispatchers

/**
 * Combining context elements
 *
 * 때론, 컨텍스트를 위하여 다수의 요소를 정의 할 필요가 있습니다.
 * 우리는 + 명령문을 이용하여 이것을 할 수 있습니다.
 * 예를 들어. 명시적 디스펜서와 명시적 이름을 한번에 지정 할때 하기와 같이 합니다.
 *
 */

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    //sampleStart
    launch(Dispatchers.Default + CoroutineName("test")) {
        println("I'm working in thread ${Thread.currentThread().name}")
    }
//sampleEnd
}