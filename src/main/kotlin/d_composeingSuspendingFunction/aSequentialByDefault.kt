package d_composeingSuspendingFunction

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis


/**
 * 순차적으로 실행 해야하는 경우 어떻게 해야하는가요? --- 첫번째 doSomethingUsefulOne을 실행하고.
 * 두번째 doSomethingUsefullTwo 를 실행 하고, 그 결과를 더하세요.
 * 실제로 첫번째 함수의 결과를 사용하여 두번째 함수를 호출 할지 또는 호출 방법을 결정 해야합니다.
 *
 * 일반적인 코드와 마찬가지고 코루틴 코드는 기본적으로 순차적이기 때문에 순차적으로 실행 됩니다
 */
fun main() = runBlocking<Unit> {
    //sampleStart
    val time = measureTimeMillis {
        val one = doSomethingUsefulOne()
        val two = doSomethingUsefulTwo()
        println("The answer is ${one + two}")
    }
    println("Completed in $time ms")
//sampleEnd
}

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}
