package d_composeingSuspendingFunction

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * GlobalScope 를 명시적으로 참조하는 async coroutine builder 를 사용하여 서스팬딩 함수를 비동기적으로 호출 하는 비동기 스타일 함수를 정의 할 수 있습니다.
 * 이러한 함수의 이름 끝에 'Async'를 붙여서 비동기 계산만 시작하며, 결과를 얻기 위해 deferred value 를 써야한다는 것을 강조 할 수 있습니다.
 *
 */
//fun somethingUsefulOneAsync() = GlobalScope.async {
//    doSomethingUsefulOne()
//}
//
//fun somethingUsefulTwoAsync() = GlobalScope.async {
//    doSomethingUsefulTwo()
//}

/**
 * xxAsync 함수는 서스팬딩 함수가 아니기 때문에 어디서나 사용 가능합니다.
 * 그러나 그 실행은 언제나 비동기 적으로(여기서는 동시에라는 의미입니다) 실행 됩니다.
 */
//sampleStart
// note that we don't have `runBlocking` to the right of `main` in this example
fun main() {
    val time = measureTimeMillis {
        // we can initiate async actions outside of a coroutine
        val one = somethingUsefulOneAsync()
        val two = somethingUsefulTwoAsync()
        // but waiting for a result must involve either suspending or blocking.
        // here we use `runBlocking { ... }` to block the main thread while waiting for the result
        runBlocking {
            println("The answer is ${one.await() + two.await()}")
        }
    }
    println("Completed in $time ms")
}
//sampleEnd
/**
 * 비동기 함수가 있는 이 프로그래밍 스타일은 다른 프로그래밍 언어에서 널리 사용되는 스타일이기 때문에
 * 여기서는 설명을 위해서만 제공 됩니다. 코틀린 코루틴과 함게 이 스타일을 사용하는 것은 아래 설명 된 이유로 권장하지 않습니다.
 *
 * val one = somethingUsefulOneAsync 와 one.await 식 사이에 논리적 에러가 있어서 프로그램이 익셉션을 던졌을 때 어떻게 되는 지 고민해 보자.
 * 일반적으로 글로벌 에러 핸들러는 익셉션을 받았을 것 이고 로그와 에러리포트가 개발자에게 전달 되었을 것이다.
 * 그러나 프로그램은 다른 명령을 계속 실행 할수 있습니다. 그러나 somethingUsefulOneAsync 는 그것을 시작한 작업이 중지 되었다는 사실에도 불구하고.
 * 여전히 백그라운드에서 실행 되고 있게 됩니다.
 * 구조적 동시성에서는 발생하지 않습니다.
 */

fun somethingUsefulOneAsync() = GlobalScope.async {
    doSomethingUsefulOne()
}

fun somethingUsefulTwoAsync() = GlobalScope.async {
    doSomethingUsefulTwo()
}

//suspend fun doSomethingUsefulOne(): Int {
//    delay(1000L) // pretend we are doing something useful here
//    return 13
//}
//
//suspend fun doSomethingUsefulTwo(): Int {
//    delay(1000L) // pretend we are doing something useful here, too
//    return 29
//}

